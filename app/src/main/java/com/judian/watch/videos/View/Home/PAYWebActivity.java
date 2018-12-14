//package com.judian.watch.videos.View.Home;
//
//import android.annotation.SuppressLint;
//import android.content.pm.ActivityInfo;
//import android.content.res.Configuration;
//import android.databinding.DataBindingUtil;
//import android.graphics.Bitmap;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.text.TextUtils;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.cuihai.library.StatusBarUtils;
//import com.judian.watch.videos.DiyView.BaseActivity;
//import com.judian.watch.videos.DiyView.JsInterface;
//import com.judian.watch.videos.Http.OkHttpUtils;
//import com.judian.watch.videos.Interface.MyHttpCallBack;
//import com.judian.watch.videos.Mode.KEY;
//import com.judian.watch.videos.Mode.ShareMode;
//import com.judian.watch.videos.Mode.UriListMode;
//import com.judian.watch.videos.R;
//import com.judian.watch.videos.Utils.JsonUtil;
//import com.judian.watch.videos.Utils.LogUtils;
//import com.judian.watch.videos.Utils.MyApplication;
//import com.judian.watch.videos.Utils.PreferencesUtil;
//import com.judian.watch.videos.Utils.WxShareUtils;
//import com.judian.watch.videos.View.Dialog.PyqDialog;
//import com.judian.watch.videos.databinding.PayWebLayoutBinding;
//import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
//
//import java.util.HashMap;
//import java.util.concurrent.ExecutionException;
//
//import static com.judian.watch.videos.Http.UrlUtils.URI.backinformation;
//import static com.judian.watch.videos.Http.UrlUtils.URI.getdomain;
//import static com.judian.watch.videos.Mode.KEY.VOD_ID;
//
///**
// * Created by 李鹏 2017/11/29 0029.
// */
//
//public class PAYWebActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack {
//
//    public PayWebLayoutBinding binding;
//
//    private String js = "javascript: var a = document.getElementsByClassName('header-app');a[0].innerHTML = a.length;";
//    private String js1 = "javascript: var a = document.getElementsByClassName('m-box-Ptop');a[0].innerHTML = a.length;";
//    private String js2 = "javascript: var a = document.getElementsByClassName('data-item');a[0].innerHTML = a.length;";
//    private String js3 = "javascript: var a = document.getElementsByClassName('link-login');a[0].innerHTML = a.length;";
//    private String js4 = "javascript: var a = document.getElementsByClassName('iqy-items');a[0].innerHTML = a.length;";
//    private String js5 = "javascript: var a = document.getElementsByClassName('vo-link');a[0].innerHTML = a.length;";
//    private String js6 = "javascript: var a = document.getElementsByClassName('yk-ucenter');a[0].innerHTML = a.length;";
//    private String js7 = "javascript: var a = document.getElementsByClassName('x_login_icon');a[0].innerHTML = a.length;";
//    private String js8 = "javascript: var a = document.getElementsByClassName('Hold');a[0].innerHTML = a.length;";
//    private String js9 = "javascript: var a = document.getElementsByClassName('tvp_app_banner');a[0].innerHTML = a.length;";
//    private String js10 = "javascript: var a = document.getElementsByClassName('btn_user_text');a[0].innerHTML = a.length;";
//    private String js11 = "javascript: var a = document.getElementsByClassName('info-vbox');a[0].innerHTML = a.length;";
//    private String js12 = "javascript: var a = document.getElementsByClassName('j-leappMore');a[0].innerHTML = a.length;";
//    private String js13 = "javascript: var a = document.getElementsByClassName('leapp_btn');a[0].innerHTML = a.length;";
//    private String js14 = "javascript: var a = document.getElementsByClassName('j_a_app');a[0].innerHTML = a.length;";
//    private String js15 = "javascript: var a = document.getElementsByClassName('player-info');a[0].innerHTML = a.length;";
//
//    private TextView tv;
//
//    private View xCustomView;
//    private WebChromeClient.CustomViewCallback xCustomViewCallback;
//
//    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface"})
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = DataBindingUtil.setContentView(this, R.layout.pay_web_layout);
//
//        StatusBarUtils.transparencyBar(this);
//        View views = binding.loading;
//        tv = views.findViewById(R.id.tv_info);
//        WebSettings settings = binding.webView.getSettings();
//        settings.setLoadsImagesAutomatically(true);
//        settings.setBlockNetworkImage(false);
//        settings.setJavaScriptEnabled(true);
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
//        settings.setAllowFileAccess(true);
//        settings.setLoadWithOverviewMode(true);
//        settings.setUseWideViewPort(true);
//        settings.setBuiltInZoomControls(false);
////        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
////        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
//
//        WebViewClient webViewClient = new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//
//                super.onPageStarted(view, url, favicon);
//                if (url.contains("https://vod.beichengo.com/index.php/vod-play-id-")) {
//                    binding.title.rightButtonImg.setVisibility(View.VISIBLE);
//                } else {
//                    binding.title.rightButtonImg.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                LogUtils.e(binding.webView.getTitle() + "uri=" + url);
//                try {
//                    String title = binding.webView.getTitle();
//
//                    if (title.contains("《") && title.contains("》")) {
//                        title = title.replace("《", "");
//                        String[] titles = title.split("》");
//                        binding.title.title.setText(titles[0]);
//                    } else {
//                        binding.title.title.setText(view.getTitle());
//                    }
//                } catch (Exception e) {
//                    binding.title.title.setText(view.getTitle());
//                }
//
//
//                super.onPageFinished(view, url);
//
//                binding.webView.loadUrl(js);
//                binding.webView.loadUrl(js1);
//                binding.webView.loadUrl(js2);
//                binding.webView.loadUrl(js3);
//                binding.webView.loadUrl(js4);
//                binding.webView.loadUrl(js5);
//                binding.webView.loadUrl(js6);
//                binding.webView.loadUrl(js7);
//                binding.webView.loadUrl(js8);
//                binding.webView.loadUrl(js9);
//                binding.webView.loadUrl(js10);
//                binding.webView.loadUrl(js11);
//                binding.webView.loadUrl(js12);
//                binding.webView.loadUrl(js13);
//                binding.webView.loadUrl(js14);
//                binding.webView.loadUrl(js15);
//
//            }
//        };
//        binding.webView.setWebViewClient(webViewClient);
//
//        binding.webView.addJavascriptInterface(new JsInterface(this, binding.webView, webViewClient), "app");
//        binding.title.imgBack.setOnClickListener(this);
//
//        HashMap<String, String> map;
//        map = new HashMap<>();
//        if (MyApplication.getInstance().isLogin()) {
//            map.put("openid", MyApplication.getInstance().getUser().getUnionid());
//        } else {
//            map.put("openid", "");
//        }
//        if (Build.VERSION.SDK_INT >= 21) {// 支持https 混用http
//            binding.webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        binding.webView.loadUrl(getIntent().getStringExtra(KEY.URL), map);
//        binding.title.title.setText(getIntent().getStringExtra(KEY.TITLE));
//
//        binding.webView.setWebChromeClient(new WebChromeClient() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                tv.setText(newProgress + "%");
//                if (newProgress == 100) {
//                    binding.loading.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onShowCustomView(View view, CustomViewCallback callback) {
//                super.onShowCustomView(view, callback);
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//                binding.webView.setVisibility(View.INVISIBLE);
//                binding.title.rootView.setVisibility(View.GONE);
//                // 如果一个视图已经存在，那么立刻终止并新建一个
//                if (xCustomView != null) {
//                    callback.onCustomViewHidden();
//                    return;
//                }
//                binding.mFrameLayout.addView(view);
//                xCustomView = view;
//                xCustomViewCallback = callback;
//            }
//
//            // 视频播放退出全屏会被调用的
//            @Override
//            public void onHideCustomView() {
//                binding.title.rootView.setVisibility(View.VISIBLE);
//                binding.webView.setVisibility(View.VISIBLE);
//                if (xCustomView == null)// 不是全屏播放状态
//                    return;
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                xCustomView.setVisibility(View.GONE);
//                binding.mFrameLayout.removeView(xCustomView);
//                xCustomView = null;
//                xCustomViewCallback.onCustomViewHidden();
//            }
//        });
//        MyApplication.getInstance().setUri("");
//        MyApplication.getInstance().setTitle("");
//
//        MyApplication.getInstance().setVid("");
//        binding.title.rightButtonImg.setImageResource(R.drawable.ic_quanping);
//        binding.title.rightButtonImg.setVisibility(View.VISIBLE);
////        if (getIntent().hasExtra(KEY.TYPE)) {
////
////        } else {
////            binding.title.rightButtonImg.setVisibility(View.GONE);
////        }
//        binding.title.rightButtonImg.setOnClickListener(v -> {
////                if (getIntent().hasExtra(KEY.TYPE)) {
//            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//                binding.title.rootView.setVisibility(View.GONE);
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            } else {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//                binding.title.rootView.setVisibility(View.VISIBLE);
//            }
////                }
//        });
//        if (getIntent().hasExtra(KEY.STAR)) {
//            try {
//                if (getIntent().getStringExtra(KEY.STAR).equals("5")) {
//                    if (TextUtils.isEmpty(PreferencesUtil.getString(KEY.URL_LIST))) {
//                        dialog();
//                    } else {
//                        boolean xianshi = false;
//                        UriListMode mode = new JsonUtil<UriListMode>()
//                                .json2Bean(PreferencesUtil.getString(KEY.URL_LIST), UriListMode.class.getName());
//
//                        for (int i = 0; i < mode.getList().size(); i++) {
//                            try {
//                                if (binding.webView.getUrl().contains(mode.getList().get(i).getVid())) {
//                                    xianshi = true;
//                                    i = mode.getList().size();
//                                }
//                            } catch (Exception ignored) {
//                            }
//
//                        }
//                        if (!xianshi) {
//                            dialog();
//                        }
//                    }
//
//                }
//            } catch (Exception ignored) {
//            }
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        super.onResume();
//        binding.webView.onResume();
//        binding.webView.resumeTimers();
//
//        /*
//         * 设置为横屏
//         */
//        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }
//    }
//
//    private void dialog() {
//        PyqDialog.show(mContext, ButtType -> {
//            if (ButtType == 1) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    finishAfterTransition();
//                } else {
//                    finish();
//                }
//
//            } else {
//                MyApplication.getInstance().setUri(binding.webView.getUrl());
//                MyApplication.getInstance().setVid(getIntent().getStringExtra(KEY.VOD_ID));
//                new OkHttpUtils(515, this)
//                        .SetApiUrl(getdomain)
//                        .SetKey("unionid", "type", "id")
//                        .SetValue(MyApplication.getInstance().getUser().getUnionid(),
//                                "2", getIntent().getStringExtra(KEY.VOD_ID))
//                        .POST();
//            }
//        });
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.img_back:
//                if (binding.webView.canGoBack()) {
//                    binding.webView.goBack();
//                } else {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        finishAfterTransition();
//                    } else {
//                        finish();
//                    }
//                }
//                break;
//        }
//    }
//
//    private Bitmap bitmap;
//
//    @Override
//    public void ok(String jsonString, int httpTY) {
//        switch (httpTY) {
//            case 515:
//
//                if (getIntent().hasExtra(VOD_ID)) {
//                    new OkHttpUtils(98, this)
//                            .SetApiUrl(backinformation)
//                            .SetKey("type", "id")
//                            .SetValue("3", getIntent().getStringExtra(VOD_ID))
//                            .POST();
//                }
//                break;
//            case 98:
//                mode = new JsonUtil<ShareMode>()
//                        .json2Bean(jsonString, ShareMode.class.getName());
//
//                new Thread(() -> {
//                    try {
//                        bitmap = Glide.with(MyApplication.getInstance())
//                                .load(getIntent().getStringExtra(KEY.IMG_URL))
//                                .asBitmap() //必须
//                                .centerCrop()
//                                .into(86, 86)
//                                .get();
//                        handler.sendEmptyMessage(9);
//                    } catch (InterruptedException | ExecutionException e) {
//                        e.printStackTrace();
//                    }
//                }).start();
//
//
//                break;
//        }
//    }
//
//    private ShareMode mode;
//    @SuppressLint("HandlerLeak")
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 9:
//                    MyApplication.getInstance().setWxLogin(false);
//                    WxShareUtils.sendWxWeb(mContext, mode.getUrl(),
//                            "《" + binding.webView.getTitle() + "》 " + mode.getTitle(),
//                            SendMessageToWX.Req.WXSceneTimeline, bitmap);
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        finishAfterTransition();
//                    } else {
//                        finish();
//                    }
//                    break;
//
//            }
//        }
//    };
//
//    @Override
//    public void error(String e, int uriType) {
//
//    }
//
//    @Override
//    public void downloadUpProgress(long Percentile, int httpTY) {
//
//    }
//
//
//    @Override
//    public void onConfigurationChanged(Configuration config) {
//        super.onConfigurationChanged(config);
//        switch (config.orientation) {
//            case Configuration.ORIENTATION_LANDSCAPE:
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                break;
//            case Configuration.ORIENTATION_PORTRAIT:
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
//                break;
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        binding.webView.onPause();
//    }
//
//
//    @Override
//    public void onBackPressed() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            finishAfterTransition();
//        } else {
//            finish();
//        }
//        super.onBackPressed();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        ViewGroup view = (ViewGroup) getWindow().getDecorView();
//        view.removeAllViews();
//    }
//
//}
//
