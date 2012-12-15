package com.fortysevensixteen.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.fortysevensixteen.R;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        addPreferencesFromResource(R.xml.settings);
    }
}
