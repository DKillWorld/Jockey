package com.marverenic.music;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.marverenic.music.utils.Themes;

public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Themes.setTheme(this);
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefFragment()).commit();
        Themes.themeActivity(android.R.layout.preference_category, getWindow().getDecorView().findViewById(android.R.id.content), this);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onResume() {
        Themes.setApplicationIcon(this);
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (prefs.getBoolean("prefAddShortcut", true)) {
            Themes.updateLauncherIcon(this);
            prefs.edit().putBoolean("prefAddShortcut", false).apply();
        }
    }

    public static class PrefFragment extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);
        }
    }
}