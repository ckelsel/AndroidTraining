package com.ckelsel.android.training.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ckelsel on 2016/9/13.
 */
public class MyStartServiceReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("MyStartServiceReceiver", "onReceive");
        Intent service = new Intent(context, LocalService.class);
        context.startService(service);
    }
}
