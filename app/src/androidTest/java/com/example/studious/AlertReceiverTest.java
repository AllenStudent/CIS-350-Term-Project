package com.example.studious;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;


import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AlertReceiverTest {
    private static final String TAG = "AlertReceiverTest";

    @Test
    public void AlertReceiver() throws InterruptedException {
        Log.d(TAG, "createAlarm was triggered");
        int id1 = 17;
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        Context context = ApplicationProvider.getApplicationContext();
        Calendar c = Calendar.getInstance();
        AlarmMangerHelper alarmMangerHelper = new AlarmMangerHelper(context);
        alarmMangerHelper.createAlarm(c, id1);
        Thread.sleep(2000);

        Log.d(TAG, "AlertReceiver id  " + AlertReceiver.id);
        /* NOTE:This assert will fail if run as junit
           has to be run on virtual device. */
        assertEquals(id1, AlertReceiver.id);
    }

    @Ignore("No worky yet")
    @Test
    public void ReBootReceiver() throws InterruptedException {
        Log.d(TAG, "alertReBootReceiver was triggered");
        Context context = ApplicationProvider.getApplicationContext();
        AlarmMangerHelper alarmMangerHelper = new AlarmMangerHelper(context);
        Intent intent = new Intent(context, ReBootReceiver.class);




        // backup/get current state of receiver>
        ComponentName receiver = new ComponentName(context, ReBootReceiver.class);
        PackageManager pm = context.getPackageManager();
        int backup = pm.getComponentEnabledSetting(receiver);
        Log.d(TAG, "getComponentEnabledSetting " + backup);
        // on error or else this need to et restordd



        ReBootReceiver.called = false;
        LocalBroadcastManager
                .getInstance(context)
                .sendBroadcast(new Intent( Intent.ACTION_BOOT_COMPLETED));
//                .sendBroadcast(new Intent("android.intent.action.BOOT_COMPLETED"));
//        context.sendOrderedBroadcast ( intent,"android.intent.action.BOOT_COMPLETED");
        Thread.sleep(2000);
        Log.d(TAG, "ReBootReceiver.called " + ReBootReceiver.called);
        assertFalse("ReBootReceiver.called", ReBootReceiver.called);


    }



}