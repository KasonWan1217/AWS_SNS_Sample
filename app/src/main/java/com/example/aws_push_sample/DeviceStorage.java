package com.example.aws_push_sample;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class DeviceStorage {
    public static void storeApp_Reg_ID(String val, Activity activity) {
        storeStringToSharedPreferences(activity.getResources().getString(R.string.SHARED_PREF_KEY_App_Ref_ID), val, activity.getResources().getString(R.string.SHARED_PREF_FILE_PUSH_SERVICE_SETTING), activity);
    }

    public static void storeToken(String val, Activity activity) {
        storeStringToSharedPreferences(activity.getResources().getString(R.string.SHARED_PREF_KEY_DToken), val, activity.getResources().getString(R.string.SHARED_PREF_FILE_PUSH_SERVICE_SETTING), activity);
    }

    public static void storeOriToken(String val, Activity activity) {
        storeStringToSharedPreferences(activity.getResources().getString(R.string.SHARED_PREF_KEY_Ori_DToken), val, activity.getResources().getString(R.string.SHARED_PREF_FILE_PUSH_SERVICE_SETTING), activity);
    }

    public static String getStringFormConfigFile(String key, Activity activity) {
        return getStringFormSharedPreferences(key, activity.getResources().getString(R.string.SHARED_PREF_FILE_PUSH_SERVICE_SETTING), activity);
    }


    public static void storeStringToSharedPreferences(String key, String val, String file, Activity activity) {
        //saving the token on shared preferences
        SharedPreferences sharedPreferences = activity.getSharedPreferences(file, MODE_PRIVATE);
        SharedPreferences.Editor editors = sharedPreferences.edit();
        editors.putString(key, val);
        editors.commit();
        Log.e(TAG, "Stored in SharedPreferences");
    }

    public static String getStringFormSharedPreferences(String key, String file, Activity activity) {
        SharedPreferences prefs = activity.getSharedPreferences(file, MODE_PRIVATE);
        return prefs.getString(key, "");
    }
}
