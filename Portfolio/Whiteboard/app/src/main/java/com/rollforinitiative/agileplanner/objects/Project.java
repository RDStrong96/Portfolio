package com.rollforinitiative.agileplanner.objects;

import android.os.health.ProcessHealthStats;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static androidx.room.ForeignKey.CASCADE;

// @Entity defines a table in a database, the
// tableName attribute defines the name of the table in the databse
@Entity (tableName = "project_table")
public class Project implements Serializable {

    // auto generate an integer id for each project
    @PrimaryKey (autoGenerate = true)
    @NonNull
    @ColumnInfo (name = "project_id")
    private int projectID = 0;

    @ColumnInfo (name = "project_title")
    private String title;

    // add another column to the Project table, this is
    // defined by the column name annotation
    @NonNull
    @ColumnInfo (name = "project_description")
    private String description;

    //initializer takes title and description for project
    //@NonNull ensures that a null title and description cannot be provided for a project
    public Project(@NonNull int projectID, @NonNull String title, @NonNull String description) {
        this.projectID = projectID;
        this.title = title;
        this.description = description;
    }

    //getters for private instance variables
    public String getTitle() {
        return this.title;
    }
    public String getDescription() {
        return this.description;
    }
    public int getProjectID() { return this.projectID; }
}