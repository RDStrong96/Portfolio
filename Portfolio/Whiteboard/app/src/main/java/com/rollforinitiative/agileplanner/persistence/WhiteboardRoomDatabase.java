package com.rollforinitiative.agileplanner.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rollforinitiative.agileplanner.objects.Magnet;
import com.rollforinitiative.agileplanner.objects.Project;
import com.rollforinitiative.agileplanner.objects.Whiteboard;


// this annotation is used by room to define tables in the database,
// these are defined using the @Entity annotation in a class
@Database(entities = {Project.class, Whiteboard.class, Magnet.class}, version = 12)
public abstract class WhiteboardRoomDatabase extends RoomDatabase {

    //instance variable for Data Access Object (DAO)
    //this defines SQL queries to insert project objects into tables
    public abstract ProjectDao projectDao();
    public abstract WhiteboardDao whiteboardDao();
    public abstract MagnetDao magnetDao();

    //singleton instance variable, this ensures only one database instance can be created
    public static WhiteboardRoomDatabase INSTANCE;

    //method to ensure the INSTANCE database can only be created once
    static WhiteboardRoomDatabase getDatabase(final Context context) {
        //if the instance hasn't been created, create it with the Room database builder
        if (INSTANCE == null) {
            synchronized (WhiteboardRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WhiteboardRoomDatabase.class, "whiteboard_database")
                            // Wipe and rebuild database instead of migrating
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        //if the instance has been created, return it
        return INSTANCE;
    }
}
