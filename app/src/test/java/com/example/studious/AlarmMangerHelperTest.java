package com.example.studious;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowAlarmManager;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowLog;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.Shadows.shadowOf;

/*
 NOTE: for coverage has to be run as JUNIT test.
 (the red green back to back arrows. under edit configurations)
 add to jvm options
 -ea -noverify
 */


@RunWith(RobolectricTestRunner.class)
public class AlarmMangerHelperTest {
    private static final String TAG = "AlarmMangerHelperTest";

    static
    {
        ShadowLog.stream = System.out;
    }


    private Context context;
    private AlarmManager alarmManager;
    private ShadowAlarmManager shadowAlarmManager;
    private AlarmMangerHelper alarmMangerHelper;


    @Before
    public void setUp() throws Exception {
        context = RuntimeEnvironment.getApplication();
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        shadowAlarmManager = shadowOf(alarmManager);
        alarmMangerHelper = new AlarmMangerHelper(context);
        Log.d(TAG, "setUp was triggered");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSetup() {
        assertNotNull(context);
        assertNotNull(alarmManager);
        assertNotNull(shadowAlarmManager);
        assertNotNull(alarmMangerHelper);
    }

    @Test
    public void testForReceiver() {
        Intent intent = new Intent(context, AlertReceiver.class);
        assertNotNull(intent);
        //        int id1 = 1;
        //        Calendar c = Calendar.getInstance();
        //        alarmMangerHelper.createAlarm(c, id1);

        PackageManager packageManager = context.getPackageManager();
        assertNotNull(packageManager);
        List<ResolveInfo> receivers = packageManager.queryBroadcastReceivers(
                intent,
                PackageManager.GET_RESOLVED_FILTER);
        assertNotNull(receivers);
        assertEquals(1, receivers.size());
        Assert.assertFalse(receivers.isEmpty());

        //        alarmMangerHelper.cancelAlarm(c, id1);


    }

    @Test
    public void createRepeating() {
        int id0 = 0;
        int id1 = 1;
        int id2 = 2;
        int id3 = 3;
        PendingIntent pendingIntent;

        Intent intent = new Intent(context, AlertReceiver.class);
        assertNotNull(intent);

        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 0);

        Calendar c = Calendar.getInstance();
        alarmMangerHelper.createRepeating(c, id1);
        alarmMangerHelper.createRepeating(c, id2);
        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 2);

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id0,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNull(pendingIntent);

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id1,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNotNull(pendingIntent);
        pendingIntent.cancel();

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id2,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNotNull(pendingIntent);
        pendingIntent.cancel();

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id3,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNull(pendingIntent);

        alarmMangerHelper.cancelAlarm(c, id2);
        assertEquals(1, shadowAlarmManager.getScheduledAlarms().size());

        alarmMangerHelper.cancelAlarm(c, id1);
        assertEquals(0, shadowAlarmManager.getScheduledAlarms().size());
    }

    @Test
    public void createInexactRepeating() {
        int id0 = 0;
        int id1 = 1;
        int id2 = 2;
        int id3 = 3;
        PendingIntent pendingIntent;

        Intent intent = new Intent(context, AlertReceiver.class);
        assertNotNull(intent);

        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 0);

        Calendar c = Calendar.getInstance();
        alarmMangerHelper.createInexactRepeating(c, id1);
        alarmMangerHelper.createInexactRepeating(c, id2);
        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 2);

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id0,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNull(pendingIntent);

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id1,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNotNull(pendingIntent);
        pendingIntent.cancel();

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id2,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNotNull(pendingIntent);
        pendingIntent.cancel();

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id3,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNull(pendingIntent);

        alarmMangerHelper.cancelAlarm(c, id2);
        assertEquals(1, shadowAlarmManager.getScheduledAlarms().size());

        alarmMangerHelper.cancelAlarm(c, id1);
        assertEquals(0, shadowAlarmManager.getScheduledAlarms().size());
    }

    @Test
    public void createAlarm() {
        int id0 = 0;
        int id1 = 1;
        int id2 = 2;
        int id3 = 3;
        PendingIntent pendingIntent;

        Intent intent = new Intent(context, AlertReceiver.class);
        assertNotNull(intent);

        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 0);

        Calendar c = Calendar.getInstance();
        alarmMangerHelper.createAlarm(c, id1);
        alarmMangerHelper.createAlarm(c, id2);
        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 2);

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id0,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNull(pendingIntent);

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id1,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNotNull(pendingIntent);
        pendingIntent.cancel();

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id2,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNotNull(pendingIntent);
        pendingIntent.cancel();

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id3,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNull(pendingIntent);

        alarmMangerHelper.cancelAlarm(c, id2);
        assertEquals(1, shadowAlarmManager.getScheduledAlarms().size());

        alarmMangerHelper.cancelAlarm(c, id1);
        assertEquals(0, shadowAlarmManager.getScheduledAlarms().size());
    }

    @Test
    public void createCalendarAlert() {
        int id0 = 0;
        int id1 = 1;
        int id2 = 2;
        int id3 = 3;
        PendingIntent pendingIntent;

        Intent intent = new Intent(context, AlertReceiver.class);
        assertNotNull(intent);

        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 0);

        Calendar c = Calendar.getInstance();
        alarmMangerHelper.createCalendarAlert(c, id1);
        alarmMangerHelper.createCalendarAlert(c, id2);
        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 2);

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id0,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNull(pendingIntent);

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id1,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNotNull(pendingIntent);
        pendingIntent.cancel();

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id2,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNotNull(pendingIntent);
        pendingIntent.cancel();

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id3,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNull(pendingIntent);

        alarmMangerHelper.cancelAlarm(c, id2);
        assertEquals(1, shadowAlarmManager.getScheduledAlarms().size());

        alarmMangerHelper.cancelAlarm(c, id1);
        assertEquals(0, shadowAlarmManager.getScheduledAlarms().size());
    }

    @Test
    public void createAlert() {
        int id0 = 0;
        int id1 = 1;
        int id2 = 2;
        int id3 = 3;
        PendingIntent pendingIntent;

        Intent intent = new Intent(context, AlertReceiver.class);
        assertNotNull(intent);

        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 0);

        Calendar c = Calendar.getInstance();
        alarmMangerHelper.createAlert(c, id1);
        alarmMangerHelper.createAlert(c, id2);
        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 2);

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id0,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNull(pendingIntent);

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id1,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNotNull(pendingIntent);
        pendingIntent.cancel();

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id2,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNotNull(pendingIntent);
        pendingIntent.cancel();

        pendingIntent = PendingIntent.getBroadcast(
                context,
                id3,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
        Assert.assertNull(pendingIntent);

        alarmMangerHelper.cancelAlarm(c, id2);
        assertEquals(1, shadowAlarmManager.getScheduledAlarms().size());

        alarmMangerHelper.cancelAlarm(c, id1);
        assertEquals(0, shadowAlarmManager.getScheduledAlarms().size());
    }

    @Test
    public void cancelAlarm() {
        int id1 = 1;
        int id2 = 2;

        Intent intent = new Intent(context, AlertReceiver.class);
        assertNotNull(intent);
        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 0);

        Calendar c = Calendar.getInstance();
        alarmMangerHelper.createAlarm(c, id1);
        alarmMangerHelper.createAlarm(c, id2);
        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 2);

        alarmMangerHelper.cancelAlarm(c, id2);
        assertEquals(1, shadowAlarmManager.getScheduledAlarms().size());

        alarmMangerHelper.cancelAlarm(c, id1);
        assertEquals(0, shadowAlarmManager.getScheduledAlarms().size());
    }



}