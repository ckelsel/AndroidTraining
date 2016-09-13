package com.ckelsel.android.training.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.ckelsel.android.training.R;

public class DownloadIntentServiceActivity extends AppCompatActivity {

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mText.setText(intent.getStringExtra(DownloadIntentService.ID));
            }
        }
    };

    private EditText mText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_intent_service);

        mText = (EditText) findViewById(R.id.text);

        DownloadIntentService.startActionFoo(getApplicationContext(),
                "param1", "param2");
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, new IntentFilter(DownloadIntentService.NOTIFICATIONS));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }
}
