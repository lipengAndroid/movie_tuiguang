package com.judian.watch.videos.View.Home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cuihai.library.StatusBarUtils;
import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.LogUtils;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.databinding.LpWebLayoutBinding;
import com.judian.watch.videos.databinding.OcclusionLayoutBinding;

/**
 * Created by 李鹏 2017/11/29 0029.
 */

public class TXWebActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack {

    public LpWebLayoutBinding binding;

    public OcclusionLayoutBinding topViewBinding;

    private String js = "javascript: var a = document.getElementsByClassName('header-app');a[0].innerHTML = a.length;";
    private String js1 = "javascript: var a = document.getElementsByClassName('m-box-Ptop');a[0].innerHTML = a.length;";
    private String js2 = "javascript: var a = document.getElementsByClassName('data-item');a[0].innerHTML = a.length;";
    private String js3 = "javascript: var a = document.getElementsByClassName('link-login');a[0].innerHTML = a.length;";
    private String js4 = "javascript: var a = document.getElementsByClassName('iqy-items');a[0].innerHTML = a.length;";
    private String js5 = "javascript: var a = document.getElementsByClassName('vo-link');a[0].innerHTML = a.length;";
    private String js6 = "javascript: var a = document.getElementsByClassName('yk-ucenter');a[0].innerHTML = a.length;";
    private String js7 = "javascript: var a = document.getElementsByClassName('x_login_icon');a[0].innerHTML = a.length;";
    private String js8 = "javascript: var a = document.getElementsByClassName('Hold');a[0].innerHTML = a.length;";
    private String js9 = "javascript: var a = document.getElementsByClassName('tvp_app_banner');a[0].innerHTML = a.length;";
    private String js10 = "javascript: var a = document.getElementsByClassName('btn_user_text');a[0].innerHTML = a.length;";
    private String js11 = "javascript: var a = document.getElementsByClassName('info-vbox');a[0].innerHTML = a.length;";
    private String js12 = "javascript: var a = document.getElementsByClassName('j-leappMore');a[0].innerHTML = a.length;";
    private String js13 = "javascript: var a = document.getElementsByClassName('leapp_btn');a[0].innerHTML = a.length;";
    private String js14 = "javascript: var a = document.getElementsByClassName('j_a_app');a[0].innerHTML = a.length;";
    private String js15 = "javascript: var a = document.getElementsByClassName('player-info');a[0].innerHTML = a.length;";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.lp_web_layout);

        topViewBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.occlusion_layout, null, false);

        StatusBarUtils.transparencyBar(this);

        binding.webView.getSettings().setBlockNetworkImage(false);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setBuiltInZoomControls(false);
        binding.webView.getSettings().setSupportZoom(false);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.requestFocus();
        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);

        topViewBinding.rootView.setOnClickListener(this);
        binding.title.imgBack.setOnClickListener(this);


        binding.webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                LogUtils.e("onPageStarted" + url);

                if (!url.equals(getIntent().getStringExtra(KEY.URL))) {

                    topViewBinding.img.getLayoutParams().width = MyApplication.getInstance().getX();
                    if (url.contains("qq.com")) {
                        topViewBinding.view.getLayoutParams().height = 110;
                    } else if (url.contains("iqiyi")) {
                        topViewBinding.view.getLayoutParams().height = 160;
                    } else if (url.contains("youku")) {
                        topViewBinding.view.getLayoutParams().height = 120;
                    } else if (url.contains("sohu")) {
                        topViewBinding.view.getLayoutParams().height = 140;
                    } else if (url.contains("le")) {
                        topViewBinding.view.getLayoutParams().height = 110;
                    } else if (url.contains("pptv")) {
                        topViewBinding.view.getLayoutParams().height = 140;
                    } else if (url.contains("mgtv")) {
                        topViewBinding.view.getLayoutParams().height = 90;
                    }
                }
                if (((url.contains("vplay_") && url.contains("le.com")))
                        || ((url.contains(".shtml") && url.contains("sohu.com")))
                        || ((url.contains("show") && url.contains("pptv")))
                        || ((url.contains("cover") && url.contains("qq.com")))
                        || ((url.contains("video") && url.contains("youku")))
                        || ((url.contains("com/b/") && url.contains("mgtv")))
                        || ((url.contains("v_") && url.contains("iqiyi")))
                        || ((url.contains("channeled") && url.contains("sohu")))) {//vid=

                    if (binding.webView.getChildAt(0) != null) {
                        binding.webView.removeView(topViewBinding.rootView);
                    }
                    if (!getIntent().hasExtra(KEY.DISPLAY_SCREEN_VIEW)) {
                        binding.webView.addView(topViewBinding.rootView);
                    }

                } else {
                    if (TextUtils.isEmpty(getIntent().getStringExtra(KEY.TYPE))) {
                        if (binding.webView.getChildAt(0) != null) {
                            binding.webView.removeView(topViewBinding.rootView);
                        }
                    }

                    LogUtils.e(url + "\"条件不满足\"");
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);


                binding.webView.loadUrl(js);
                binding.webView.loadUrl(js1);
                binding.webView.loadUrl(js2);
                binding.webView.loadUrl(js3);
                binding.webView.loadUrl(js4);
                binding.webView.loadUrl(js5);
                binding.webView.loadUrl(js6);
                binding.webView.loadUrl(js7);
                binding.webView.loadUrl(js8);
                binding.webView.loadUrl(js9);
                binding.webView.loadUrl(js10);
                binding.webView.loadUrl(js11);
                binding.webView.loadUrl(js12);
                binding.webView.loadUrl(js13);
                binding.webView.loadUrl(js14);
                binding.webView.loadUrl(js15);

            }
        });

        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                LogUtils.e("newProgress==" + newProgress + "" +
                        "TextUtils.isEmpty(getIntent().getStringExtra(KEY.TYPE))==" + TextUtils.isEmpty(getIntent().getStringExtra(KEY.TYPE)));
                if (newProgress == 100) {
                    if (getIntent().hasExtra(KEY.TYPE)) {

//                        if (binding.webView.getChildAt(0) != null) {
//                            binding.webView.removeView(topViewBinding.rootView);
//                        }
                        if (!getIntent().hasExtra(KEY.DISPLAY_SCREEN_VIEW)) {
                            startActivity(new Intent(mContext, PAYWebActivityForWeb.class)
                                    .putExtra(KEY.URL, binding.webView.getUrl())
                                    .putExtra(KEY.COME_FORM_WEV_VIEW, "1"));
                        }

                    }
                }
            }
        });
        binding.loading.setVisibility(View.GONE);
        binding.title.imgBack.setOnClickListener(this);

        topViewBinding.getRoot().setOnClickListener(this);

        binding.webView.loadUrl(getIntent().getStringExtra(KEY.URL));
        binding.title.title.setText(getIntent().getStringExtra(KEY.TITLE));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.webView.destroy();
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
                    if (binding.webView.getChildAt(0) != null) {
                        binding.webView.removeView(topViewBinding.rootView);
                    }
                    binding.webView.goBack();
                } else {
                    finish();
                }
                break;
            case R.id.root_view:

//                if (binding.webView.getChildAt(0) != null) {
//                    binding.webView.removeView(topViewBinding.rootView);
//                }
//                if (!getIntent().hasExtra(KEY.TYPE)) {
                startActivity(new Intent(mContext, PAYWebActivityForWeb.class)
                        .putExtra(KEY.URL, binding.webView.getUrl())
                        .putExtra(KEY.COME_FORM_WEV_VIEW, "1"));
//                }


                break;
        }
    }

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {

            case 99:
                if (binding.webView.getChildAt(0) != null) {
                    binding.webView.removeView(topViewBinding.rootView);
                }

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
