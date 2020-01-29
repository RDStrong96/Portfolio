package com.rollforinitiative.agileplanner.presentation;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rollforinitiative.agileplanner.R;
import com.rollforinitiative.agileplanner.objects.Project;

public class ProjectAdderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_adder);

        //set up activity bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Add Project");

        //create reference to the button so that we can respond to clicks
        Button addProjectButton = findViewById(R.id.addProjectButton);
        addProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = findViewById(R.id.projectNameText);
                EditText description = findViewById(R.id.projectDescriptionEdit);
                String projectName = editText.getText().toString();
                String projectDescription = description.getText().toString();
                Intent intent = new Intent();

                //check if the project name field was empty,
                //if it was, return RESULT_CANCELLED to the main activity,
                //otherwise send back a new project with the given name
                if (!projectName.matches("")) {
                    // set projectID to 0 so that room can auto increment primary keys
                    Project project = new Project(0, projectName, projectDescription);
                    intent.putExtra("Project", project);
                    setResult(RESULT_OK, intent);
                }
                else {
                    Log.d(ProjectAdderActivity.class.getSimpleName(), "empty string entered for project name, do no update adapter");
                    setResult(RESULT_CANCELED, intent);
                }
                //return back to main activity
                finish();
            }
        });
    }
}
