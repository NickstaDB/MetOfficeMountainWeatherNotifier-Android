# Met Office Mountain Weather Notifier #
This is a simple Android app to help in taking advantage of favourable mountain weather by generating twice-daily notifications containing summaries from the [Met Office's](https://www.metoffice.gov.uk/ "Met Office") specialist [mountain weather forecast](https://www.metoffice.gov.uk/weather/specialist-forecasts/mountain "Met Office mountain weather forecast.").

![The Met Office mountain weather forecast website](/screenshots/metofficemountainweather.jpg?raw=true)

Evening notifications are generated around 6pm and contain summaries for the following day's weather. Morning notifications are generated around 6am and contain summaries for the same day's weather. Clicking on a notification leads to the full forecast on the Met Office website.

![Notification example](/screenshots/notification.jpg?raw=true)

Notifications can be enabled/disabled individually for each of the 10 locations covered in the Met Office forecast:

- Brecon Beacons
- Lake District
- Mourne Mountains
- North Grampian
- Northwest Highlands
- Peak District
- Snowdonia
- South Grampian and Southeast Highlands
- Southwest Highlands
- Yorkshire Dales

![Configuration screen](/screenshots/settings.jpg?raw=true)

## Build & Install ##

The easy way to build the app is to load up the [Android Studio](https://developer.android.com/studio "Android Studio") project or use the `VCS -> Checkout from Version Control -> Github` menu in Android Studio.

Once loaded, use the `Build -> Generate Signed APK` menu item to generate a new APK. Transfer that to your device (e.g. via USB), then install via the file manager on your device. Alternatively if your device is in developer mode enable USB debugging and run `adb install <apk-file>` to install the app.
