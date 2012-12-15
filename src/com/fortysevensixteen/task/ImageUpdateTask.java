package com.fortysevensixteen.task;

import android.os.AsyncTask;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageUpdateTask extends AsyncTask<Void, Void, Void> {

    ImageView image;
    int waitTime;
    int imageResource;

    public ImageUpdateTask(ImageView image, int waitTime, int imageResource) {
        this.image = image;
        this.waitTime = waitTime;
        this.imageResource = imageResource;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        image.setImageResource(imageResource);
    }
}
