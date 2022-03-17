package com.example.studious;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Receive notification from system that a reboot has happened.
 * We need to recreate any active AlarmManager alarms.
 *
 * Needs to be EXPLICITLY turned on and off. This persists across reboots.
 * (Or obviously this wouldn't work.) Once on, will override manifest until
 * EXPLICITLY turned off.
 *
 * Implemented in
 * AndroidMangerHelper.turnOnReBootReceiver()
 * AndroidMangerHelper.turnOffReBootReceiver()
 *
 *
 *
 */
public class ReBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            // Set the alarm here.
        }
    }
}
