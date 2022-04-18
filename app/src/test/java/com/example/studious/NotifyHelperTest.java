package com.example.studious;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowNotificationManager;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class NotifyHelperTest {

    private static final String TAG = "NotifyHelperTest";
    private Context context;
    private NotificationHelper notificationHelper;
    private NotificationManager notificationManager;
    private ShadowNotificationManager shadowNotificationManager;

    @Before
    public void setUp() {
        Log.d(TAG, "setUp was triggered");
        context = RuntimeEnvironment.getApplication();
        notificationHelper = new NotificationHelper(context);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        shadowNotificationManager = shadowOf(notificationManager);
    }

    @Test
    public void testSetup() {
        assertNotNull(context);
        assertNotNull(notificationHelper);
        assertNotNull(notificationManager);
        assertNotNull(shadowNotificationManager);
    }

    @Test
    public void createNotificationChannels() {
        assertEquals(0, shadowNotificationManager.getNotificationChannels().size());
        notificationHelper.createNotificationChannels();
        assertEquals(4, shadowNotificationManager.getNotificationChannels().size());
    }

    @Test
    public void sendCalendarNotification() {
        int id = 17;
        Items item = new Items(id, "calendar title", Items.TYPE_CALENDAR, "notes", "12:30", "13:00", "04/15/2022", "04/16/2022");
        assertEquals(0, shadowNotificationManager.size());
        Notification notification = notificationHelper.sendCalendarNotification(item);
        assertEquals(1, shadowNotificationManager.size());
        assertEquals(notification, shadowNotificationManager.getNotification(null, id));
    }

    @Test
    public void sendTodoNotification() {
        int id = 18;
        Items item = new Items(id, "todo title", Items.TYPE_TODO, "notes", "12:30", "13:00", "04/15/2022", "04/16/2022");
        assertEquals(0, shadowNotificationManager.size());
        Notification notification = notificationHelper.sendTodoNotification(item);
        assertEquals(1, shadowNotificationManager.size());
        assertEquals(notification, shadowNotificationManager.getNotification(null, id));
    }

    @Test
    public void sendReminderNotification() {
        int id = 19;
        Items item = new Items(id, "reminder title", Items.TYPE_REMINDER, "notes", "12:30", "13:00", "04/15/2022", "04/16/2022");
        assertEquals(0, shadowNotificationManager.size());
        Notification notification = notificationHelper.sendReminderNotification(item);
        assertEquals(1, shadowNotificationManager.size());
        assertEquals(notification, shadowNotificationManager.getNotification(null, id));
    }

    @Test
    public void sendAlarmNotification() {
        int id = 20;
        Items item = new Items(id, "alarm title", Items.TYPE_ALARM, "notes", "12:30", "13:00", "04/15/2022", "04/16/2022");
        assertEquals(0, shadowNotificationManager.size());
        Notification notification = notificationHelper.sendAlarmNotification(item);
        assertEquals(1, shadowNotificationManager.size());
        assertEquals(notification, shadowNotificationManager.getNotification(null, id));
    }

    @Test
    public void cancelNotification() {
        int id = 21;
        Items item = new Items(id, "alarm title", Items.TYPE_ALARM, "notes", "12:30", "13:00", "04/15/2022", "04/16/2022");
        assertEquals(0, shadowNotificationManager.size());
        Notification notification = notificationHelper.sendAlarmNotification(item);
        assertEquals(1, shadowNotificationManager.size());
        assertEquals(notification, shadowNotificationManager.getNotification(null, id));
        notificationHelper.cancelNotification(id);
        assertEquals(0, shadowNotificationManager.size());
        assertNull(shadowNotificationManager.getNotification(null, id));
    }
}