package com.rollforinitiative.agileplanner.presentation;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rollforinitiative.agileplanner.objects.Project;
import com.rollforinitiative.agileplanner.objects.Whiteboard;
import com.rollforinitiative.agileplanner.persistence.WhiteboardRepository;

import java.util.List;

public class WhiteboardViewModel extends AndroidViewModel {

    //internal reference to the repository that manages all sources of data
    private WhiteboardRepository mRepository;
    //LiveData wrapped list of projects to ensure live updates of changes
    private LiveData<List<Whiteboard>> mCurrentWhiteboards;

    private int currentProject;

    //initializer calls superclass AndroidViewModel initializer and sets up instance variables
    public WhiteboardViewModel (Application application) {
        super(application);
        mRepository = new WhiteboardRepository(application);
        mCurrentWhiteboards = null;
    }

    //public method for setting the
    public void setCurrentProject (int id) {
        currentProject = id;
        mCurrentWhiteboards = mRepository.getWhiteboardsForProject(currentProject);
    }

    //wrapper to get all projects from the repository
    public LiveData<List<Whiteboard>> getCurrentWhiteboards() { return mCurrentWhiteboards; }

    //wrapper to insert projects into the repository
    public void insertWhiteboard(Whiteboard whiteboard) { mRepository.insertWhiteboard(whiteboard); }

}
