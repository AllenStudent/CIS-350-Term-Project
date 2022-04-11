package com.example.studious;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Class to help with setting system Alarms through AlarmManager
 */
public class AlarmMangerHelper {
    private static final String TAG = "AlarmMangerHelper";
    public static int utid = -1; // for unit tests

    /** Manager of system alarms. **/
    private final AlarmManager alarmManager;

    /** Applications system context. **/
    private final Context context;

    /**
     * constructor for AlarmManagerHelper
     *
     * @param context Application Context
     */
    public AlarmMangerHelper(Context context) {
        this.context = context.getApplicationContext();
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    /**
     * Create a unique pending (system token) intent for this item.
     * <p>
     * Since we're not using a filter, this is going to use our unique
     * row id for a unique request code. id is also stored in intent
     * data for retrieval from the broadcast receiver.
     *
     * @param id unique id. Use row id from database.
     * @return PendingIntent
     */
    public PendingIntent createPendingIntent(int id) {
        if (id < 0)
            return null;

        Intent intent = new Intent(this.context, AlertReceiver.class);
        intent.putExtra(DataBaseHelper.COL_ID, id);

        return PendingIntent.getBroadcast(
                this.context,
                id, // REQUEST_CODE,
                intent,
                0);
    }

    /**
     * Find (an alarm) Pending intent for id.
     * <p>
     * Not a check for an existing alarm but (it's close enough for
     * hand grenades) we are going to use it that way.
     *
     * @param id id unique id. Use row id from database.
     * @return found PendingIntent. Not null if alarm exists.
     */
    public PendingIntent findPendingIntent(int id) {
        if (id < 0)
            return null;
        Intent intent = new Intent(this.context, AlertReceiver.class);
        return PendingIntent.getBroadcast(
                this.context,
                id,
                intent,
                PendingIntent.FLAG_NO_CREATE // return null in intent doesn't exist
        );
    }

    /**
     * Schedule a repeating alarm.
     *
     * @param c  Calendar item with date and time information.
     * @param id database (unique) row id of item.
     */
    public void createRepeating(Calendar c, int id) {
        PendingIntent pendingIntent = createPendingIntent(id);
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,  // wake up device
                //                AlarmManager.RTC,  // don't wake up device
                c.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );
    }

    /**
     * Schedule a repeating alarm that has inexact trigger time requirements
     *
     * @param c  Calendar item with date and time information.
     * @param id database (unique) row id of item.
     */
    public void createInexactRepeating(Calendar c, int id) {
        PendingIntent pendingIntent = createPendingIntent(id);
        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,  // wake up device
                //                AlarmManager.RTC,  // don't wake up device
                c.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );
    }

    /**
     * Create Alarm callback.
     * <p>
     * Sets system alarm Icon.
     * Will fire if phone is in doze mode.
     *
     * @param c Calendar item with date and time information.
     */
    public void createAlarm(Calendar c, int id) {
        Log.d(TAG, "createAlarm was triggered");
        PendingIntent pendingIntent = createPendingIntent(id);

        /*
        This method is like setExact(int, long, android.app.PendingIntent),
        but implies RTC_WAKEUP.
        sets alarm icon and wakes in doze mode
         */
        AlarmManager.AlarmClockInfo alarmClockInfo =
                new AlarmManager.AlarmClockInfo(
                        c.getTimeInMillis(), pendingIntent
                );
        alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);
    }

    /**
     * Create Calendar Alert Callback
     * <p>
     * Does not set system alarm Icon.
     * Will fire if phone is in doze mode.
     *
     * @param c Calendar item with date and time information.
     */
    public void createCalendarAlert(Calendar c, int id) {
        Log.d(TAG, "createCalendarAlert was triggered");
        PendingIntent pendingIntent = createPendingIntent(id);

        // setExact for Alarms only
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    /**
     * Create Alert Callback.
     * <p>
     * Will not fire in doze mode.
     * Will not be called before time.
     * System will batch these together.
     * May be delayed until system convenient
     *
     * @param c Calendar item with date and time information.
     */
    public void createAlert(Calendar c, int id) {
        PendingIntent pendingIntent = createPendingIntent(id);
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    /**
     * cancel an alarm
     *
     * @param id unique (row) id of item.
     */
    public void cancelAlarm(int id) {
        PendingIntent pendingIntent = findPendingIntent(id);
        if (null != pendingIntent)
        {
            /* belt */
            alarmManager.cancel(pendingIntent);
            /* suspenders */
            pendingIntent.cancel();
        }
    }

    /**
     * Process and item to add, delete, or modify an alarm.
     *
     * @param item instance of Items class.
     * @return true if successful. false on error.
     */
    public boolean processItem(Items item) {
        int id = item.getId();
        int type = item.getType();

        if (id < 0)
            return false;

        boolean alarmExist = (null == findPendingIntent(id));
        //  process item attributes to see if it needs an alarm
        boolean needsAlarm = false; // check if item should have active alarm

        switch (type)
        {
            case Items.TYPE_ALARM:
                break;
            case Items.TYPE_CALENDAR:
                break;
            case Items.TYPE_TODO:
                break;
            case Items.TYPE_REMINDER:
                break;
            default:
                /* bad */
                return false;
        }

        if (needsAlarm && !alarmExist)
        {
            // create alarm
        }
        else if (!needsAlarm && alarmExist)
        {
            cancelAlarm(id);
        }
        else if (needsAlarm && alarmExist)
        {
            // is same data
            // do nothing

            // updated data
            // update alarm

            // or delete and recreate.
        }
        return true;
    }

    /**
     * Properly handle a triggered alarm.
     *
     * @param id row id of item to handle.
     */
    public void handleCallback(int id) {
        Log.d(TAG, "handleCallback id " + id);
        AlarmMangerHelper.utid = id; // for unit test

        // not sure but this might be dangerous having 2 helper
        DataBaseHelper dataBaseHelper = new DataBaseHelper(this.context);

        Items item = dataBaseHelper.getItem(id);
        if (null == item)
            return;

        NotificationHelper notificationHelper = new NotificationHelper(this.context);

        switch (item.getType())
        {
            case Items.TYPE_ALARM:
                notificationHelper.sendAlarmNotification(item);
                break;

            case Items.TYPE_CALENDAR:
                notificationHelper.sendCalendarNotification(item);
                break;

            case Items.TYPE_REMINDER:
                notificationHelper.sendReminderNotification(item);
                break;

            case Items.TYPE_TODO:
                notificationHelper.sendTodoNotification(item);
                break;
        }
    }

    /**
     * Recreate all alarms. Called on system reboot.
     */
    public void recreateAllAlarms() {
        Log.d(TAG, "recreateAllAlarms");

        // TODO: don't forget unit tests

        // pull and filter all items from database
        //      for each
        //          processItem(Items item)
    }
}


//    Calendar c = Calendar.getInstance();
//    c.set(Calendar.HOUR_OF_DAY, hourOfDay);
//    c.set(Calendar.MINUTE, minute);
//    c.set(Calendar.SECOND, 0);
// c.get(Calendar.DAY_OF_MONTH);


    /*  set()
        Deliver an alarm after a specific time
        If your app calls set(), setInexactRepeating(), or setAndAllowWhileIdle(),
        the alarm never goes off before the supplied trigger time.
     */

/*  setAndAllowWhileIdle() -  fire in doze mode

 */

    /*  setWindow()
        Deliver an alarm during a time window
        If your app calls setWindow(), the alarm never goes off before
        the supplied trigger time. Unless any battery-saving restrictions
        are in effect, the alarm is delivered within the specified time
         window, starting from the given trigger time.
     */

    /*
        Set an exact alarm
        The system invokes an exact alarm at a precise moment in the
        future. If your app targets Android 12 (API level 31) or higher,
        you must declare the "Alarms & reminders" special app access;
        otherwise, a SecurityException occurs.

        setExact() - alarm unless sleepy (deferred until next window)
            Invoke an alarm at a nearly precise time in the future,
            as long as other battery-saving measures aren't in effect.

            Use this method to set exact alarms, unless your app's work is
             time-critical for the user.

        setExactAndAllowWhileIdle() - exact and fire in doze
            Invoke an alarm at a nearly precise time in the future,
            even if battery-saving measures are in effect.

        setAlarmClock() - triggers in doze mode, sets system alarm clock
            Invoke an alarm at a precise time in the future.
            Because these alarms are highly visible to users,
            the system never adjusts their delivery time.
            The system identifies these alarms as the most critical
            ones and leaves low-power modes if necessary to deliver
            the alarms.
     */


   /*  setInexactRepeating()
        Deliver a repeating alarm at roughly regular intervals
        If your app calls setInexactRepeating(), the system invokes multiple alarms:

            1. The first alarm goes off within the specified time window,
            starting from the given trigger time.

            2. Subsequent alarms go off, on average, after the specified
            time window elapses. The time between two consecutive
            invocations of the alarm can vary.
     */

    /*
        setRepeating(int type, long triggerAtMillis, long intervalMillis,
        PendingIntent operation)

        Schedule a repeating alarm.
     */