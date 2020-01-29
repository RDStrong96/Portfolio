package com.rollforinitiative.agileplanner.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.rollforinitiative.agileplanner.objects.Project;
import com.rollforinitiative.agileplanner.objects.Whiteboard;

import java.util.List;

@Dao
public interface WhiteboardDao {
    // @Insert is included in Room and allows for objects to be insterted into a databse
    @Insert
    void insert(Whiteboard whiteboard);

    // @Query allows for custom SQL queries, Room will verify these so that if there are
    // any issues they are compile-time and not runtime errors
    @Query("DELETE FROM whiteboard_table")
    void deleteAll();

    // returns a LiveData list of whiteboards associated with a given project
    @Query("SELECT * from whiteboard_table " +
            "WHERE project_id = :projectID " +
            "ORDER BY whiteboard_name ASC")
    LiveData<List<Whiteboard>> getAllForProject(int projectID);
}
