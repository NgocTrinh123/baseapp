package com.baseapp.baseapp.utils;

import com.baseapp.baseapp.model.database.CurrentUser;

/**
 * Created by mb on 4/1/16.
 */
public class CacheUtils {

    private static CurrentUser user;

    public static boolean isLoggedIn() {
        return SharedPreferenceHelper.getInstance().getBool("is_logged_in", false);
    }

    public static void setLoggedIn(boolean loggedIn) {
        SharedPreferenceHelper.getInstance().putBool("is_logged_in", loggedIn);
    }

    public static boolean isFirstLogin(String key) {
        return SharedPreferenceHelper.getInstance().getBool(key, true);
    }

    public static void setFirstLogin(String key, boolean firstLogin) {
        SharedPreferenceHelper.getInstance().putBool(key, firstLogin);
    }

    public static String getAuthToken() {
        return SharedPreferenceHelper.getInstance().getString("auth_token", "");
    }

    public static void setAuthToken(String token) {
        SharedPreferenceHelper.getInstance().putString("auth_token", token);
    }

    public static void clearLoggedInData() {
        SharedPreferenceHelper.getInstance().removeValue("profile");
        SharedPreferenceHelper.getInstance().removeValue("gcm_token");
        SharedPreferenceHelper.getInstance().removeValue("enable_push_sound");
        SharedPreferenceHelper.getInstance().removeValue("enable_push");
        SharedPreferenceHelper.getInstance().removeValue("is_registered_push_not");
        SharedPreferenceHelper.getInstance().removeValue("is_unregistered_push_not");
        SharedPreferenceHelper.getInstance().removeValue("is_logged_in");
        SharedPreferenceHelper.getInstance().removeValue(Constant.HTTP_AUTH_TOKEN);
    }

    public static void setGCMToken(String token) {
        SharedPreferenceHelper.getInstance().putString("gcm_token", token);
    }

    public static String getGCMToken() {
        return SharedPreferenceHelper.getInstance().getString("gcm_token", "");
    }

    public static void setConfigPushSound(boolean enable) {
        SharedPreferenceHelper.getInstance().putBool("enable_push_sound", enable);
    }

    public static boolean isConfigPushSound() {
        return SharedPreferenceHelper.getInstance().getBool("enable_push_sound", true);
    }

    public static void setConfigPushEnable(boolean enable) {
        SharedPreferenceHelper.getInstance().putBool("enable_push", enable);
    }

    public static boolean isConfigPushEnable() {
        return SharedPreferenceHelper.getInstance().getBool("enable_push", true);
    }

    public static boolean isRegisterPushNot2Server() {
        return SharedPreferenceHelper.getInstance().getBool("is_registered_push_not", true);
    }

    public static void setRegisterPushNot2Server(boolean isRegistered) {
        SharedPreferenceHelper.getInstance().putBool("is_registered_push_not", isRegistered);
    }

    public static boolean isUnregisterPushNot2Server() {
        return SharedPreferenceHelper.getInstance().getBool("is_unregistered_push_not", true);
    }

    public static void setUnregisterPushNot2Server(boolean isUnregistered) {
        SharedPreferenceHelper.getInstance().putBool("is_unregistered_push_not", isUnregistered);
    }

    public static CurrentUser getUser() {
        return user;
    }
}
