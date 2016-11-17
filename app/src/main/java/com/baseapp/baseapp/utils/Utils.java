package com.baseapp.baseapp.utils;

import android.text.TextUtils;

/**
 * Created by tunglam on 11/10/16.
 */

public class Utils {
    public static String upperCaseFirstChar(String s) {
        if (TextUtils.isEmpty(s)) {
            return s;
        }

        String string = s.trim();
        if (string.length() == 0) {
            return string;
        }

        if (string.length() == 1) {
            return string.toUpperCase();
        }

        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
