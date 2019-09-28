package uk.co.nickbloor.mountainweatherforecast;

import android.content.SharedPreferences;
import android.net.Uri;

public class ForecastNotification {
    //Counter to generate unique notification IDs
    private static int NOTIFICATION_ID = 0;

    //Notification properties
    private String _channel_id;
    private String _channel_name;
    private String _channel_description;
    private int _notification_id;
    private String _notification_title;
    private Uri _forecast_url;

    //Shared preferences
    private SharedPreferences _prefs;
    private String _enabledPrefName;

    //Initialise the notification
    public ForecastNotification(String location_name, boolean prefixThe, Uri url, SharedPreferences prefs, String enabledPrefName) {
        _channel_id = location_name;
        _channel_name = location_name + " Weather";
        _channel_description = "Met Office mountain weather forecast for " + (prefixThe == true ? "the " : "") + location_name;
        _notification_id = NOTIFICATION_ID++;
        _notification_title = location_name + " Weather";
        _forecast_url = url;
        _prefs = prefs;
        _enabledPrefName = enabledPrefName;
    }

    //Getters
    public String getChannelId() { return _channel_id; }
    public String getChannelName() { return _channel_name; }
    public String getChannelDescription() { return _channel_description; }
    public int getNotificationId() { return _notification_id; }
    public String getNotificationTitle() { return _notification_title; }
    public Uri getForecastUrl() { return _forecast_url; }

    //Check whether the notification is currently enabled
    public boolean isEnabled() {
        return _prefs.getBoolean(_enabledPrefName, false);
    }
}
