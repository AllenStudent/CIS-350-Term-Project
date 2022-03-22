package com.example.studious;

import android.content.Context;
import android.util.Log;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class AlertReceiverTest {
    private static final String TAG = "AlertReceiverTest";

    @Test
    public void onReceive() throws InterruptedException {
        Log.d(TAG, "createAlarm was triggered");
        AlertReceiver.ar_got = false;
        int id1 = 1;
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Calendar c = Calendar.getInstance();
        AlarmMangerHelper alarmMangerHelper = new AlarmMangerHelper(context);
        alarmMangerHelper.createAlarm(c, id1);
        Thread.sleep(2000);
        boolean r = AlertReceiver.ar_got;
        assertTrue(r);
        Log.d(TAG, "AlertReceiver.ar_got " + r);
    }


}