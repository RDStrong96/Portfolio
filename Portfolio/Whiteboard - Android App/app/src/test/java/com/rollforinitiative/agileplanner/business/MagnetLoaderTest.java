package com.rollforinitiative.agileplanner.business;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.contains;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsNot.not;

class MagnetLoaderTest {

    @Mock
    Context context;

    @Mock
    AssetManager assetManager;

    MagnetLoader loader;
    String[] files = {"my_file.png", "new_file_2.png", "file3.png"};

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        //return fake asset manager
        when(context.getAssets()).thenReturn(assetManager);

        try {
            //return fake list of files
            when(assetManager.list("magnets")).thenReturn(files);
        } catch (IOException e) {
            e.printStackTrace();
        }

        loader = new MagnetLoader(context);
    }

    @Test
    void getMagnetIconNames() {
        String[] items = loader.getMagnetIconNames();
        for (int i = 0; i < items.length; i++) {
            assertEquals(items[i], files[i]);
        }
    }

    @Test
    void getPrettyNames() {
        String[] prettyNames = loader.getPrettyNames();
        assertThat(prettyNames[0], not(containsString("_")));
        assertThat(prettyNames[0], not(containsString(".")));
    }

}