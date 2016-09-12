package com.example.owncloud.ui.errorhandling;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.owncloud.R;

public class ErrorShowActivity extends AppCompatActivity {
    private static final String TAG = ErrorShowActivity.class.getSimpleName();
    TextView mError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "ErrorShowActivity was called. See above for StackTrace.");
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.errorhandling_showerror);
        mError = (TextView) findViewById(R.id.errorTextView);
        mError.setText(getIntent().getStringExtra("error"));
    }
}
