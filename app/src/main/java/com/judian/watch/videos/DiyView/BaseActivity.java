package com.judian.watch.videos.DiyView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;



//import cn.magicwindow.Session;

/**
 * Created by Administrator on 2016/12/29 0029.
 */

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

    }
    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        MobclickAgent.onKillProcess(this);
        super.onDestroy();
    }
}
