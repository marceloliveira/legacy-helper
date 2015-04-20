package org.attalaya.legacyhelper.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import org.attalaya.legacyhelper.R;

public class SettingsFragment extends PreferenceFragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

}
