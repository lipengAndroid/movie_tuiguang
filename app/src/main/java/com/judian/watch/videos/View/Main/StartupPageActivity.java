package com.judian.watch.videos.View.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Window;

import com.cuihai.library.StatusBarUtils;
import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Http.UrlUtils;
import com.judian.watch.videos.Interface.EditTextDialogInterface;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Mode.UriListMode;
import com.judian.watch.videos.Mode.UserInfoMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.PreferencesUtil;
import com.judian.watch.videos.View.Dialog.EdDialogUtils;
import com.judian.watch.videos.View.User.BinderPhoneActivity;

import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;

import static com.judian.watch.videos.Http.UrlUtils.URI.bindcode;
import static com.judian.watch.videos.Mode.KEY.USER_INFO;


public class StartupPageActivity extends BaseActivity implements MyHttpCallBack {//  implements com.kyview.interfaces.AdViewSpreadListener, AdViewBannerListener
    private boolean mainIsNoStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.startup_page_activity);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        MyApplication.getInstance().setX(dm.widthPixels);
        MyApplication.getInstance().setY(dm.heightPixels);
        StatusBarUtils.transparencyBar(this);

        initVideoListMode();//获取分享过得五星电影
        if (!TextUtils.isEmpty(PreferencesUtil.getString(USER_INFO))) {
            UserInfoMode infoMode = new JsonUtil<UserInfoMode>().json2Bean(
                    PreferencesUtil.getString(USER_INFO),
                    UserInfoMode.class.getName());
            MyApplication.getInstance().setUser(infoMode);
            MyApplication.getInstance().setLogin(true);
            try {
//                if (TextUtils.isEmpty(MyApplication.getInstance().getUser().getData().getPhonenum() + "") ||
//                        MyApplication.getInstance().getUser().getData().getPhonenum() == null) {
//                    MyApplication.getInstance().setuId(PreferencesUtil.getString("uid"));
//                    startActivity(new Intent(mContext, BinderPhoneActivity.class));
//                    finish();
//                } else {
                    new OkHttpUtils(1, this)
                            .SetApiUrl(UrlUtils.URI.getmyself)
                            .SetKey("unionid")
                            .SetValue(MyApplication.getInstance().getUser().getData().getUnionid())
                            .POST();
//                }
            } catch (Exception e) {
                MyApplication.getInstance().setLogin(false);
                initRootView();
            }

        } else {
            MyApplication.getInstance().setLogin(false);
            initRootView();
        }

    }

    private void initVideoListMode() {
        if (TextUtils.isEmpty(PreferencesUtil.getString(KEY.URL_LIST))) {
            UriListMode mode = new UriListMode();
            List<UriListMode.ban> bans = new ArrayList<>();
            mode.setList(bans);
            PreferencesUtil.putString(KEY.URL_LIST,
                    new JsonUtil<UriListMode>().bean2Json(mode));
        }
    }

    private void startMain() {
        if (mainIsNoStart) {
            startActivity(new Intent(mContext, MainActivity.class));
            mainIsNoStart = false;
            finish();
        }

    }

    private void initRootView() {

        new Thread(() -> {
            try {
                Thread.sleep(500);
                handler.sendEmptyMessage(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (TextUtils.isEmpty(PreferencesUtil.getString("ad_is_start"))) {
                        startActivity(new Intent(mContext, AdWebViewActivity.class));
                        PreferencesUtil.putString("ad_is_start", "0");
                        finish();
                    } else {
                        startMain();
                    }
                    break;
            }
        }
    };

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 1:
                PreferencesUtil.putString(KEY.USER_INFO, jsonString);
                UserInfoMode infoMode = new JsonUtil<UserInfoMode>().json2Bean(
                        jsonString,
                        UserInfoMode.class.getName());
                MyApplication.getInstance().setUser(infoMode);
                MyApplication.getInstance().setLogin(true);
                initRootView();
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        if (uriType == 1) {
            initRootView();
        }
    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }


//    @Override
//    public void onAdDisplay(String s) {
//
//        ToastUtils.show("onAdDisplay" + s);
//    }
//
//    @Override
//    public void onAdClose(String s) {
//        startMain();
//    }
//
//    @Override
//    public void onAdRecieved(String s) {
//
//        ToastUtils.show("onAdRecieved" + s);
//    }
//
//    @Override
//    public void onAdClick(String s) {
//
//        ToastUtils.show("onAdClick" + s);
//    }
//
//    @Override
//    public void onAdFailed(String s) {
//        startMain();
//    }
//
//    @Override
//    public void onAdReady(String s) {
//
//    }
//
//    @Override
//    public void onAdSpreadNotifyCallback(String s, ViewGroup viewGroup, int i, int i1) {
//
//        ToastUtils.show("onAdSpreadNotifyCallback" + s);
//    }
}
