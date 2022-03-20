package com.example.studious;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

/**
 * Class to help with setting system Alarms through AlarmManager
 */
public class AlarmMangerHelper {
    /** Manager of system alarms. **/
    private final AlarmManager alarmManager;

    /** Applications system context. **/
    private final Context context;

    public AlarmMangerHelper(Context context) {
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
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


    public void createRepeating() {
    }

    public void createInexactRepeating() {
    }

    /**
     * Create Alarm callback.
     * <p>
     * Sets system alarm Icon.
     * Will fire if phone is in doze mode.
     *
     * @param c
     */
    //    public void createAlarm(Context context, Calendar c, long id) {
    public void createAlarm(Calendar c, long id) {
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra("id", id);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                (int)id, // REQUEST_CODE,
                intent,
                0);

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
    //    public void createCalendarAlert(Context context, Calendar c, long id) {
    public void createCalendarAlert(Calendar c, long id) {
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra("id", id);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                (int)id, //REQUEST_CODE,
                intent,
                0);

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
    public void createAlert(Calendar c, long id) {
        Intent intent = new Intent(context, AlertReceiver.class);
        intent.putExtra("id", id);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                (int)id, //REQUEST_CODE,
                intent,
                0);

        //
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    /**
     * cancel and alarm
     *
     * @param c
     * @param id
     */
    public void cancelAlarm(Calendar c, long id) {
        Intent intent = new Intent(context, AlertReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                (int)id, //REQUEST_CODE,
                intent,
                0);

        alarmManager.cancel(pendingIntent);
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
