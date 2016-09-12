package com.example.owncloud.ui.dialog;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.example.owncloud.R;

/**
 * Created by ckelsel on 2016/9/12.
 */
public class Notification {

    public static NotificationCompat.Builder create(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.notification_icon).setAutoCancel(false);
        builder.setColor(context.getResources().getColor(R.color.primary));
        return builder;
    }

    public static void show(Context context, int id, NotificationCompat.Builder builder) {
        ((NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE))
                .notify(id, builder.build());
    }


    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifyManager;
    int showIncreId = 1;
    int showProgressId = 2;
    public void showIncre(final Context context) {
        mNotifyManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = create(context);
        mBuilder.setContentTitle("Picture Download")
                .setContentText("Download in progress");

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {


                        int incr;
                        for (incr = 0; incr <= 100; incr += 5) {
                            mBuilder.setProgress(100, incr, false);
                            show(context, showIncreId, mBuilder);

                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {

                            }
                        }
                        mBuilder.setContentText("Download complete")
                                .setProgress(0, 0, false);
                        show(context, showIncreId, mBuilder);
                    }
                }
        ).start();
    }

    public void showProgress(final Context context) {
        mNotifyManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = create(context);
        mBuilder.setContentTitle("Picture Download")
                .setContentText("Download in progress");

        mBuilder.setProgress(0, 0, true);
        show(context, showProgressId, mBuilder);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        for (incr = 0; incr <= 100; incr += 5) {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {

                            }
                        }
                        mBuilder.setProgress(0, 0, false);
                        show(context, showProgressId, mBuilder);
                    }
                }
        ).start();


    }
}
