package com.ckelsel.android.training.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by ckelsel on 2016/9/13.
 *
 * Start at system boot
 *
 * adb shell
 * am broadcast -a android.intent.action.BOOT_COMPLETED
 */
public class MyScheduleReceiver extends BroadcastReceiver {
    private static final long REPEAT_TIME = 30 * 1000;


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("MyScheduleReceiver", "onReceive");
        AlarmManager service = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent1 = new Intent(context, MyStartServiceReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                intent1, PendingIntent.FLAG_CANCEL_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 30);
        service.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), REPEAT_TIME, pendingIntent);
    }
}
