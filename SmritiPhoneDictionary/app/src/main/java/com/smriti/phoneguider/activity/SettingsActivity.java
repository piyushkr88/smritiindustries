package com.smriti.phoneguider.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.smriti.phoneguider.R;

import java.util.List;

/**
 * Created by Piyush on 8/23/2017.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {
    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);

    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName);
    }



    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            int preferenceFile_toLoad=-1;
            String settings = getArguments().getString("settings");
            if ("prefs_general".equalsIgnoreCase(settings)) {
                // Load the preferences from an XML resource
                preferenceFile_toLoad= R.xml.pref_general;
            }/*else if ("prefs_notification".equalsIgnoreCase(settings)) {
                // Load the preferences from an XML resource
                preferenceFile_toLoad=R.xml.pref_notification;
            }*/
            addPreferencesFromResource(preferenceFile_toLoad);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            Preference appPreference = (Preference) findPreference("app_name");
            appPreference.setOnPreferenceClickListener(this);
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            if(preference.getKey().equalsIgnoreCase("app_name")){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ashishandsons.in/about-us.html"));
                startActivity(browserIntent);
            }else if(preference.getKey().equalsIgnoreCase("notifications_new_message")){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ashishandsons.in/about-us.html"));
                startActivity(browserIntent);
            }{

            }
            return true;
        }
    }
}
