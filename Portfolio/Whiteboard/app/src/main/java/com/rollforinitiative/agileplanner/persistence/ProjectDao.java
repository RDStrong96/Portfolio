package com.rollforinitiative.agileplanner.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.rollforinitiative.agileplanner.objects.Project;

import java.util.List;

//(D)ata (A)ccess (O)bject
// defines SQL queries for inserting Project instances into a database
@Dao
public interface ProjectDao {

    // @Insert is included in Room and allows for objects to be insterted into a databse
    @Insert
    void insert(Project project);

    // @Query allows for custom SQL queries, Room will verify these so that if there are
    // any issues they are compile-time and not runtime errors
    @Query("DELETE FROM project_table")
    void deleteAll();

    // returns a LiveData list of projects, this allows for MainActivity to respond
    // to changes in the Project table so that the list can be updated
    @Query("SELECT * from project_table ORDER BY project_title ASC")
    LiveData<List<Project>> getAll();

}
