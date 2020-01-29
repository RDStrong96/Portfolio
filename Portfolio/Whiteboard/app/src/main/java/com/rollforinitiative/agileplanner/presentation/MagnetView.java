package com.rollforinitiative.agileplanner.presentation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.rollforinitiative.agileplanner.R;
import com.rollforinitiative.agileplanner.business.MagnetLoader;
import com.rollforinitiative.agileplanner.objects.Magnet;

import java.io.IOException;
import java.io.InputStream;

public class MagnetView extends AppCompatImageView {

    private float dX, dY, initX, initY;
    private float containerHeight, containerWidth;
    private Magnet magnet;
    private MagnetViewModel mMagnetViewModel;
    private AssetManager assetManager;
    private ProjectDetailActivity activity;

    @SuppressLint({"ClickableViewAccessibility", "NewApi"})
    public MagnetView (final Magnet magnet, ConstraintLayout parentLayout, Context applicationContext, ProjectDetailActivity activity, MagnetViewModel m) {
        super(applicationContext);
        assetManager = applicationContext.getAssets(); //used to retrieve image assets
        this.activity = activity;
        this.magnet = magnet;
        this.mMagnetViewModel = m;

        //setting image resource
        //this.setImageResource(R.drawable.meeting_icon120_120);

        try {
            //make a new input stream to read the image associated with an image
            InputStream is = assetManager.open(magnet.getImageName());
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            this.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        parentLayout.setPadding(0, 0, 0 , 0);
        this.setPadding(0,0,0,0);
        containerHeight = parentLayout.getHeight();
        containerWidth = parentLayout.getWidth();

        //setting image position
        this.setLayoutParams(new LinearLayout.LayoutParams(200, 200));
        parentLayout.addView(this);
        this.setX(magnet.getX());
        this.setY(magnet.getY());

        this.setOnTouchListener(new View.OnTouchListener() {

            //handler allows us to delay an action to run in another thread
            //in this case, that action is a runnable that shows a detail view popup
            //basically, when the user touches down, if they don't move the magnet or release
            //their finger in one second, the detail view gets shown
            final Handler handler = new Handler();
            Runnable mLongPressed = new Runnable() {
                public void run() {
                    showMagnetDetails();
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int offScreenOffset = 50;

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        //find how far away from the center of the image was clicked
                        dX = v.getX() - event.getRawX();
                        dY = v.getY() - event.getRawY();
                        initX = event.getRawX();
                        initY = event.getRawY();
                        //start waiting for a long press
                        handler.postDelayed(mLongPressed, ViewConfiguration.getLongPressTimeout());
                        Log.d(getClass().getSimpleName(), "Container width: " + containerWidth);
                        break;
                    case MotionEvent.ACTION_UP:
                        //when the action is finished, update its location and cancel the long press callback
                        if(v.getY() < 0) {
                            v.animate().y(offScreenOffset).setDuration(0).start();
                        }
                        else if (v.getY() > containerHeight){
                            v.animate().y(containerHeight - offScreenOffset*4).setDuration(0).start();
                        }
                        else if(v.getX() < 0) {
                            v.animate().x(offScreenOffset).setDuration(0).start();
                        }
                        else if (v.getX() > containerWidth) {
                            v.animate().x(containerWidth - offScreenOffset).setDuration(0).start();
                        }
                        updateMagnet(v.getX(), v.getY());
                        Log.d(getClass().getSimpleName(), v.getX() + ", " + v.getY());
                        handler.removeCallbacks(mLongPressed);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //when the touch event is moved, move the view with it, accounting for the
                        // distance away from the center that was clicked
                        //also stop waiting for a long press
                        if(Math.abs(initX - event.getRawX()) > 5 && Math.abs(initY - event.getRawY()) > 5)
                            handler.removeCallbacks(mLongPressed);
                        v.animate()
                                .x(event.getRawX() + dX)
                                .y(event.getRawY() + dY)
                                .setDuration(0)
                                .start();
                        break;
                }
                return true;
            }
        });
    }

    private void updateMagnet(float x, float y) {
        magnet.setX(x);
        magnet.setY(y);
        mMagnetViewModel.updateMagnet(magnet);
    }

    public void showMagnetDetails() {
        MagnetLoader loader = new MagnetLoader(getContext());

        //set up a textbox for typing details about the magnet
        final EditText textBox = new EditText(getContext());
        textBox.setSingleLine(false);
        textBox.setTextColor(Color.parseColor("#ffffff"));
        textBox.setLines(6);
        textBox.setMaxLines(8);
        textBox.setGravity(Gravity.LEFT | Gravity.TOP);
        textBox.setHorizontalScrollBarEnabled(false);
        textBox.setText(magnet.getMagnetDescription());


        //set up the dialog box that will show the view
        AlertDialog.Builder db = new AlertDialog.Builder(new ContextThemeWrapper(activity , R.style.Theme_AppCompat));
        db.setView(textBox);
        db.setIcon(loader.getDrawableForImageNames(magnet.getImageName()));
        db.setTitle("Notes for " + magnet.getMagnetName());
        db.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                magnet.setMagnetDescription(textBox.getText().toString());
                mMagnetViewModel.updateMagnet(magnet);
            }
        });

        //present the view
        AlertDialog dialog = db.show();
    }


}
