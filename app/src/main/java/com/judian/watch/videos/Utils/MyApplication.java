package com.judian.watch.videos.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Mode.UserInfoMode;
import com.judian.watch.videos.Mode.WxUserInfoMode;
import com.judian.watch.videos.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.analytics.MobclickAgent;


public class MyApplication extends MultiDexApplication {
    private static MyApplication mApplication;
    private boolean isLogin = false;
    private int x = 1080, y = 1920;
    private boolean shareVideo = false;
    private boolean wxLogin = false, isUserUp = false, upDialogisShow = false;
    private UserInfoMode userInfo;
    private String uri, vid, title, video_details,uId;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public boolean isShareVideo() {
        return shareVideo;
    }

    public boolean isUserUp() {
        return isUserUp;
    }

    public boolean isUpDialogisShow() {
        return upDialogisShow;
    }

    public void setUpDialogisShow(boolean upDialogisShow) {
        this.upDialogisShow = upDialogisShow;
    }

    public void setUserUp(boolean userUp) {
        isUserUp = userUp;
    }

    public void setShareVideo(boolean shareVideo) {
        this.shareVideo = shareVideo;
    }

    public String getVideo_details() {
        return video_details;
    }

    public void setVideo_details(String video_details) {
        this.video_details = video_details;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public boolean isWxLogin() {
        return wxLogin;
    }

    public String getUri() {
        return uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setWxLogin(boolean wxLogin) {
        this.wxLogin = wxLogin;
    }

    public UserInfoMode getUser() {
        return userInfo;
    }

    public void setUser(UserInfoMode userInfo) {
        this.userInfo = userInfo;
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater((context, layout) -> {
            layout.setPrimaryColorsId(R.color.white, R.color.txt_black);//全局设置主题颜色
            return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater((context, layout) -> new ClassicsFooter(context).setDrawableSize(20));
    }

    public static synchronized MyApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        initCloudChannel(this);
//        CrashHandler crashHandler = CrashHandler.getInstance();// 崩溃收集器
//        crashHandler.init(this);

        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, KEY.UM_APP_KEY, "yyb"));

        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
            @Override
            public void onSuccess() {
                LogUtils.e("初始化成功");
//                ToastUtils.show("初始化成功");
                //初始化成功，设置相关的全局配置参数
            }

            @Override
            public void onFailure(int code, String msg) {
//                ToastUtils.show("MyApplication" + code + msg);
                //初始化失败，可以根据code和msg判断失败原因，详情参见错误说明
            }
        });


        // initialize最好放在attachBaseContext最前面，初始化直接在Application类里面，切勿封装到其他类
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public int getVersionCode() {
        return getPackageInfo().versionCode;
    }

    public String getVersionCodeString() {
        return getPackageInfo().versionName;
    }


    private static PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            PackageManager pm;
            pm = MyApplication.getInstance().getPackageManager();
            pi = pm.getPackageInfo(MyApplication.getInstance().getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }

    /**
     * 初始化云推送通道
     *
     * @
     */
    private void initCloudChannel(Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();

        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.e("11111111111111111111111", "init cloudchannel success");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.e("11111111111111111111111", "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
    }

}
