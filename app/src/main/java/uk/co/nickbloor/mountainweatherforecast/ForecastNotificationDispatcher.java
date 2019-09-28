package uk.co.nickbloor.mountainweatherforecast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ForecastNotificationDispatcher extends BroadcastReceiver {
    //Handle Android Alarm Service event to generate notifications
    public void onReceive(Context context, Intent intent) {
        //Generate forecast notifications
        ForecastNotificationService forecastService = new ForecastNotificationService(context);
        forecastService.generateNotifications();
    }
}
