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
    public static boolean ar_got = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive was triggered");
        Toast.makeText(context, "onReceive was triggered",
                Toast.LENGTH_LONG).show();
        ar_got = true;

        long id = -1;
        Bundle extras = intent.getExtras();
        if (extras != null)
            id = extras.getLong("rowId", -1);

        //        intent.getAction()

        // add function?
        // public Item ItemAdaptor.getItem(long id)
        // or use?
        // DatabaseHelper.getItem(id)


        // do something

    }
}
