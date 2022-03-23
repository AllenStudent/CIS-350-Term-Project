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

    /** Manager of system alarms. **/
    private final AlarmManager alarmManager;

    /** Applications system context. **/
    private final Context context;

    public AlarmMangerHelper(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public PendingIntent createPendingIntent(int id) {
        if (id < 0)
            return null;

        Intent intent = new Intent(this.context, AlertReceiver.class);
        intent.putExtra("id", id);

        return PendingIntent.getBroadcast(
                this.context,
                id, // REQUEST_CODE,
                intent,
                0);
    }

    public PendingIntent findPendingIntent(int id) {
        if (id < 0)
            return null;
        Intent intent = new Intent(this.context, AlertReceiver.class);
        return PendingIntent.getBroadcast(
                this.context,
                id,
                intent,
                PendingIntent.FLAG_NO_CREATE
        );
    }


    public boolean processItem(Items item) {
        int id = item.getId();
        int type = item.getType();

        boolean alarmExist = (null == findPendingIntent(id));
        boolean needsAlarm = false; // check if item should have active alarm


        if (id < 0)
            return false;

        switch (type)
        {
            case MainActivity.TYPE_ALARM: // move teh Defs from MainActivity to Items??
                break;
            case MainActivity.TYPE_CALENDAR:
                break;
            case MainActivity.TYPE_TODO:
                break;
            case MainActivity.TYPE_REMINDER:
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


        return true;
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

        setExact() - alarm unless sleepy (defered until next window)
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

    /**
     * Schedule a repeating alarm.
     *
     * @param c
     * @param id
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
     * @param c
     * @param id
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
     * @param c
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
     * @param c
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
     * @param c
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
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }


    /**
     * Turn on reboot notification if we have any alarms we want to
     * persist across a reboot. (probably all of them.)
     */
    private void turnOnReBootReceiver() {
        //        ComponentName receiver = new ComponentName(context, ReBootReceiver.class);
        //        PackageManager pm = context.getPackageManager();
        //
        //        pm.setComponentEnabledSetting(receiver,
        //                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        //                PackageManager.DONT_KILL_APP);
    }

    /**
     * Turn off reboot notifications if we have no active alarms.
     */
    private void turnOffReBootReceiver() {
        //        ComponentName receiver = new ComponentName(context, ReBootReceiver.class);
        //        PackageManager pm = context.getPackageManager();
        //
        //        pm.setComponentEnabledSetting(receiver,
        //                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
        //                PackageManager.DONT_KILL_APP);
    }

}
