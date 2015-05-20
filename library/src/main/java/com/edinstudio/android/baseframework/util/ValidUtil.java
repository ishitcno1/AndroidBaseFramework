package com.edinstudio.android.baseframework.util;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by albert on 15-5-20.
 */
public class ValidUtil {
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
