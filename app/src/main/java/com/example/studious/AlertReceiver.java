package com.example.studious;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


/**
 * Receive broadcast for AlarmManager
 */
public class AlertReceiver extends BroadcastReceiver {
    private static final String TAG = "AlertReceiver";
    public static int utid = -1; // for unit tests

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive was triggered");

        Bundle extras = intent.getExtras();
        if (extras != null)
        {
            int id = extras.getInt(DataBaseHelper.COL_ID, -1);
            AlertReceiver.utid = id; // for unit tests
            Log.d(TAG, "onReceive rowId " + id);

            AlarmMangerHelper helper = new AlarmMangerHelper(context);
            helper.handleCallback(id);
        }



    }
}
