package com.judian.watch.videos.View.Home;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.cuihai.library.StatusBarUtils;
import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.DiyView.JsInterface;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Interface.getUserInterFace;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Mode.UserInfoMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.GetUserInfo;
import com.judian.watch.videos.Utils.LogUtils;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Dialog.DialogUtils;
import com.judian.watch.videos.databinding.GetVipWebLayoutBinding;

import java.util.HashMap;

/**
 * Created by 李鹏 2017/11/29 0029.
 */

public class GetVipWebActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack {

    public GetVipWebLayoutBinding binding;


    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StatusBarUtils.transparencyBar(this);
        binding = DataBindingUtil.setContentView(this, R.layout.get_vip_web_layout);

        AndroidBug5497Workaround.assistActivity(this);

        binding.webView.getSettings().setBlockNetworkImage(true);
        if (Build.VERSION.SDK_INT >= 21) {
            binding.webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        binding.webView.getSettings().setBlockNetworkImage(false);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setAllowContentAccess(true);
        binding.webView.getSettings().setAllowFileAccessFromFileURLs(true);
        binding.webView.getSettings().setAppCacheEnabled(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.getSettings().setBlockNetworkImage(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        binding.imgBack.setOnClickListener(this);
        HashMap<String, String> map = new HashMap<>();
        if (MyApplication.getInstance().isLogin()) {
            map.put("unionid", MyApplication.getInstance().getUser().getData().getUnionid());
            map.put("vip", getIntent().getStringExtra("vip"));
        } else {
            ToastUtils.show("请先登录！");
        }
        WebViewClient webViewClient = new WebViewClient() {

        };

        binding.webView.setWebViewClient(webViewClient);
        binding.webView.addJavascriptInterface(
                new JsInterface(this,
                        this,
                        getIntent().getStringExtra("vip")), "app");
        binding.webView.loadUrl(getIntent().getStringExtra(KEY.URL), map);
        LogUtils.e(getIntent().getStringExtra(KEY.URL));
        binding.title.setText(getIntent().getStringExtra(KEY.TITLE));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    }

    @Override
    public void onBackPressed() {
        if (binding.webView.canGoBack()) {
            binding.webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                if (binding.webView.canGoBack()) {
                    binding.webView.goBack();
                } else {
                    finish();
                }
                break;
        }
    }

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 1478:
                new GetUserInfo().Get(MyApplication.getInstance().getUser().getData().getUnionid()
                        , new getUserInterFace() {
                            @Override
                            public void ok(UserInfoMode userInfoMode) {
                                new DialogUtils().init(mContext)
                                        .setTitle(getString(R.string.nqcg))
                                        .setOne(getString(R.string.qd), ButtType -> finish());
                            }

                            @Override
                            public void error(String e) {

                            }
                        });


                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }
}
