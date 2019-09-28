package uk.co.nickbloor.mountainweatherforecast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class ForecastUpdateScheduler {
    private static final int MORNING_ALARM_ID = 0;
    private static final int EVENING_ALARM_ID = 1;

    //Schedule alarms to deliver mountain weather forecast updates
    public static void scheduleForecastUpdates(Context context) {
        //Get the alarm manager service
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        //Start a daily morning alarm to generate enabled weather forecast notifications for the same day
        Intent intent = new Intent(context, ForecastNotificationDispatcher.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, MORNING_ALARM_ID, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, getAlarmTime(6), 24 * 60 * 60 * 1000, alarmIntent);

        //Start a daily evening alarm to generate enabled weather forecast notifications for the next day
        alarmIntent = PendingIntent.getBroadcast(context, EVENING_ALARM_ID, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, getAlarmTime(18), 24 * 60 * 60 * 1000, alarmIntent);
    }

    //Get the next alarm time for the given hour, pushing back to tomorrow if more than an hour has passed since the given hour
    //i.e. If it's now 06:59 and the given hour is 6 this will return today at 06:00 causing an immediate weather forecast,
    //     whereas if it's now 07:00 and the given hour is 6, the next 06:00 forecast will be tomorrow.
    private static long getAlarmTime(int hour) {
        Calendar calendar = Calendar.getInstance();

        //Set the time to the current time
        calendar.setTimeInMillis(System.currentTimeMillis());

        //Set the calendar time
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        //Return the time in milliseconds, adjusted to tomorrow if we're more than an hour over the alarm time
        long alarmTime = calendar.getTimeInMillis();
        if((System.currentTimeMillis() - 3600000) < alarmTime) {
            return alarmTime;
        } else {
            return alarmTime + 86400000;
        }
    }
}
