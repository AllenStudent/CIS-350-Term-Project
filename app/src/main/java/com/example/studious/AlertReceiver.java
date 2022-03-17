package com.example.studious;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Receive broadcast for AlarmManager
 */
public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        long id = -1;
        Bundle extras = intent.getExtras();
        if (extras != null)
            id = extras.getLong("rowId", -1);

        // add function?
        // public Item ItemAdaptor.getItem(long id)
        // or use?
        // DatabaseHelper.getItem(id)


        // do something

    }
}
