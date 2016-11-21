package com.example.AsyncTasks;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.example.mercedesapp.R;

import java.io.InputStream;

public class JSONAsyncTask extends AsyncTask<String, String, Void> {

    //region Initiation
    private Context context;
    private InputStream inputStream = null;
    private String result = "";
    private ProgressDialog progressDialog;
    //endregion

    public JSONAsyncTask(Context context) {
        this.context = context;
        progressDialog = new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                JSONAsyncTask.this.cancel(true);
            }
        });
    }

    @Override
    protected Void doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
    }
}
