package com.edinstudio.android.baseframework.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 * Created by albert on 15-5-20.
 */
public class ManifestUtil {
    private static Bundle getMetaData(Context context) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return info.metaData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getString(Context context, String key) {
        Bundle bundle = getMetaData(context);
        if (bundle == null) {
            return "";
        } else {
            return bundle.getString(key, "");
        }
    }

    public static int getInt(Context context, String key) {
        Bundle bundle = getMetaData(context);
        if (bundle == null) {
            return -1;
        } else {
            return bundle.getInt(key, -1);
        }
    }

    public static boolean getBoolean(Context context, String key) {
        Bundle bundle = getMetaData(context);
        if (bundle == null) {
            return false;
        } else {
            return bundle.getBoolean(key, false);
        }
    }
}
