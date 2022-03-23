package com.example.studious;

import android.content.Context;
import android.util.Log;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AlertReceiverTest {
    private static final String TAG = "AlertReceiverTest";

    @Test
    public void onReceive() throws InterruptedException {
        Log.d(TAG, "createAlarm was triggered");
        int id1 = 17;
        //        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Context context = ApplicationProvider.getApplicationContext();
        Calendar c = Calendar.getInstance();
        AlarmMangerHelper alarmMangerHelper = new AlarmMangerHelper(context);
        alarmMangerHelper.createAlarm(c, id1);
        Thread.sleep(2000);

        Log.d(TAG, "AlertReceiver id  " + AlertReceiver.id);
        /* NOTE:This assert will fail if run as junit
           has to be run on virtual device. */
        assertEquals(id1, AlertReceiver.id);
    }


}