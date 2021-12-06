package com.irayasoft.pakkruti.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferenceHelper {
    //handling functionality for cache storage
    //singleton class
    private static final String PREF_TIME="pref_time";
    private static  SharedPreferenceHelper INSTANCE;
    private SharedPreferences sharedPreferences;
    public SharedPreferenceHelper(Context context) {
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static  SharedPreferenceHelper getInstance(Context context){
        if(INSTANCE==null){
            INSTANCE=new SharedPreferenceHelper(context);

        }
            return INSTANCE;
    }
    //handling time for cache
    public void saveUpdateTime(long time){
        sharedPreferences.edit().putLong(PREF_TIME,time).apply();

    }
    public Long getUpdatedTime(){
        return sharedPreferences.getLong(PREF_TIME,0);

    }
}
