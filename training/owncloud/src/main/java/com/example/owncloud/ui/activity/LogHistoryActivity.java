package com.example.owncloud.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.owncloud.R;
import com.example.owncloud.ui.dialog.LoadingDialog;
import com.example.owncloud.ui.dialog.Notification;

import java.lang.ref.WeakReference;

public class LogHistoryActivity extends AppCompatActivity {
    private static final String DIALOG_WAIT_TAG = "DIALOG_WAIT";

    private static final String KEY_LOG_TEXT = "LOG_TEXT";

    private static final String TAG = LogHistoryActivity.class.getSimpleName();

    private String mLogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_send_file);

        TextView logTV = (TextView) findViewById(R.id.logTV);

        new Notification().showIncre(getApplicationContext());

        new Notification().showProgress(getApplicationContext());

        if (savedInstanceState == null) {
            LoadingLogTask task = new LoadingLogTask(logTV);
            task.execute();
        } else {
            mLogText = savedInstanceState.getString(KEY_LOG_TEXT);
            logTV.setText(mLogText);
        }


    }

    private class LoadingLogTask extends AsyncTask <String, Void, String> {
        private final WeakReference<TextView> textViewReference;

        public LoadingLogTask(TextView logTV) {
            textViewReference = new WeakReference<TextView>(logTV);
        }

        @Override
        protected void onPostExecute(String result) {
            dismissLoadingDialog();
        }

        @Override
        protected void onPreExecute() {
            showLoadingDialog();
        }

        protected String doInBackground(String... args) {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {

            }
            return "";
        }

    }

    public void showLoadingDialog() {
        LoadingDialog dialog = LoadingDialog.newInstance("test");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        dialog.show(ft, DIALOG_WAIT_TAG);
    }

    public void dismissLoadingDialog() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(DIALOG_WAIT_TAG);
        if (fragment != null) {
            ((LoadingDialog) fragment).dismiss();
        }
    }
}
