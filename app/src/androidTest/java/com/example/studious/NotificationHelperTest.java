package com.example.studious;

import android.content.Context;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;

// these will crap out on junit tests. Has to be run on device.
// not really tests. just to run code on virtual device.

@RunWith(AndroidJUnit4.class)
public class NotificationHelperTest {
    private static final String TAG = "NotificationHelperTest";

    @Test
    public void sendCalendarNotification() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Items item = new Items(17, "calendar title", Items.TYPE_CALENDAR);
        notificationHelper.sendCalendarNotification(item);
    }

    @Test
    public void sendTodoNotification() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Items item = new Items(18, "todo title", Items.TYPE_TODO);
        notificationHelper.sendTodoNotification(item);
    }

    @Test
    public void sendReminderNotification() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Items item = new Items(19, "reminder title", Items.TYPE_REMINDER);
        notificationHelper.sendReminderNotification(item);
    }

    @Test
    public void sendAlarmNotification() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        NotificationHelper notificationHelper = new NotificationHelper(context);
        Items item = new Items(19, "alarm title", Items.TYPE_ALARM);
        notificationHelper.sendAlarmNotification(item);
    }
}