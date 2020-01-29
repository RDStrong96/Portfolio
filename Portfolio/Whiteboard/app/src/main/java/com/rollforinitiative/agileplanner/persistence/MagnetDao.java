package com.rollforinitiative.agileplanner.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.rollforinitiative.agileplanner.objects.Magnet;
import com.rollforinitiative.agileplanner.objects.Project;
import com.rollforinitiative.agileplanner.objects.Whiteboard;

import java.util.List;

@Dao
public interface MagnetDao {

    // @Insert is included in Room and allows for objects to be insterted into a databse
    @Insert
    void insert(Magnet magnet);

    // @Query allows for custom SQL queries, Room will verify these so that if there are
    // any issues they are compile-time and not runtime errors
    @Query("DELETE FROM magnet_table")
    void deleteAll();

    @Update
    void update(Magnet magnet);

    @Query("SELECT * from magnet_table " +
            "WHERE whiteboard_id = :whiteboardID ")
    LiveData<List<Magnet>> getAllForWhiteboard(int whiteboardID);

}