package uk.co.nickbloor.mountainweatherforecast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnBootNotificationScheduler extends BroadcastReceiver {
    //Ensure notifications continue to work after a reboot
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            ForecastUpdateScheduler.scheduleForecastUpdates(context);
        }
    }
}
