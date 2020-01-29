package com.rollforinitiative.agileplanner.presentation;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

import com.rollforinitiative.agileplanner.R;
import com.rollforinitiative.agileplanner.objects.Project;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProjectAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ProjectViewModel mProjectViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<Project> projects;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the view model for projects, this maintains the lifecycle of projects
        //and wraps a getter to get all of the projects from the database
        mProjectViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);

        //set up activity bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Projects");

        recyclerView = (RecyclerView) findViewById(R.id.project_recycler);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        // a horizontal layoutmanager looks like this
        //layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new ProjectAdapter(null);
        recyclerView.setAdapter(mAdapter);

        //callback for when the projects in the database are updated
        //since getAllProjects in the viewmodel returns live data, this will automatically get called
        //if any items are added to the list, then notify the adapter of the changes to that
        //the contents of the recycler view can be updated
        mProjectViewModel.getAllProjects().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable final List<Project> projects) {
                // Update cached copy of the projects in the adapter
                mAdapter.setProjects(projects);
            }
        });
    }

    // hook to inflate menu bar with the res/menu/main.xml layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle add button clicked
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addProject) {
            //start the ProjectAdder activity, set the request code to 1 so that we can
            //respond to values returned from the activity
            Intent intent = new Intent(this, ProjectAdderActivity.class);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    // handle result from finished activity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if this is a response to code 1 (adding an activity), then add the new project
        //to the database
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Project newProject = (Project) data.getSerializableExtra("Project");
                //insert the new project into the ViewModel for projects, this will
                //insert the project into the database and cause the getAllProjects observer
                //in onCreate to be triggered, updating the recyclerview
                mProjectViewModel.insertProject(newProject);
            }
        }
    }
}
