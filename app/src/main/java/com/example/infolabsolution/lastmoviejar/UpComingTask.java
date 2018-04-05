package com.example.infolabsolution.lastmoviejar;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;



public class UpComingTask {

    private GcmNetworkManager mGcmNetworkManager;

    public UpComingTask(Context context) {
        mGcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodicTask() {
        Task periodicTask = new PeriodicTask.Builder()
                .setService(UpComingReminderMovie.class)
                .setPeriod(60)
                .setFlex(10)
                .setTag(UpComingReminderMovie.TAG_TASK_UPCOMING)
                .setPersisted(true)
                .build();
        mGcmNetworkManager.schedule(periodicTask);
    }

    public void cancelPeriodicTask() {
        if (mGcmNetworkManager != null) {
            mGcmNetworkManager.cancelTask(UpComingReminderMovie.TAG_TASK_UPCOMING, UpComingReminderMovie.class);
        }
    }
}

