package uk.co.nickbloor.mountainweatherforecast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainView extends AppCompatActivity {
    private SharedPreferences _prefs = null;
    private SharedPreferences.Editor _prefsEditor = null;

    //Initialise the app
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        //Setup toggle switch click handlers to update notification configuration
        setupSwitchHandlers();

        //Setup click handlers for full forecast links
        setupForecastLinks();

        //Kick off alarms for notifications
        ForecastUpdateScheduler.scheduleForecastUpdates(this);
    }

    //Ensure preferences are loaded on resume
    protected void onResume() {
        super.onResume();

        //Load configuration
        loadPreferences();
    }

    //Load preferences and set toggle switches accordingly
    private void loadPreferences() {
        //Get the shared preferences and editor
        if(_prefs == null) {
            _prefs = getSharedPreferences(getString(R.string.shared_prefs_filename), Context.MODE_PRIVATE);
            _prefsEditor = _prefs.edit();
        }

        //Set toggle switches according to current configuration
        ((Switch)findViewById(R.id.swBrecon)).setChecked(_prefs.getBoolean(getString(R.string.PREF_BRECON_ENABLED), false));
        ((Switch)findViewById(R.id.swLakes)).setChecked(_prefs.getBoolean(getString(R.string.PREF_LAKES_ENABLED), false));
        ((Switch)findViewById(R.id.swMourne)).setChecked(_prefs.getBoolean(getString(R.string.PREF_MOURNE_ENABLED), false));
        ((Switch)findViewById(R.id.swNGrampian)).setChecked(_prefs.getBoolean(getString(R.string.PREF_NGRAMPIAN_ENABLED), false));
        ((Switch)findViewById(R.id.swNWHighlands)).setChecked(_prefs.getBoolean(getString(R.string.PREF_NWHIGHLANDS_ENABLED), false));
        ((Switch)findViewById(R.id.swPeak)).setChecked(_prefs.getBoolean(getString(R.string.PREF_PEAK_ENABLED), false));
        ((Switch)findViewById(R.id.swSnowdonia)).setChecked(_prefs.getBoolean(getString(R.string.PREF_SNOWDONIA_ENABLED), false));
        ((Switch)findViewById(R.id.swSGrampian)).setChecked(_prefs.getBoolean(getString(R.string.PREF_SGRAMPIAN_ENABLED), false));
        ((Switch)findViewById(R.id.swSWHighlands)).setChecked(_prefs.getBoolean(getString(R.string.PREF_SWHIGHLANDS_ENABLED), false));
        ((Switch)findViewById(R.id.swYorkshire)).setChecked(_prefs.getBoolean(getString(R.string.PREF_YORKSHIRE_ENABLED), false));
    }

    //Set change listeners on the toggle switches to update preferences
    private void setupSwitchHandlers() {
        ((Switch)findViewById(R.id.swBrecon)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                _prefsEditor.putBoolean(getString(R.string.PREF_BRECON_ENABLED), b);
                _prefsEditor.commit();
            }
        });
        ((Switch)findViewById(R.id.swLakes)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                _prefsEditor.putBoolean(getString(R.string.PREF_LAKES_ENABLED), b);
                _prefsEditor.commit();
            }
        });
        ((Switch)findViewById(R.id.swMourne)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                _prefsEditor.putBoolean(getString(R.string.PREF_MOURNE_ENABLED), b);
                _prefsEditor.commit();
            }
        });
        ((Switch)findViewById(R.id.swNGrampian)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                _prefsEditor.putBoolean(getString(R.string.PREF_NGRAMPIAN_ENABLED), b);
                _prefsEditor.commit();
            }
        });
        ((Switch)findViewById(R.id.swNWHighlands)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                _prefsEditor.putBoolean(getString(R.string.PREF_NWHIGHLANDS_ENABLED), b);
                _prefsEditor.commit();
            }
        });
        ((Switch)findViewById(R.id.swPeak)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                _prefsEditor.putBoolean(getString(R.string.PREF_PEAK_ENABLED), b);
                _prefsEditor.commit();
            }
        });
        ((Switch)findViewById(R.id.swSnowdonia)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                _prefsEditor.putBoolean(getString(R.string.PREF_SNOWDONIA_ENABLED), b);
                _prefsEditor.commit();
            }
        });
        ((Switch)findViewById(R.id.swSGrampian)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                _prefsEditor.putBoolean(getString(R.string.PREF_SGRAMPIAN_ENABLED), b);
                _prefsEditor.commit();
            }
        });
        ((Switch)findViewById(R.id.swSWHighlands)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                _prefsEditor.putBoolean(getString(R.string.PREF_SWHIGHLANDS_ENABLED), b);
                _prefsEditor.commit();
            }
        });
        ((Switch)findViewById(R.id.swYorkshire)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                _prefsEditor.putBoolean(getString(R.string.PREF_YORKSHIRE_ENABLED), b);
                _prefsEditor.commit();
            }
        });
    }

    //Set click handlers on the full forecast links to open the forecast URLs
    private void setupForecastLinks() {
        findViewById(R.id.txtBrecon).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_brecon))));
            }
        });
        findViewById(R.id.txtLakes).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_lakes))));
            }
        });
        findViewById(R.id.txtMourne).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_mourne))));
            }
        });
        findViewById(R.id.txtNGrampian).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_ngrampian))));
            }
        });
        findViewById(R.id.txtNWHighlands).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_nwhighlands))));
            }
        });
        findViewById(R.id.txtPeak).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_peak))));
            }
        });
        findViewById(R.id.txtSnowdonia).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_snowdonia))));
            }
        });
        findViewById(R.id.txtSGrampian).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_sgrampian))));
            }
        });
        findViewById(R.id.txtSWHighlands).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_swhighlands))));
            }
        });
        findViewById(R.id.txtYorkshire).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_yorkshire))));
            }
        });
    }
}
