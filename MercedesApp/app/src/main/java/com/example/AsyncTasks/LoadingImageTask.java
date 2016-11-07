package com.example.AsyncTasks;

import android.os.AsyncTask;
import android.widget.ImageView;

public class LoadingImageTask extends AsyncTask<Integer, Void, Integer> {
    private ImageView loadingImg;

    public LoadingImageTask(ImageView loadingImg) {
        this.loadingImg = loadingImg;
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        return params[0];
    }

    @Override
    protected void onPostExecute(Integer integer) {
        loadingImg.setImageResource(integer);
    }
}
