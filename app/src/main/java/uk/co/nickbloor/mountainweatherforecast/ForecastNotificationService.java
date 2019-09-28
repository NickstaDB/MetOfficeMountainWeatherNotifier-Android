package uk.co.nickbloor.mountainweatherforecast;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ForecastNotificationService {
    //Notification channel IDs
    public static final int CHANNEL_BRECONBEACONS = 1;
    public static final int CHANNEL_LAKEDISTRICT = 2;
    public static final int CHANNEL_MOURNEMOUNTAINS = 3;
    public static final int CHANNEL_NORTHGRAMPIAN = 4;
    public static final int CHANNEL_NORTHWESTHIGHLANDS = 5;
    public static final int CHANNEL_PEAKDISTRICT = 6;
    public static final int CHANNEL_SNOWDONIA = 7;
    public static final int CHANNEL_SOUTHGRAMPIAN = 8;
    public static final int CHANNEL_SOUTHWESTHIGHLANDS = 9;
    public static final int CHANNEL_YORKSHIREDALES = 10;

    //Notification channels
    private HashMap<Integer, ForecastNotification> _channels;

    //Android Context and Notification Manager
    private Context _context;
    private NotificationManager _manager;

    //OkHttp client
    private OkHttpClient _client;

    //Initialise the notification service
    public ForecastNotificationService(Context ctx) {
        _context = ctx;
        _manager = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        //Configure notification channels
        _channels = new HashMap<Integer, ForecastNotification>();
        _channels.put(CHANNEL_BRECONBEACONS, new ForecastNotification(ctx.getString(R.string.loc_brecon), true, Uri.parse(ctx.getString(R.string.url_brecon)), _context.getSharedPreferences(ctx.getString(R.string.shared_prefs_filename), Context.MODE_PRIVATE), ctx.getString(R.string.PREF_BRECON_ENABLED)));
        _channels.put(CHANNEL_LAKEDISTRICT, new ForecastNotification(ctx.getString(R.string.loc_lakes), true, Uri.parse(ctx.getString(R.string.url_lakes)), _context.getSharedPreferences(ctx.getString(R.string.shared_prefs_filename), Context.MODE_PRIVATE), ctx.getString(R.string.PREF_LAKES_ENABLED)));
        _channels.put(CHANNEL_MOURNEMOUNTAINS, new ForecastNotification(ctx.getString(R.string.loc_mourne), true, Uri.parse(ctx.getString(R.string.url_mourne)), _context.getSharedPreferences(ctx.getString(R.string.shared_prefs_filename), Context.MODE_PRIVATE), ctx.getString(R.string.PREF_MOURNE_ENABLED)));
        _channels.put(CHANNEL_NORTHGRAMPIAN, new ForecastNotification(ctx.getString(R.string.loc_ngrampian), false, Uri.parse(ctx.getString(R.string.url_ngrampian)), _context.getSharedPreferences(ctx.getString(R.string.shared_prefs_filename), Context.MODE_PRIVATE), ctx.getString(R.string.PREF_NGRAMPIAN_ENABLED)));
        _channels.put(CHANNEL_NORTHWESTHIGHLANDS, new ForecastNotification(ctx.getString(R.string.loc_nwhighlands), true, Uri.parse(ctx.getString(R.string.url_nwhighlands)), _context.getSharedPreferences(ctx.getString(R.string.shared_prefs_filename), Context.MODE_PRIVATE), ctx.getString(R.string.PREF_NWHIGHLANDS_ENABLED)));
        _channels.put(CHANNEL_PEAKDISTRICT, new ForecastNotification(ctx.getString(R.string.loc_peak), true, Uri.parse(ctx.getString(R.string.url_peak)), _context.getSharedPreferences(ctx.getString(R.string.shared_prefs_filename), Context.MODE_PRIVATE), ctx.getString(R.string.PREF_PEAK_ENABLED)));
        _channels.put(CHANNEL_SNOWDONIA, new ForecastNotification(ctx.getString(R.string.loc_snowdonia), false, Uri.parse(ctx.getString(R.string.url_snowdonia)), _context.getSharedPreferences(ctx.getString(R.string.shared_prefs_filename), Context.MODE_PRIVATE), ctx.getString(R.string.PREF_SNOWDONIA_ENABLED)));
        _channels.put(CHANNEL_SOUTHGRAMPIAN, new ForecastNotification(ctx.getString(R.string.loc_sgrampian), false, Uri.parse(ctx.getString(R.string.url_sgrampian)), _context.getSharedPreferences(ctx.getString(R.string.shared_prefs_filename), Context.MODE_PRIVATE), ctx.getString(R.string.PREF_SGRAMPIAN_ENABLED)));
        _channels.put(CHANNEL_SOUTHWESTHIGHLANDS, new ForecastNotification(ctx.getString(R.string.loc_swhighlands), true, Uri.parse(ctx.getString(R.string.url_swhighlands)), _context.getSharedPreferences(ctx.getString(R.string.shared_prefs_filename), Context.MODE_PRIVATE), ctx.getString(R.string.PREF_SWHIGHLANDS_ENABLED)));
        _channels.put(CHANNEL_YORKSHIREDALES, new ForecastNotification(ctx.getString(R.string.loc_yorkshire), true, Uri.parse(ctx.getString(R.string.url_yorkshire)), _context.getSharedPreferences(ctx.getString(R.string.shared_prefs_filename), Context.MODE_PRIVATE), ctx.getString(R.string.PREF_YORKSHIRE_ENABLED)));

        //Register the notification channels
        for(Integer k: _channels.keySet()) {
            registerAndroidNotificationChannel(_channels.get(k));
        }

        //Create OkHttpClient to use in getting weather forecasts
        _client = new OkHttpClient();
    }

    //Register an Android notification channel
    private void registerAndroidNotificationChannel(ForecastNotification notification) {
        NotificationChannel channel = new NotificationChannel(notification.getChannelId(), notification.getChannelName(), NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription(notification.getChannelDescription());
        _manager.createNotificationChannel(channel);
    }

    //Generate mountain weather notifications
    public void generateNotifications() {
        ForecastNotification notification;
        for(Integer k: _channels.keySet()) {
            notification = _channels.get(k);
            if(notification.isEnabled() == true) {
                getWeatherUpdate(notification);
            }
        }
    }

    //Get weather data and dispatch a notification
    private void getWeatherUpdate(final ForecastNotification notification) {
        //Build a HTTP request to get the weather update
        Request request = new Request.Builder()
                .url(notification.getForecastUrl().toString())
                //Just like visiting the website every day, except more convenient :)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36")
                .build();

        //Queue up the request
        _client.newCall(request).enqueue(new Callback() {
            public void onResponse(Call call, Response response) throws IOException {
                //Extract the forecast description text
                String description = "";
                try {
                    if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 16) {
                        description = response.body().string().split("<div id=\"day1\"")[1]
                                .split("<div class=\"mountain-additional-info\">")[1]
                                .split("<div class=\"weather\">")[1]
                                .split("<p>")[1]
                                .split("</p>")[0];
                    } else {
                        description = response.body().string().split("<div id=\"day0\"")[1]
                                .split("<div class=\"mountain-additional-info\">")[1]
                                .split("<div class=\"weather\">")[1]
                                .split("<p>")[1]
                                .split("</p>")[0];
                    }
                } catch(Exception e) {
                    description = "Unable to parse forecast, click to open Met Office mountain weather forecast web page.";
                }
                generateNotification(notification, description);
            }
            public void onFailure(Call call, IOException e) {
                //Generate an error notification
                generateNotification(notification, "Unable to retrieve forecast, click to open Met Office mountain weather forecast web page.");
            }
        });
    }

    //Generate an Android notification
    private void generateNotification(ForecastNotification notification, String forecastText) {
        //Build the notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(_context, notification.getChannelId())
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setContentTitle(notification.getNotificationTitle())
                .setContentText(forecastText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(forecastText));

        //Create an intent to open the forecast URL when the notification is clicked
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(notification.getForecastUrl());
        PendingIntent pintent = PendingIntent.getActivity(_context, 0, intent, 0);
        notificationBuilder.setContentIntent(pintent);
        notificationBuilder.setAutoCancel(true);

        //Dispatch the notification
        _manager.notify(notification.getNotificationId(), notificationBuilder.build());
    }
}
