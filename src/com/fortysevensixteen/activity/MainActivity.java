package com.fortysevensixteen.activity;

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
import com.fortysevensixteen.R;
import com.fortysevensixteen.task.ImageUpdateTask;
import com.fortysevensixteen.task.RequestTask;

public class MainActivity extends Activity {

    ImageView lockButton;
    TextView urlText;
    SharedPreferences sharedPreferences;

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

        private LockButtonClickListener(Context context) {
            this.context = context;
        }

        public void onClick(View view) {
            executeHttpRequest();
            Toast.makeText(context, "Door is now open.", Toast.LENGTH_SHORT);
            lockButton.setImageResource(R.drawable.lock_open);
            ImageUpdateTask task = new ImageUpdateTask((ImageView) findViewById(R.id.lockButton),
                    3000, R.drawable.lock_closed);
            task.execute();
        }
    }

    private String getUrl() {
        return sharedPreferences.getString(getString(R.string.settingsServerAddress), "") +
                sharedPreferences.getString(getString(R.string.settingsRequestParameters), "");
    }

    private void executeHttpRequest() {
        if(isOnline()) {
            new RequestTask().execute(getUrl());
        } else {
            Toast.makeText(this, "No network connection!", Toast.LENGTH_SHORT);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

}
