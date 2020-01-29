package com.rollforinitiative.agileplanner.presentation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rollforinitiative.agileplanner.R;
import com.rollforinitiative.agileplanner.objects.Magnet;
import com.rollforinitiative.agileplanner.objects.Project;
import com.rollforinitiative.agileplanner.objects.Whiteboard;

import java.util.List;

import static android.app.PendingIntent.getActivity;

public class ProjectDetailActivity extends AppCompatActivity implements ItemClickListener {
    public TextView projectTitle;
    private WhiteboardViewModel mWhiteboardViewModel;
    private MagnetViewModel mMagnetViewModel;
    private Project project;
    private RecyclerView recyclerView;
    private WhiteboardAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private int currentWhiteboard = -1;
    private List<Magnet> currentMagnets = null;
    Observer<List<Magnet>> magnetObserver = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_detail_view);

        //get the current project from the intent that created this view
        project = (Project) getIntent().getSerializableExtra("Project");

        //set up activity bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(project.getTitle());

        mWhiteboardViewModel = ViewModelProviders.of(this).get(WhiteboardViewModel.class);
        mWhiteboardViewModel.setCurrentProject(project.getProjectID());

        mMagnetViewModel = ViewModelProviders.of(this).get(MagnetViewModel.class);

        recyclerView = (RecyclerView) findViewById(R.id.whiteboard_recycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        // a horizontal layoutmanager looks like this
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new WhiteboardAdapter(null, this);
        recyclerView.setAdapter(mAdapter);

        //set up horizontal spacing between list items
        HorizontalSpaceItemDecoration dividerItemDecoration = new HorizontalSpaceItemDecoration(20);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //hook to listen for more whiteboards
        mWhiteboardViewModel.getCurrentWhiteboards().observe(this, new Observer<List<Whiteboard>>() {
            @Override
            public void onChanged(@Nullable final List<Whiteboard> whiteboards) {
                // Update cached copy of the projects in the adapter
                mAdapter.setWhiteboards(whiteboards);
            }
        });

        //create magnet adding action button with white add icon
        FloatingActionButton fab = findViewById(R.id.addMagnetFab);
        fab.hide();
        fab.setColorFilter(Color.WHITE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do nothing if there is no whiteboard selected
                if (currentWhiteboard == -1) { return; }
                final MagnetAdderFragment dialog = new MagnetAdderFragment();
                dialog.show(getSupportFragmentManager(), "whiteboard_dialog");
            }
        });

        // ActionBar bar = getActionBar();
        // bar.setBackgroundDrawable(new ColorDrawable("colorBackground"));
    }

    public void renderMagnets() {
        for (Magnet magnet : currentMagnets) {
            MagnetView v = new MagnetView(magnet, (ConstraintLayout) findViewById(R.id.magnetContainer), getApplicationContext(), this, mMagnetViewModel);
        }
    }

    // hook to inflate menu bar with the res/menu/main.xml layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle add button clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addProject) {
            WhiteboardAdderFragment dialog = new WhiteboardAdderFragment();
            dialog.show(getSupportFragmentManager(), "whiteboard_dialog");
        }
        return super.onOptionsItemSelected(item);
    }

    public void addWhiteboard (String whiteboardName) {
        Whiteboard newWhiteboard = new Whiteboard(0, whiteboardName, project.getProjectID());
        mWhiteboardViewModel.insertWhiteboard(newWhiteboard);
    }

    public void addMagnet (String magnetName, String magnetIconName) {
        Magnet newMagnet = new Magnet(0, 50, 50, "magnets/" + magnetIconName, magnetName, currentWhiteboard);
        mMagnetViewModel.insertMagnet(newMagnet);
    }

    //called when a whiteboard is clicked
    @Override
    public void onClick(Whiteboard whiteboard) {
        if (magnetObserver != null) {
            mMagnetViewModel.getCurrentMagnets().removeObserver(magnetObserver);
        } else {
            //if the magnetObserver has not been set, this is the first time a whiteboard is being
            //opened, so let the user know how magnets work
            String headsUp = "Drag Magnets to reposition, and long press for details";
            Toast toast = Toast.makeText(getApplicationContext(), headsUp, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM|Gravity.LEFT, 50, 50);
            toast.show();

            //show add magnet button
            FloatingActionButton fab = findViewById(R.id.addMagnetFab);
            fab.show();
        }

        TextView currentWhiteboardTitle = findViewById(R.id.selectedWhiteboardTitle);
        ((ConstraintLayout) findViewById(R.id.magnetContainer)).removeAllViews();
        currentWhiteboard = whiteboard.getWhiteboardID();
        currentWhiteboardTitle.setText("Magnets for " + whiteboard.getName());
        mMagnetViewModel.setCurrentWhiteboard(currentWhiteboard);
        currentMagnets = mMagnetViewModel.getCurrentMagnets().getValue();

        magnetObserver = new Observer<List<Magnet>>() {
            @Override
            public void onChanged(@Nullable final List<Magnet> magnets) {
                // Update cached copy of the projects in the adapter
                ((ConstraintLayout) findViewById(R.id.magnetContainer)).removeAllViews();
                currentMagnets = magnets;
                renderMagnets();
            }
        };

        mMagnetViewModel.getCurrentMagnets().observe(this, magnetObserver);
    }

    //private class to creat spacing between horizontal list items
    //horizontal lists look awful without this
    private class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        //instanace variable to define the actual spacing
        private final int horizontalSpaceHeight;

        //constructor set spacing instance variable
        public HorizontalSpaceItemDecoration(int horizontalSpaceHeight) {
            this.horizontalSpaceHeight = horizontalSpaceHeight;
        }

        //hook to update the right spacing when the item decoration is created
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.right = horizontalSpaceHeight;
        }
    }

}
