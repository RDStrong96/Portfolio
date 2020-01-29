package com.rollforinitiative.agileplanner.objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "whiteboard_table", foreignKeys = @ForeignKey(entity = Project.class,
        parentColumns = "project_id",
        childColumns = "project_id",
        onDelete = CASCADE))
public class Whiteboard {

    @PrimaryKey (autoGenerate = true)
    @NonNull
    @ColumnInfo (name = "whiteboard_id")
    private int whiteboardID = 0;

    @NonNull
    @ColumnInfo (name = "whiteboard_name")
    private String name;

    @NonNull
    @ColumnInfo (name = "project_id")
    private int projectID;

    public Whiteboard(@NonNull int whiteboardID, @NonNull String name, @NonNull int projectID) {
        this.whiteboardID = whiteboardID;
        this.name = name;
        this.projectID = projectID;
        //magnets = new ArrayList<>();
    }

    public String getName() { return name; }
    public int getWhiteboardID() { return whiteboardID; }
    public int getProjectID() { return projectID; }
}