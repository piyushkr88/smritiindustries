package com.smriti.phoneguider.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.smriti.phoneguider.data.Constant;


/**
 * Created by Piyush on 9/14/2016.
 */
public class SharedPreferenceUtil {


    public static boolean getApplicationRatingData(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(Constant.APP_PREFERENCE_DB, Context.MODE_PRIVATE);

        boolean isratingProvided = prefs.getBoolean(Constant.KEY_SHARED_PREF_RATING_FLAG, false);
        return isratingProvided;
    }

    public static void setApplicationRatingData(Context mContext, boolean isratingProvided) {
        SharedPreferences prefs = mContext.getSharedPreferences(Constant.APP_PREFERENCE_DB, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(Constant.KEY_SHARED_PREF_RATING_FLAG, isratingProvided);
        edit.commit();
    }
}
