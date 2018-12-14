package com.judian.watch.videos.DiyView;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.judian.watch.videos.View.Home.TitleWebActivity;


/**
 * Created by Administrator on 2017/9/30 0030.
 */

public class myService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Intent main;

    @Override
    public void onCreate() {
        super.onCreate();
        main = new Intent(this, TitleWebActivity.class);

        startActivity(main);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        startService(new Intent(this, myService.class));
    }
}
