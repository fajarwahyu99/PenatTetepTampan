package com.example.infolabsolution.lastmoviejar;

import android.content.Context;
import android.content.SharedPreferences;



public class AppPreference {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public AppPreference(Context context) {
        preferences = context.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }
    public void setDaily(boolean status){
        editor.putBoolean(BuildConfig.KEY_DAILY, status);
        editor.apply();
    }
    public void setUpcoming(boolean status){
        editor.putBoolean(BuildConfig.KEY_UPCOMING, status);
        editor.apply();
    }



    public boolean isUpcoming(){
        return preferences.getBoolean(BuildConfig.KEY_UPCOMING,false);
    }

    public boolean isDaily(){
        return preferences.getBoolean(BuildConfig.KEY_DAILY, false);
    }
}
