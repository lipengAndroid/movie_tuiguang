package com.judian.watch.videos.View.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.cuihai.library.StatusBarUtils;
import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.EditTextDialogInterface;
import com.judian.watch.videos.Interface.FragmentToActivityCallBack;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Mode.VersionInfo;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.AppUtils;
import com.judian.watch.videos.Utils.DownloadUtils;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.LogUtils;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.PreferencesUtil;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Dialog.EdDialogUtils;
import com.judian.watch.videos.View.Dialog.YDDialog;
import com.judian.watch.videos.View.Shopping.ShoppingFragment;
import com.judian.watch.videos.View.User.UserFragment;
import com.judian.watch.videos.View.Video.ClassBrushFragment;
import com.judian.watch.videos.View.Video.HomeFragment;
import com.judian.watch.videos.databinding.ActivityMainBinding;

import static com.judian.watch.videos.Http.UrlUtils.URI.bindcode;
import static com.judian.watch.videos.Http.UrlUtils.URI.getversioninfo;


public class MainActivity extends BaseActivity implements MyHttpCallBack, FragmentToActivityCallBack {
    //Fragment数组
    private Class<?> fragmentArray[] = new Class<?>[]{
            HomeFragment.class,
            ShoppingFragment.class,
            ClassBrushFragment.class,
            UserFragment.class,
    };


    private static final ThreadLocal<MainActivity> main = new ThreadLocal<>();

    private int mTabNameArray[] = new int[]{
            R.string.home,
            R.string.shopping,
            R.string.class_video,
            R.string.user};

    private int[] mIconArray = new int[]{
            R.drawable.home_bar_home_selector,
            R.drawable.home_bar_shopping_selector,
            R.drawable.home_bar_class_video_selector,
            R.drawable.home_bar_user_selector
    };
    private boolean isckUp = false;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        StatusBarUtils.transparencyBar(this);
        binding.tabHost.setup(this, getSupportFragmentManager(), R.id.real_content);
        main.set(this);
        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = binding.tabHost.newTabSpec(String.valueOf(i)).setIndicator(getTabItemViews(i));
            binding.tabHost.addTab(tabSpec, fragmentArray[i], null);
        }
        binding.tabHost.setOnTabChangedListener(onTabChangeListener);
        upDataApp();// app 更新
        if (MyApplication.getInstance().isLogin()) {
            if (MyApplication.getInstance().getUser().getData().getGuoqi() != 1) {
                ToastUtils.show("VIP已过期，请去“我的”领取VIP");
            }
        }
        initEdCode();
    }

    public static void Finish() {
        if (main.get() != null) {
            main.get().finish();
        }
    }

    public void upDataApp() {
        isckUp = true;
        if (!MyApplication.getInstance().isUpDialogisShow()) {
            new OkHttpUtils(54, this)
                    .SetApiUrl(getversioninfo)
                    .SetKey("status")
                    .SetValue("2")
                    .POST();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initEdCode();
        if (!isckUp) {
            upDataApp();
        }
    }

    private void initEdCode() {
        if (MyApplication.getInstance().isLogin()
                && !TextUtils.isEmpty(PreferencesUtil.getString(KEY.IS_ED_FIRST_CODE))
                && PreferencesUtil.getString(KEY.IS_ED_FIRST_CODE).equals("0")) {
            new EdDialogUtils()
                    .init(this, "请输入邀请码", new EditTextDialogInterface() {
                        @Override
                        public void ED(String order) {
                            new OkHttpUtils(16, MainActivity.this)
                                    .SetApiUrl(bindcode)
                                    .SetKey("openid", "code")
                                    .SetValue(MyApplication.getInstance().getUser().getData().getOpenid(), order)
                                    .POST();

                        }

                        @Override
                        public void cancel() {
                            PreferencesUtil.removeKey(KEY.USER_INFO);
                            MyApplication.getInstance().setUser(null);
                            MyApplication.getInstance().setLogin(false);
                            finish();
                        }
                    }).isTouchcCancel(false);
        }
    }


    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemViews(int index) {

        @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.layout_main_tab_item_child, null);

        TextView textView = view.findViewById(R.id.tv_main_tab_item);
        ImageView imageView = view.findViewById(R.id.iv_main_tab_item);
        textView.setText(mTabNameArray[index]);
        int resourceId = mIconArray[index];

        imageView.setImageResource(resourceId);

        view.setTag(index + "");
        return view;
    }

    TabHost.OnTabChangeListener onTabChangeListener = (String tabId) -> {
        if (tabId.equals("3") && MyApplication.getInstance().isLogin() && TextUtils.isEmpty(PreferencesUtil.getString("YDDtwoialog"))) {
            startActivity(new Intent(mContext, YDDialog.class));
        }
    };


    private long mExitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 1500) {

                ToastUtils.show("再按一次退出");

                mExitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isUpData = false;//是否强制跟新
    private DownloadUtils downLoadUtils;

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 54:
                VersionInfo info = new JsonUtil<VersionInfo>()
                        .json2Bean(jsonString, VersionInfo.class.getName());
                int code = 1;
                isckUp = true;
                try {
                    code = Integer.valueOf(info.getVersion());
                } catch (Exception ignored) {
                }
                try {
                    if (code > MyApplication.getInstance().getVersionCode()) {
                        downLoadUtils = new DownloadUtils();
                        if (info.getUpdatestatus().equals("1")) isUpData = true;

                        downLoadUtils.init(MainActivity.this);
                        downLoadUtils.Down(info.getDownloadurl(),
                                info.getInstruction(), isUpData, this);
                    } else {
                        if (MyApplication.getInstance().isUserUp()) {
                            ToastUtils.showCENTER("已经是最新版本了");
                            MyApplication.getInstance().setUserUp(false);
                        }
                    }
                } catch (Exception ignored) {
                }

                break;
            case 333:
                LogUtils.e(jsonString);
                AppUtils.Install(mContext, jsonString);
                break;
            case 16:
                PreferencesUtil.putString(KEY.IS_ED_FIRST_CODE, "1");
                finish();
                startActivity(new Intent(mContext, MainActivity.class));
                break;
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        downLoadUtils.onRequestPermissionsResult(requestCode, grantResults);
//    }

    @Override
    public void error(String e, int uriType) {
        switch (uriType) {
            case 333:
                ToastUtils.show("下载失败");
                break;
            case 16:
                ToastUtils.show(e);
                PreferencesUtil.removeKey(KEY.USER_INFO);
                MyApplication.getInstance().setUser(null);
                MyApplication.getInstance().setLogin(false);
                finish();
                startActivity(new Intent(mContext, MainActivity.class));
                break;
            default:
                ToastUtils.show(e);
                break;
        }
    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void callBack() {
        upDataApp();
    }
}








