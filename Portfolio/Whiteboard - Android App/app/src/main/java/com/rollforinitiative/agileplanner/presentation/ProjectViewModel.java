package com.rollforinitiative.agileplanner.presentation;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rollforinitiative.agileplanner.objects.Project;
import com.rollforinitiative.agileplanner.persistence.WhiteboardRepository;

import java.util.List;


// provide the model for Projects, used mostly in the MainActivity to get updates on
// the projects table and notify changes
public class ProjectViewModel extends AndroidViewModel {

    //internal reference to the repository that manages all sources of data
    private WhiteboardRepository mRepository;
    //LiveData wrapped list of projects to ensure live updates of changes
    private LiveData<List<Project>> mAllProjects;

    //initializer calls superclass AndroidViewModel initializer and sets up instance variables
    public ProjectViewModel (Application application) {
        super(application);
        mRepository = new WhiteboardRepository(application);
        mAllProjects = mRepository.getAllProjects();
    }

    //wrapper to get all projects from the repository
    public LiveData<List<Project>> getAllProjects() { return mAllProjects; }

    //wrapper to insert projects into the repository
    public void insertProject(Project project) { mRepository.insertProject(project); }
}
