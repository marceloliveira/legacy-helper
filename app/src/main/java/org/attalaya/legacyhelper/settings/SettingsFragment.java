package org.attalaya.legacyhelper.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import org.attalaya.legacyhelper.R;
import org.attalaya.legacyhelper.controller.DataController;

public class SettingsFragment extends PreferenceFragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
        Log.d("LEGACYSETTINGS", "BASE: " + Boolean.toString(PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("main", false)));
        Log.d("LEGACYSETTINGS", "GP01: " + Boolean.toString(PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("gp01", false)));
        Log.d("LEGACYSETTINGS", "EP01: " + Boolean.toString(PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean("ep01", false)));
        Log.d("LEGACYSETTINGS", String.valueOf(DataController.getInstance(getActivity()).getSkills().size()));
    }

}
