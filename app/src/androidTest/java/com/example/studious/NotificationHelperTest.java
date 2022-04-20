package com.example.studious;

import android.content.Context;
import android.util.Log;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class NotificationHelperTest {
    private static final String TAG = "NotificationHelperTest";

    @Test
    public void sendCalendarNotification() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Items item = new Items(17, "calendar title", Items.TYPE_CALENDAR, "here", "12:30", "13:00", "04/15/2022", "04/16/2022");
        notificationHelper.sendCalendarNotification(item);
    }

    @Test
    public void sendTodoNotification() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Items item = new Items(18, "todo title", Items.TYPE_TODO, "here", "12:30", "13:00", "04/15/2022", "04/16/2022");
        notificationHelper.sendTodoNotification(item);
    }

    @Test
    public void sendReminderNotification() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Items item = new Items(19, "reminder title", Items.TYPE_REMINDER, "here", "12:30", "13:00", "04/15/2022", "04/16/2022");
        notificationHelper.sendReminderNotification(item);
    }

    @Test
    public void sendAlarmNotification() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Items item = new Items(19, "alarm title", Items.TYPE_ALARM, "here", "12:30", "13:00", "04/15/2022", "04/16/2022");
        notificationHelper.sendAlarmNotification(item);
    }

//    @Ignore("Temp disable")
    @Test
    public void fullStackCalendar() throws InterruptedException {
        Log.d(TAG, "fullStackCalendar");

        AlertReceiver.utid = -1;
        AlarmMangerHelper.utid = -1;

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DataBaseHelper databaseHelper = new DataBaseHelper(context);
        Items newItem = new Items(-1, "fullStackCalendar", Items.TYPE_CALENDAR, "here", "12:30", "13:00", "04/15/2022", "04/16/2022");
        int row_id = databaseHelper.addItem(newItem);

        Calendar c = Calendar.getInstance();
        AlarmMangerHelper alarmMangerHelper = new AlarmMangerHelper(context);
        alarmMangerHelper.createAlarm(c, row_id);

        Thread.sleep(2000);

        Log.d(TAG, "AlertReceiver id  " + AlertReceiver.utid);
        Log.d(TAG, "AlarmMangerHelper id  " + AlarmMangerHelper.utid);
        /* NOTE: Will fail if run as junit has to be run on virtual device. */
        assertEquals(row_id, AlertReceiver.utid);
        assertEquals(row_id, AlarmMangerHelper.utid);
    }

//    @Ignore("Temp disable")
    @Test
    public void fullStackReminder() throws InterruptedException {
        Log.d(TAG, "fullStackReminder");

        AlertReceiver.utid = -1;
        AlarmMangerHelper.utid = -1;

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DataBaseHelper databaseHelper = new DataBaseHelper(context);
        Items newItem = new Items(-1, "fullStackReminder", Items.TYPE_REMINDER, "here", "12:30", "13:00", "04/15/2022", "04/16/2022");
        int row_id = databaseHelper.addItem(newItem);

        Calendar c = Calendar.getInstance();
        AlarmMangerHelper alarmMangerHelper = new AlarmMangerHelper(context);
        alarmMangerHelper.createAlarm(c, row_id);

        Thread.sleep(2000);

        Log.d(TAG, "AlertReceiver id  " + AlertReceiver.utid);
        Log.d(TAG, "AlarmMangerHelper id  " + AlarmMangerHelper.utid);
        /* NOTE: Will fail if run as junit has to be run on virtual device. */
        assertEquals(row_id, AlertReceiver.utid);
        assertEquals(row_id, AlarmMangerHelper.utid);
    }

//    @Ignore("Temp disable")
    @Test
    public void fullStackAlarm() throws InterruptedException {
        Log.d(TAG, "fullStackAlarm");

        AlertReceiver.utid = -1;
        AlarmMangerHelper.utid = -1;

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DataBaseHelper databaseHelper = new DataBaseHelper(context);
        Items newItem = new Items(-1, "fullStackAlarm", Items.TYPE_ALARM, "here", "12:30", "13:00", "04/15/2022", "04/16/2022");
        int row_id = databaseHelper.addItem(newItem);

        Calendar c = Calendar.getInstance();
        AlarmMangerHelper alarmMangerHelper = new AlarmMangerHelper(context);
        alarmMangerHelper.createAlarm(c, row_id);

        Thread.sleep(2000);

        Log.d(TAG, "AlertReceiver id  " + AlertReceiver.utid);
        Log.d(TAG, "AlarmMangerHelper id  " + AlarmMangerHelper.utid);
        /* NOTE: Will fail if run as junit has to be run on virtual device. */
        assertEquals(row_id, AlertReceiver.utid);
        assertEquals(row_id, AlarmMangerHelper.utid);
    }

//    @Ignore("Temp disable")
    @Test
    public void fullStackTodo() throws InterruptedException {
        Log.d(TAG, "fullStackTodo");

        AlertReceiver.utid = -1;
        AlarmMangerHelper.utid = -1;

        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DataBaseHelper databaseHelper = new DataBaseHelper(context);
        Items newItem = new Items(-1, "fullStackTodo", Items.TYPE_TODO, "here", "12:30", "13:00", "04/15/2022", "04/16/2022");
        int row_id = databaseHelper.addItem(newItem);

        Calendar c = Calendar.getInstance();
        AlarmMangerHelper alarmMangerHelper = new AlarmMangerHelper(context);
        alarmMangerHelper.createAlarm(c, row_id);

        Thread.sleep(2000);

        Log.d(TAG, "AlertReceiver id  " + AlertReceiver.utid);
        Log.d(TAG, "AlarmMangerHelper id  " + AlarmMangerHelper.utid);
        /* NOTE: Will fail if run as junit has to be run on virtual device. */
        assertEquals(row_id, AlertReceiver.utid);
        assertEquals(row_id, AlarmMangerHelper.utid);
    }
}