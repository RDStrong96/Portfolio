package com.rollforinitiative.agileplanner.objects;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.rollforinitiative.agileplanner.R;

import static androidx.room.ForeignKey.CASCADE;


@Entity(tableName = "magnet_table", foreignKeys = @ForeignKey(entity = Whiteboard.class,
        parentColumns = "whiteboard_id",
        childColumns = "whiteboard_id",
        onDelete = CASCADE))
public class Magnet {

    @PrimaryKey (autoGenerate = true)
    @NonNull
    @ColumnInfo (name = "magnet_id")
    private int magnetID = 0;

    @NonNull
    public float xPosition;

    @NonNull
    public float yPosition;

    @NonNull
    private String imageName;

    @NonNull
    private String magnetName;

    private String magnetDescription;

    @NonNull
    @ColumnInfo (name = "whiteboard_id")
    private int whiteboardID;

    @SuppressLint("ClickableViewAccessibility")
    public Magnet(@NonNull int magnetID, @NonNull float xPosition, @NonNull float yPosition, @NonNull String imageName, @NonNull String magnetName, @NonNull int whiteboardID) {
        this.magnetID = magnetID;
        this.imageName = imageName;
        this.magnetName = magnetName;
        this.whiteboardID = whiteboardID;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.magnetDescription = "";
    }

    public String getImageName() { return this.imageName; }
    public String getMagnetName() { return this.magnetName; }
    public float getX() { return this.xPosition; }
    public float getY() { return this.yPosition; }
    public int getMagnetID() { return this.magnetID; }
    public int getWhiteboardID() { return this.whiteboardID; }
    public String getMagnetDescription() { return this.magnetDescription; }

    public void setX(float x) { this.xPosition = x; }
    public void setY(float y) { this.yPosition = y; }
    public void setMagnetDescription(String description) {this.magnetDescription = description; }
}
