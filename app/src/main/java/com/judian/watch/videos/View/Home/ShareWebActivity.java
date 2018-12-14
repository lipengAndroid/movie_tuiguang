package com.judian.watch.videos.View.Home;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bumptech.glide.Glide;
import com.cuihai.library.StatusBarUtils;
import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.DiyView.JsInterface;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Mode.ShareMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.LogUtils;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.PreferencesUtil;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.Utils.WxShareUtils;
import com.judian.watch.videos.View.Dialog.ShareDialog;
import com.judian.watch.videos.databinding.LpWebLayoutBinding;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.util.concurrent.ExecutionException;

import static com.judian.watch.videos.Http.UrlUtils.URI.backinformation;
import static com.judian.watch.videos.Http.UrlUtils.URI.problem;
import static com.judian.watch.videos.Mode.KEY.TYPE;

/**
 * Created by 李鹏 2017/11/29 0029.
 */

public class ShareWebActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack {

    public LpWebLayoutBinding binding;
    private String type = "2";

    @SuppressLint({"SetJavaScriptEnabled", "JavascriptInterface", "AddJavascriptInterface"})
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.lp_web_layout);

        StatusBarUtils.transparencyBar(this);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setBlockNetworkImage(false);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setBuiltInZoomControls(false);
        binding.webView.getSettings().setSupportZoom(false);
        binding.webView.getSettings().setDomStorageEnabled(true);
        binding.webView.requestFocus();
        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);

//        binding.webView.addJavascriptInterface(new JsInterface(), "app");
        WebViewClient webViewClient = new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);


            }
        };
        binding.webView.setWebChromeClient(new WebChromeClient() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                binding.tvInfo.setText(newProgress + "%");
                if (newProgress == 100) {
                    binding.loading.setVisibility(View.GONE);
                }
            }
        });
        if (getIntent().hasExtra(TYPE)) {
            if (getIntent().getStringExtra(TYPE).equals("1")) {
                type = "1";
            }
        }
        binding.webView.setWebViewClient(webViewClient);

        binding.webView.addJavascriptInterface(new JsInterface(this, binding.webView, webViewClient,
                this, type), "app");

        binding.title.imgBack.setOnClickListener(this);

        binding.title.rightButton.setText(R.string.fx);

        binding.title.rightButton.setOnClickListener(this);

        binding.webView.loadUrl(getIntent().getStringExtra(KEY.URL));

        LogUtils.e(getIntent().getStringExtra(KEY.URL));

        binding.title.title.setText(getIntent().getStringExtra(KEY.TITLE));

        if (getIntent().getStringExtra(KEY.URL).equals(problem)) {
            binding.title.rightButton.setText("");
        }

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
            case R.id.right_button:
                new OkHttpUtils(88, ShareWebActivity.this)
                        .SetApiUrl(backinformation)
                        .SetKey("type", "id", "unionid")
                        .SetValue(type, "", PreferencesUtil.getString("uid"))
                        .POST();
                break;
        }
    }

    private Bitmap bitmap;
    private ShareMode shareMode;

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 88:
                shareMode = new JsonUtil<ShareMode>().json2Bean(jsonString, ShareMode.class.getName());
                getBitMap(3);
                break;
            case 12:
                shareMode = new JsonUtil<ShareMode>().json2Bean(jsonString, ShareMode.class.getName());
                MyApplication.getInstance().setWxLogin(false);
                getBitMap(1);
                break;
            case 11:
                shareMode = new JsonUtil<ShareMode>().json2Bean(jsonString, ShareMode.class.getName());
                MyApplication.getInstance().setWxLogin(false);
                getBitMap(2);
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (bitmap == null || shareMode == null) {
                        ToastUtils.show("数据获取失败，请稍后重试");
                        return;
                    }
                    WxShareUtils.sendWxWeb(mContext, shareMode.getUrl(),
                            shareMode.getTitle(),
                            shareMode.getContent(),
                            SendMessageToWX.Req.WXSceneSession, bitmap);
                    break;
                case 2:
                    if (bitmap == null || shareMode == null) {
                        ToastUtils.show("数据获取失败，请稍后重试");
                        return;
                    }
                    WxShareUtils.sendWxWeb(mContext, shareMode.getUrl(),
                            shareMode.getTitle(),
                            SendMessageToWX.Req.WXSceneTimeline, bitmap);
                    break;
                case 3:
                    if (bitmap == null || shareMode == null) {
                        ToastUtils.show("数据获取失败，请稍后重试");
                        return;
                    }
                    ShareDialog.show(mContext, shareMode.getTitle(), shareMode.getContent(), shareMode.getUrl(), bitmap);
                    break;
            }
        }
    };

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }

    public void getBitMap(int msg) {
        new Thread(() -> {
            try {
                bitmap = Glide.with(mContext)
                        .load(shareMode.getImageurl())
                        .asBitmap() //必须
                        .centerCrop()
                        .into(80, 80)
                        .get();
                Thread.sleep(1000);
                handler.sendEmptyMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
