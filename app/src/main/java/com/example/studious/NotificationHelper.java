package com.example.studious;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 *
 */
public class NotificationHelper {
    /** */
    private static final String TAG = "NotificationHelper";

    /** */
    public static final String CHANNEL_CAL = "calender notification";
    /** */
    public static final String CHANNEL_TODO = "todo notification";
    /** */
    public static final String CHANNEL_REMINDER = "reminder notification";
    /** */
    public static final String CHANNEL_ALARM = "alarm notification";

    private final NotificationManager manager;
    private final NotificationManagerCompat managerCompat;
    private final Context context;

    /**
     *
     * @param context
     */
    public NotificationHelper(Context context) {
        this.context = context.getApplicationContext();
        manager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
        managerCompat = NotificationManagerCompat.from(this.context);
    }

    /**
     *
     */
    public void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel cal = new NotificationChannel(CHANNEL_CAL, "Calendar notifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel todo = new NotificationChannel(CHANNEL_TODO, "Todo notifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel rem = new NotificationChannel(CHANNEL_REMINDER, "Reminder notifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel alarm = new NotificationChannel(CHANNEL_ALARM, "Alarm notifications", NotificationManager.IMPORTANCE_HIGH);

            cal.setDescription("Calendar alert notifications.");
            todo.setDescription("ToDo alert notifications.");
            rem.setDescription("Reminder alert notifications.");
            alarm.setDescription("Alarm alert notifications.");

            manager.createNotificationChannel(cal);
            manager.createNotificationChannel(todo);
            manager.createNotificationChannel(rem);
            manager.createNotificationChannel(alarm);
        }
    }

    /**
     *
     * @param item
     * @return
     */
    public Notification sendCalendarNotification(Items item) {
        // do something when notification is pressed.
        // https://www.youtube.com/watch?v=CZ575BuLBo4&list=PLrnPJCHvNZuCN52QwGu7YTSLIMrjCF0gM&index=2
        // new intent - item display class?
        // add id to extra data in intent
        // new pending intent
        // add pending intent to notification

        Notification notification = new NotificationCompat.Builder(this.context, CHANNEL_CAL)
                .setSmallIcon(R.drawable.ic_outline_calendar_month_24)
                .setContentTitle(item.getItemTitle())
                .setContentText(item.getItemNotes())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                //.setContentIntent(pendingIntent)
                //.setOnlyAlertOnce(true)
                //.setColor()
                .build();

        managerCompat.notify(item.getId(), notification);
        return notification;
    }

    /**
     * Send notificaton for todo items
     * @param item
     * @return
     */
    public Notification sendTodoNotification(Items item) {
        Notification notification = new NotificationCompat.Builder(this.context, CHANNEL_TODO)
                .setSmallIcon(R.drawable.ic_outline_check_circle_24)
                .setContentTitle(item.getItemTitle())
                .setContentText(item.getItemNotes())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                //.setContentIntent(pendingIntent)
                .build();

        managerCompat.notify(item.getId(), notification);
        return notification;
    }

    /**
     *
     * @param item
     * @return
     */
    public Notification sendReminderNotification(Items item) {
        Notification notification = new NotificationCompat.Builder(this.context, CHANNEL_REMINDER)
                .setSmallIcon(R.drawable.ic_outline_circle_notifications_24)
                .setContentTitle(item.getItemTitle())
                .setContentText(item.getItemNotes())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                //.setContentIntent(pendingIntent)
                .build();

        managerCompat.notify(item.getId(), notification);
        return notification;
    }

    /**
     *
     * @param item
     * @return
     */
    public Notification sendAlarmNotification(Items item) {
        Notification notification = new NotificationCompat.Builder(this.context, CHANNEL_ALARM)
                .setSmallIcon(R.drawable.ic_outline_access_alarm_24)
                .setContentTitle(item.getItemTitle())
                .setContentText(item.getItemNotes())
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                //.setContentIntent(pendingIntent)
                .build();

        managerCompat.notify(item.getId(), notification);
        return notification;
    }

    /**
     *
     * @param id
     */
    public void cancelNotification(int id) {
        managerCompat.cancel(id);
    }


}
