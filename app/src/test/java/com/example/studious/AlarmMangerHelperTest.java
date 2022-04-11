package com.example.studious;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;
import org.junit.*;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowAlarmManager;
import org.robolectric.shadows.ShadowLog;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;
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
        Log.d(TAG, "setUp was triggered");
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
    public void createPendingIntent(){
        int id = 1;
        PendingIntent pendingIntent = alarmMangerHelper.createPendingIntent(id);
        assertNotNull(pendingIntent);

        pendingIntent = alarmMangerHelper.createPendingIntent(-1);
        assertNull(pendingIntent);
    }

    @Test
    public void findPendingIntent(){
        int id = 1;
        PendingIntent pendingIntent = alarmMangerHelper.createPendingIntent(id);
        assertNotNull(pendingIntent);

        pendingIntent = alarmMangerHelper.findPendingIntent(id);
        assertNotNull(pendingIntent);

        pendingIntent = alarmMangerHelper.findPendingIntent(-1);
        assertNull(pendingIntent);

        pendingIntent = alarmMangerHelper.findPendingIntent(9);
        assertNull(pendingIntent);
    }

    @Test
    public void createRepeating() {
        int id0 = 0;
        int id1 = 1;
        int id2 = 2;
        int id3 = 3;

        Intent intent = new Intent(context, AlertReceiver.class);
        assertNotNull(intent);

        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 0);

        Calendar c = Calendar.getInstance();
        alarmMangerHelper.createRepeating(c, id1);
        alarmMangerHelper.createRepeating(c, id2);
        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 2);

        Assert.assertNull(alarmMangerHelper.findPendingIntent(id0));
        Assert.assertNotNull(alarmMangerHelper.findPendingIntent(id1));
        Assert.assertNotNull(alarmMangerHelper.findPendingIntent(id2));
        Assert.assertNull(alarmMangerHelper.findPendingIntent(id3));

        alarmMangerHelper.cancelAlarm(id2);
        assertEquals(1, shadowAlarmManager.getScheduledAlarms().size());
        Assert.assertNull(alarmMangerHelper.findPendingIntent(id2));

        alarmMangerHelper.cancelAlarm(id1);
        assertEquals(0, shadowAlarmManager.getScheduledAlarms().size());
        Assert.assertNull(alarmMangerHelper.findPendingIntent(id1));
    }

    @Test
    public void createInexactRepeating() {
        int id0 = 0;
        int id1 = 1;
        int id2 = 2;
        int id3 = 3;

        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 0);

        Calendar c = Calendar.getInstance();
        alarmMangerHelper.createInexactRepeating(c, id1);
        alarmMangerHelper.createInexactRepeating(c, id2);
        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 2);

        Assert.assertNull(alarmMangerHelper.findPendingIntent(id0));
        Assert.assertNotNull(alarmMangerHelper.findPendingIntent(id1));
        Assert.assertNotNull(alarmMangerHelper.findPendingIntent(id2));
        Assert.assertNull(alarmMangerHelper.findPendingIntent(id3));

        alarmMangerHelper.cancelAlarm(id2);
        assertEquals(1, shadowAlarmManager.getScheduledAlarms().size());
        Assert.assertNull(alarmMangerHelper.findPendingIntent(id2));

        alarmMangerHelper.cancelAlarm(id1);
        assertEquals(0, shadowAlarmManager.getScheduledAlarms().size());
        Assert.assertNull(alarmMangerHelper.findPendingIntent(id1));
    }

    @Test
    public void createAlarm() {
        int id0 = 0;
        int id1 = 1;
        int id2 = 2;
        int id3 = 3;

        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 0);

        Calendar c = Calendar.getInstance();
        alarmMangerHelper.createAlarm(c, id1);
        alarmMangerHelper.createAlarm(c, id2);
        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 2);

        Assert.assertNull(alarmMangerHelper.findPendingIntent(id0));
        Assert.assertNotNull(alarmMangerHelper.findPendingIntent(id1));
        Assert.assertNotNull(alarmMangerHelper.findPendingIntent(id2));
        Assert.assertNull(alarmMangerHelper.findPendingIntent(id3));

        alarmMangerHelper.cancelAlarm(id2);
        assertEquals(1, shadowAlarmManager.getScheduledAlarms().size());
        Assert.assertNull(alarmMangerHelper.findPendingIntent(id2));

        alarmMangerHelper.cancelAlarm(id1);
        assertEquals(0, shadowAlarmManager.getScheduledAlarms().size());
        Assert.assertNull(alarmMangerHelper.findPendingIntent(id1));
    }

    @Test
    public void createCalendarAlert() {
        int id0 = 0;
        int id1 = 1;
        int id2 = 2;
        int id3 = 3;

        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 0);

        Calendar c = Calendar.getInstance();
        alarmMangerHelper.createCalendarAlert(c, id1);
        alarmMangerHelper.createCalendarAlert(c, id2);
        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 2);

        Assert.assertNull(alarmMangerHelper.findPendingIntent(id0));
        Assert.assertNotNull(alarmMangerHelper.findPendingIntent(id1));
        Assert.assertNotNull(alarmMangerHelper.findPendingIntent(id2));
        Assert.assertNull(alarmMangerHelper.findPendingIntent(id3));

        alarmMangerHelper.cancelAlarm(id2);
        assertEquals(1, shadowAlarmManager.getScheduledAlarms().size());
        Assert.assertNull(alarmMangerHelper.findPendingIntent(id2));

        alarmMangerHelper.cancelAlarm(id1);
        assertEquals(0, shadowAlarmManager.getScheduledAlarms().size());
        Assert.assertNull(alarmMangerHelper.findPendingIntent(id1));
    }

    @Test
    public void createAlert() {
        int id0 = 0;
        int id1 = 1;
        int id2 = 2;
        int id3 = 3;

        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 0);

        Calendar c = Calendar.getInstance();
        alarmMangerHelper.createAlert(c, id1);
        alarmMangerHelper.createAlert(c, id2);
        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 2);

        Assert.assertNull(alarmMangerHelper.findPendingIntent(id0));
        Assert.assertNotNull(alarmMangerHelper.findPendingIntent(id1));
        Assert.assertNotNull(alarmMangerHelper.findPendingIntent(id2));
        Assert.assertNull(alarmMangerHelper.findPendingIntent(id3));

        alarmMangerHelper.cancelAlarm(id2);
        assertEquals(1, shadowAlarmManager.getScheduledAlarms().size());
        Assert.assertNull(alarmMangerHelper.findPendingIntent(id2));

        alarmMangerHelper.cancelAlarm(id1);
        assertEquals(0, shadowAlarmManager.getScheduledAlarms().size());
        Assert.assertNull(alarmMangerHelper.findPendingIntent(id1));
    }

    @Test
    public void cancelAlarm() {
        int id1 = 1;
        int id2 = 2;

        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 0);

        Calendar c = Calendar.getInstance();
        alarmMangerHelper.createAlarm(c, id1);
        alarmMangerHelper.createAlarm(c, id2);
        assertEquals(shadowAlarmManager.getScheduledAlarms().size(), 2);
        Assert.assertNotNull(alarmMangerHelper.findPendingIntent(id1));
        Assert.assertNotNull(alarmMangerHelper.findPendingIntent(id2));

        alarmMangerHelper.cancelAlarm(id2);
        assertEquals(1, shadowAlarmManager.getScheduledAlarms().size());
        Assert.assertNull(alarmMangerHelper.findPendingIntent(id2));

        alarmMangerHelper.cancelAlarm(id1);
        assertEquals(0, shadowAlarmManager.getScheduledAlarms().size());
        Assert.assertNull(alarmMangerHelper.findPendingIntent(id1));
    }

    @Ignore("test doesn't work. receiver is never called.")
    @Test
    public void alertReceiverOnReceive() throws InterruptedException {
        Log.d(TAG, "alertReceiverOnCreate was triggered");
        int id0 = 0;
        Calendar c = Calendar.getInstance();
        alarmMangerHelper.createAlarm(c, id0);
        Thread.sleep(2000);
        Log.d(TAG, "AlertReceiver id " + AlertReceiver.utid);
        assertEquals(id0, AlertReceiver.utid);
    }



}