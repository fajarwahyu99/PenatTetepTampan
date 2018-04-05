package com.example.infolabsolution.lastmoviejar;

import android.content.SharedPreferences;
import android.content.Context;



public class AppPreference {

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor sharededitor;

    public AppPreference(Context context) {
        sharedpreferences = context.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE);
        sharededitor = sharedpreferences.edit();
    }

    public boolean isUpcoming(){
        return sharedpreferences.getBoolean(BuildConfig.KEY_UPCOMING,false);
    }

    public boolean isDaily(){
        return sharedpreferences.getBoolean(BuildConfig.KEY_DAILY, false);
    }

    public void setDaily(boolean status){
        sharededitor.putBoolean(BuildConfig.KEY_DAILY, status);
        sharededitor.apply();
    }
    public void setUpcoming(boolean status){
        sharededitor.putBoolean(BuildConfig.KEY_UPCOMING, status);
        sharededitor.apply();
    }


}
