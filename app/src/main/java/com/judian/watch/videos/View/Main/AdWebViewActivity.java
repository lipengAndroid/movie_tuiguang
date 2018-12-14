package com.judian.watch.videos.View.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cuihai.library.StatusBarUtils;
import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.DiyView.JsInterface;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.AdMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.databinding.AdWebViewActivityBinding;

import static com.judian.watch.videos.Http.UrlUtils.URI.getadinfo;

/**
 * Created by 李鹏 2017/12/21 0021.
 */

public class AdWebViewActivity extends BaseActivity implements MyHttpCallBack, View.OnClickListener {
    private AdWebViewActivityBinding binding;

    private TimeCount time;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.ad_web_view_activity);
        StatusBarUtils.transparencyBar(this);
        WebSettings settings = binding.webView.getSettings();
        settings.setLoadsImagesAutomatically(true);
        settings.setBlockNetworkImage(false);
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setAllowFileAccess(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        binding.title.imgBack.setVisibility(View.INVISIBLE);
        if (Build.VERSION.SDK_INT >= 21) {// 支持https 混用http
            binding.webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        new OkHttpUtils(94, this)
                .SetApiUrl(getadinfo)
                .GET();
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                time.start();
            }

        };
        binding.webView.setWebViewClient(webViewClient);
        binding.webView.setWebChromeClient(new WebChromeClient() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress > 70) {
                    binding.tvInfo.setVisibility(View.GONE);
                } else {
                    if (binding.tvInfo.getVisibility() != View.VISIBLE) {
                        binding.tvInfo.setVisibility(View.VISIBLE);
                    }
                    binding.tvInfo.setText(newProgress + "%");
                }
            }
        });

        binding.webView.addJavascriptInterface(new JsInterface(this, binding.webView, webViewClient), "app");//
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 94:
                AdMode mode = new JsonUtil<AdMode>().json2Bean(jsonString, AdMode.class.getName());
                binding.webView.loadUrl(mode.getUrl());//http://www.teax.top/p.html
                time = new TimeCount(mode.getMiao() * 1000, 1000);
                binding.title.title.setText(mode.getTitle());
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }

    private boolean isTimeJup = true;

    private class TimeCount extends CountDownTimer {// 计时器

        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            if (isTimeJup) {
                jup();
            }
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            binding.title.rightButton.setText(((millisUntilFinished / 1000)) + "S");
        }

    }

    private void jup() {
        isTimeJup = false;
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }
}
