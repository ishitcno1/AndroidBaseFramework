package com.edinstudio.android.baseframework.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * Created by albert on 15-5-20.
 */
public class SPUtil {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public SPUtil(Context context, String spName) {
        if (context == null) {
            throw new NullPointerException();
        }
        mSharedPreferences = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public Map<String, ?> getAll() {
        return mSharedPreferences.getAll();
    }

    public void clearAll() {
        mEditor.clear();
        mEditor.commit();
    }

    public void setBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    public void setInt(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    public void setLong(String key, long value) {
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public long getLong(String key, long defValue) {
        return mSharedPreferences.getLong(key, defValue);
    }

    public void setFloat(String key, float value) {
        mEditor.putFloat(key, value);
        mEditor.commit();
    }

    public float getFloat(String key, float defValue) {
        return mSharedPreferences.getFloat(key, defValue);
    }

    public void setString(String key, String value) {
        mEditor.putString(key, value);
        mEditor.commit();
    }

    public String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    public void setStringSet(String key, Set<String> values) {
        mEditor.putStringSet(key, values);
        mEditor.commit();
    }

    public Set<String> getStringSet(String key, Set<String> defValues) {
        return mSharedPreferences.getStringSet(key, defValues);
    }
}
