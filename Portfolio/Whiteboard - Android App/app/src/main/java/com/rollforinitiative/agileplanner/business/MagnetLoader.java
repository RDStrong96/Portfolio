package com.rollforinitiative.agileplanner.business;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MagnetLoader {
    private AssetManager assetManager;
    private Context context;

    public MagnetLoader(Context context) {
        this.context = context;
        assetManager = context.getAssets();
    }

    public String[] getMagnetIconNames() {
        try {
            return assetManager.list("magnets");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] getPrettyNames() {
        String[] list;
        ArrayList<String> prettyList = new ArrayList<>();

        try {
            list = assetManager.list("magnets");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        for (String name: list) {
            String newName = name.substring(0, name.lastIndexOf('.'));
            String capitalized = newName.substring(0, 1).toUpperCase() + newName.substring(1);
            prettyList.add(capitalized.replaceAll("_", " "));
        }

        //apparently toArray() has to get passed a useless array in order to not return a plain Object list because... Java?
        return (String[]) prettyList.toArray(new String[0]);
    }

    public String getNameAtIndex(int i) {
        String[] list;

        try {
            list = assetManager.list("magnets");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return list[i];
    }

    public BitmapDrawable getDrawableForImageNames(String name) {
        try {
            //make a new input stream to read the image associated with an image
            InputStream is = assetManager.open(name);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return new BitmapDrawable(context.getResources(), bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
