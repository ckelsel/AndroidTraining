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
    public void demo(final Context context) {
        mNotifyManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = create(context);
        mBuilder.setContentTitle("Picture Download")
                .setContentText("Download in progress");

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {

                        int id = 1;
                        int incr;
                        for (incr = 0; incr <= 100; incr += 5) {
                            mBuilder.setProgress(100, incr, false);

                            show(context, id, mBuilder);

                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {

                            }
                        }
                        mBuilder.setContentText("Download complete")
                                .setProgress(0, 0, false);
                        show(context, id, mBuilder);
                    }
                }
        ).start();
    }
}
