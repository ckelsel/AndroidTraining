package com.ckelsel.android.training.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LocalService extends Service {
    private MyBinder mBinder = new MyBinder();

    private static final String TAG = "LocalService";

    private ArrayList<String> mList = new ArrayList<String>();

    public LocalService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand");

        Random random = new Random();
        if (random.nextBoolean()) {
            Log.i(TAG, "Welcome");
            mList.add("Welcome");
        }

        if (random.nextBoolean()) {
            Log.i(TAG, "to");
            mList.add("to");
        }

        if (random.nextBoolean()) {
            Log.i(TAG, "to");
            mList.add("to");
        }

        if (random.nextBoolean()) {
            Log.i(TAG, "!");
            mList.add("!");
        }

        if (mList.size() > 20) {
            Log.i(TAG, "mList.remove(0);");
            mList.remove(0);
        }

        return START_NOT_STICKY;
    }

    public boolean onUnbind(Intent i) {
        Log.i(TAG, "onUnbind");
        return super.onUnbind(i);
    }

    public void onRebind(Intent i) {
        Log.i(TAG, "onRebind");
        super.onRebind(i);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return mBinder;
    }

    public class MyBinder extends Binder {
        LocalService getService() {
            return LocalService.this;
        }
    }

    public List<String> getWordList() {
        return mList;
    }
}
