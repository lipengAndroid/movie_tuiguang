package com.judian.watch.videos.View.Home;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cuihai.library.StatusBarUtils;
import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.DiyView.JsInterface;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.LogUtils;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.databinding.TitleWebLayoutBinding;

import java.util.HashMap;

/**
 * Created by 李鹏 2017/11/29 0029.
 */

public class TitleWebActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack {

    public TitleWebLayoutBinding binding;


    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.title_web_layout);


        StatusBarUtils.transparencyBar(this);

//        binding.webView.getSettings().setBuiltInZoomControls(false);
//        binding.webView.getSettings().setSupportZoom(false);
//        binding.webView.getSettings().setDomStorageEnabled(true);
//        binding.webView.requestFocus();
        binding.webView.getSettings().setBlockNetworkImage(true);
        if (Build.VERSION.SDK_INT >= 21) {
            binding.webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
//        binding.webView.getSettings().setUseWideViewPort(true);
//        binding.webView.getSettings().setLoadWithOverviewMode(true);

        binding.webView.getSettings().setBlockNetworkImage(false);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setAllowContentAccess(true);
        binding.webView.getSettings().setAllowFileAccessFromFileURLs(true);
        binding.webView.getSettings().setAppCacheEnabled(false);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.getSettings().setBlockNetworkImage(false);
//        binding.webView.getSettings().setPluginState(WebSettings.PluginState.ON);
//        binding.title.rightButton.setText("分享");
//        binding.title.rightButton.setOnClickListener(this);

//        binding.webView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(com.tencent.smtt.sdk.WebView webView, int i) {
//                super.onProgressChanged(webView, i);
//            }
//        });

//        binding.webView.setWebViewClient(new com.tencent.smtt.sdk.WebViewClient() {
//
//        });

        binding.title.imgBack.setOnClickListener(this);
        HashMap<String, String> map;
        map = new HashMap<>();
        if (MyApplication.getInstance().isLogin()) {
            map.put("openid", MyApplication.getInstance().getUser().getData().getUnionid());
        } else {
            map.put("openid", "");
        }
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                super.onReceivedSslError(view, handler, error);
            }
        };
        binding.webView.setWebViewClient(webViewClient);
        binding.webView.addJavascriptInterface(new JsInterface(this, binding.webView, webViewClient), "app");
        binding.webView.loadUrl(getIntent().getStringExtra(KEY.URL), map);
        LogUtils.e(getIntent().getStringExtra(KEY.URL));
        binding.title.title.setText(getIntent().getStringExtra(KEY.TITLE));


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

    }

    @Override
    public void error(String e, int uriType) {

    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }
}
