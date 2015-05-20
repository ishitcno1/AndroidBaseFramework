package com.edinstudio.android.baseframework.util;

import android.content.Context;

import com.activeandroid.ActiveAndroid;

/**
 * Created by albert on 15-5-20.
 */
public class Initializer {
    public static void initialize(Context context) {
        ActiveAndroid.initialize(context);
    }
}
