package com.rollforinitiative.agileplanner.persistence;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.rollforinitiative.agileplanner.objects.Magnet;
import com.rollforinitiative.agileplanner.objects.Project;
import com.rollforinitiative.agileplanner.objects.Whiteboard;

import java.util.List;

//this class acts as a middle-man between external data sources,
//at the time of writing, the only data source is the Room database
public class WhiteboardRepository {

    //reference to the DAO for projects in order to form queries
    private ProjectDao mProjectDao;
    private WhiteboardDao mWhiteBoardDao;
    private MagnetDao mMagnetDao;

    //LiveData wrapped list of projects returned form the database
    private LiveData<List<Project>> mAllProjects;

    //initializer sets up the database for the application
    public WhiteboardRepository(Application application) {
        //initialize the database for future use
        WhiteboardRoomDatabase db = WhiteboardRoomDatabase.getDatabase(application);
        //get the project DAO from the database
        mProjectDao = db.projectDao();
        mWhiteBoardDao = db.whiteboardDao();
        mMagnetDao = db.magnetDao();

        //maintain a list of projects by retrieving LiveData<List<Project>> using the
        // ProjectDao "SELECT *" query
        mAllProjects = mProjectDao.getAll();
    }

    //wrapper for returning the private ref to all projects
    public LiveData<List<Project>> getAllProjects() {
        return mAllProjects;
    }

    public LiveData<List<Whiteboard>> getWhiteboardsForProject(int projectID) {
        return mWhiteBoardDao.getAllForProject(projectID);
    }

    public LiveData<List<Magnet>> getMagnetsForWhiteboard(int whiteboardID) {
        return mMagnetDao.getAllForWhiteboard(whiteboardID);
    }

    //public method called to insert projects into the db
    //this has to be done via AsyncTask because by default database queries cannot be
    //performed on the main thread as it handles UI updates
    // without the AsyncTask the UI would freeze when the database is being queried
    public void insertProject (Project project) {
        new asyncInsertProject(mProjectDao).execute(project);
    }

    //read up on AsyncTask to understand the data types inside the angle brackets,
    //but basically the first one defines what kind of parameter is supplied to the
    //task for execution
    private static class asyncInsertProject extends AsyncTask<Project, Void, Void> {

        private ProjectDao mAsyncTaskDao;

        asyncInsertProject(ProjectDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Project... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    //public method called to insert projects into the db
    //this has to be done via AsyncTask because by default database queries cannot be
    //performed on the main thread as it handles UI updates
    // without the AsyncTask the UI would freeze when the database is being queried
    public void insertWhiteboard (Whiteboard whiteboard) {
        new asyncInsertWhiteboard(mWhiteBoardDao).execute(whiteboard);
    }

    //read up on AsyncTask to understand the data types inside the angle brackets,
    //but basically the first one defines what kind of parameter is supplied to the
    //task for execution
    private static class asyncInsertWhiteboard extends AsyncTask<Whiteboard, Void, Void> {

        private WhiteboardDao mAsyncTaskDao;

        asyncInsertWhiteboard(WhiteboardDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Whiteboard... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    //public method called to insert projects into the db
    //this has to be done via AsyncTask because by default database queries cannot be
    //performed on the main thread as it handles UI updates
    // without the AsyncTask the UI would freeze when the database is being queried
    public void insertMagnet (Magnet magnet) {
        new asyncInsertMagnet(mMagnetDao).execute(magnet);
    }

    public void updateMagnet (Magnet magnet) {
        new asyncUpdateMagnet(mMagnetDao).execute(magnet);
    }

    //read up on AsyncTask to understand the data types inside the angle brackets,
    //but basically the first one defines what kind of parameter is supplied to the
    //task for execution
    private static class asyncInsertMagnet extends AsyncTask<Magnet, Void, Void> {

        private MagnetDao mAsyncTaskDao;

        asyncInsertMagnet(MagnetDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Magnet... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class asyncUpdateMagnet extends AsyncTask<Magnet, Void, Void> {

        private MagnetDao mAsyncTaskDao;

        asyncUpdateMagnet(MagnetDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Magnet... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
