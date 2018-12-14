package com.judian.watch.videos.DiyView;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.R;
import com.judian.watch.videos.View.Home.TitleWebActivity;
import com.judian.watch.videos.View.Main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class NotificationUtils {
    public static void show(Context context, String title, String summary, Map<String, String> extraMap) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder mBuilder = new Notification.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.logo);

        mBuilder.setContentTitle(title);
        mBuilder.setContentText(summary);
        mBuilder.setAutoCancel(true);
        Intent resultIntent = new Intent(context, TitleWebActivity.class)
                .putExtra(KEY.TITLE, extraMap.get("title") + "")
                .putExtra(KEY.URL, extraMap.get("url") + "");
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent pIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pIntent);
        nm.notify(3, mBuilder.build());


    }

    public static void show(Context context, String title, String summary, String extraMap) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder mBuilder = new Notification.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.logo);
        try {
            JSONObject jsonObject = new JSONObject(extraMap);
            mBuilder.setContentTitle(title);
            mBuilder.setContentText(summary);
            mBuilder.setAutoCancel(true);
            Intent resultIntent = new Intent(context, TitleWebActivity.class)
                    .putExtra(KEY.TITLE, jsonObject.get("title") + "")
                    .putExtra(KEY.URL, jsonObject.get("url") + "");
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent pIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(pIntent);
            nm.notify(3, mBuilder.build());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
