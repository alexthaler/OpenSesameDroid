package com.fortysevensixteen.task;

import android.os.AsyncTask;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

public class ImageUpdateTask extends AsyncTask<Integer, Void, Void> {

    ImageView image;

    public ImageUpdateTask(ImageView image) {
        this.image = image;
    }

    @Override
    protected Void doInBackground(Integer... integers) {

        assert(integers.length == 2);
        ArrayList<Integer> params = new ArrayList<Integer>(Arrays.asList(integers));

        try {
            Thread.sleep(params.get(0) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        image.setImageResource(params.get(1));

        return null;
    }

}
