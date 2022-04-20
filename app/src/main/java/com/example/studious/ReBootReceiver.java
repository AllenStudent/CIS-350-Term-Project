package com.example.studious;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Receive notification from system that a reboot has happened.
 * We need to recreate any active AlarmManager alarms.
 *
 * filter for BOOT_COMPLETED is set in AndroidManifest.xml
 *
 *
 * https://www.youtube.com/playlist?list=PLrnPJCHvNZuBqr_0AS9BPXgU6gvNeai5S
 *
 *
 * adb shell su root am broadcast -a android.intent.action.BOOT_COMPLETED -p com.example.studious
 *  adb restart
 *  adb reboot
 */
public class ReBootReceiver extends BroadcastReceiver {
    /** */
    private static final String TAG = "ReBootReceiver";
    /** */
    public static boolean called  = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive was triggered");

        /* system reboot. recreate alarms. */
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Log.d(TAG, "android.intent.action.BOOT_COMPLETED");
            ReBootReceiver.called = true; // for unit tests

            // reset alarms
            AlarmMangerHelper helper = new AlarmMangerHelper(context);
            helper.recreateAllAlarms();

        }
    }
}
