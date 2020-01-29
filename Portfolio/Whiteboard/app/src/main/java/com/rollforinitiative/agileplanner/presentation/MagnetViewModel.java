package com.rollforinitiative.agileplanner.presentation;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rollforinitiative.agileplanner.objects.Magnet;
import com.rollforinitiative.agileplanner.objects.Project;
import com.rollforinitiative.agileplanner.persistence.WhiteboardRepository;

import java.util.ArrayList;
import java.util.List;

public class MagnetViewModel extends AndroidViewModel {
    //internal reference to the repository that manages all sources of data
    private WhiteboardRepository mRepository;
    //LiveData wrapped list of magnets to ensure live updates of changes
    private LiveData<List<Magnet>> mCurrentMagnets;
    private int currentWhiteboard;

    //initializer calls superclass AndroidViewModel initializer and sets up instance variables
    public MagnetViewModel (Application application) {
        super(application);
        mRepository = new WhiteboardRepository(application);
        mCurrentMagnets = null;
    }

    public void setCurrentWhiteboard(int id) {
        currentWhiteboard = id;
        mCurrentMagnets = mRepository.getMagnetsForWhiteboard(id);
    }

    //wrapper to get all magnets from the repository
    public LiveData<List<Magnet>> getCurrentMagnets() { return mCurrentMagnets; }

    //wrapper to insert magnets into the repository
    public void insertMagnet(Magnet magnet) { mRepository.insertMagnet(magnet); }
    public void updateMagnet(Magnet magnet) { mRepository.updateMagnet(magnet);}


}
