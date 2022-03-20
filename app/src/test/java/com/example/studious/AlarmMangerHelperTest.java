package com.example.studious;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowAlarmManager;

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
    public void createRepeating() {
    }

    @Test
    public void createInexactRepeating() {
    }

    @Test
    public void createAlarm() {
        List<ShadowAlarmManager.ScheduledAlarm> alarms;
        PendingIntent pendingIntent;
        int id0 = 0;
        int id1 = 1;
        int id2 = 2;
        int id3 = 3;
        Intent intent = new Intent(context, AlertReceiver.class);

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
    }

    @Test
    public void createAlert() {
    }

    @Test
    public void cancelAlarm() {

    }
}