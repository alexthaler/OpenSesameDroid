package com.fortysevensixteen.opensesame.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.fortysevensixteen.opensesame.R;
import com.fortysevensixteen.opensesame.task.RequestTask;

public class MainActivity extends Activity {

    private ImageView lockButton;
    private TextView urlText;
    private SharedPreferences sharedPreferences;

    private static final String http_regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        lockButton = (ImageView) findViewById(R.id.lockButton);
        lockButton.setOnClickListener(new LockButtonClickListener(this));
        urlText = (TextView) findViewById(R.id.urlText);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sharedPreferences.getBoolean(getString(R.string.settingsDisplayUrl), false)) {
            urlText.setText(getUrl());
        } else {
            urlText.setText("");
        }
    }

    private class LockButtonClickListener implements View.OnClickListener {

        Context context;
        Runnable r = new Runnable() {
            public void run(){
                lockButton.setImageResource(R.drawable.packlock_closed);
            }
        };

        private LockButtonClickListener(Context context) {
            this.context = context;
        }

        public void onClick(View view) {
            executeHttpRequest();
            lockButton.setImageResource(R.drawable.padlock_open);
            lockButton.postDelayed(r, 2000);
        }
    }

    private String getUrl() {
        return sharedPreferences.getString(getString(R.string.settingsServerAddress), "") +
                sharedPreferences.getString(getString(R.string.settingsRequestParameters), "");
    }

    private void executeHttpRequest() {
        if(isOnline()) {
            if(getUrl().matches(http_regex)) {
                new RequestTask().execute(getUrl());
            } else {
                Toast.makeText(getBaseContext(), "Please set URL in settings", Toast.LENGTH_SHORT);
            }
        } else {
            Toast.makeText(getBaseContext(), "No network connection!", Toast.LENGTH_SHORT);
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

}
