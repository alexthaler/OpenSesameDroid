package com.fortysevensixteen.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import com.fortysevensixteen.R;
import com.fortysevensixteen.task.ImageUpdateTask;

public class MainActivity extends Activity {

    ImageView lockButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        lockButton = (ImageView) findViewById(R.id.lockButton);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    private class LockButtonClickListener implements View.OnClickListener {

        Context context;

        private LockButtonClickListener(Context context) {
            this.context = context;
        }

        public void onClick(View view) {
            lockButton.setImageResource(R.drawable.lock_open);
            ImageUpdateTask task = new ImageUpdateTask((ImageView) findViewById(R.id.lockButton));
            task.execute(3000, R.drawable.lock_closed);
        }
    }

}
