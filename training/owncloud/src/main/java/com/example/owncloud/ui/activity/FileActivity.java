package com.example.owncloud.ui.activity;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.owncloud.R;
import com.example.owncloud.ui.dialog.LoadingDialog;

import java.lang.ref.WeakReference;

public class FileActivity extends AppCompatActivity {
    private static final String DIALOG_WAIT_TAG = "DIALOG_WAIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    private class LoadingLogTask extends AsyncTask <String, Void, String> {
        private final WeakReference<TextView> textViewReference;

        public LoadingLogTask(TextView logTV) {
            textViewReference = new WeakReference<TextView>(LogTV);
        }

        protected String doInBackground(String... args) {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {

            }

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
