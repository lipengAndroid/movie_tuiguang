package com.judian.watch.videos.View.Goods;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cuihai.library.StatusBarUtils;
import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.DiyView.JsInterface;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.databinding.GoodsSsActivityBinding;

import java.util.HashMap;


/**
 * Created by 李鹏 2017/12/6 0006.
 */

public class GoodsSsWebActivity extends BaseActivity implements MyHttpCallBack, View.OnClickListener {
    private GoodsSsActivityBinding binding;

    /**
     *
     */
    private HashMap<String, String> map;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.goods_ss_activity);
        StatusBarUtils.transparencyBar(this);

        binding.web.getSettings().setLoadsImagesAutomatically(true);
        binding.web.getSettings().setBlockNetworkImage(false);
        binding.web.getSettings().setJavaScriptEnabled(true);
        binding.web.getSettings().setBuiltInZoomControls(false);
        binding.web.getSettings().setSupportZoom(false);
//        binding.web.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 开启 DOM storage API 功能
        binding.web.requestFocus();
        binding.web.getSettings().setUseWideViewPort(true);
        binding.web.getSettings().setLoadWithOverviewMode(true);


//        binding.web.getSettings().setDomStorageEnabled(true);//是否支持持久化存储，保存到本地
//        binding.web.getSettings().setDatabaseEnabled(true);//开启database storage API 功能
//        binding.web.getSettings().setDatabasePath(MyApplication.getInstance().getCacheDir().getPath());//设置数据库缓存路径
//        binding.web.getSettings().setAppCacheEnabled(true);//设置开启application H5 Caches 功能
//        binding.web.getSettings().setAppCachePath(MyApplication.getInstance().getCacheDir().getPath());//设置application
        binding.ss.setOnClickListener(this);

        binding.imgBack.setOnClickListener(this);
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        };

        binding.web.addJavascriptInterface(new JsInterface(this, binding.web, webViewClient), "app");//
        if (Build.VERSION.SDK_INT >= 21) {
            binding.web.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        binding.web.setWebViewClient(webViewClient);
        map = new HashMap<>();
        if (MyApplication.getInstance().isLogin()) {
            map.put("openid", MyApplication.getInstance().getUser().getData().getUnionid());
        } else {
            map.put("openid", "");
        }
        binding.web.loadUrl("https://www.judianweitao.com/App.php?s=/Search/search", map);

        binding.smartRefreshLayout.setLoadmoreFinished(false);
        binding.smartRefreshLayout.setOnRefreshListener(refreshlayout -> {
            refreshlayout.finishRefresh(1000);
            binding.web.reload();
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                if (binding.web.canGoBack()){
                    binding.web.goBack();
                }else {
                    finish();
                }
                break;
            case R.id.ss:
                if (TextUtils.isEmpty(binding.edSs.getText() + "")) {
                    ToastUtils.show("请输入您要搜索的商品");
                    return;
                }
                binding.web.loadUrl("https://www.judianweitao.com/App.php?s=Search/searchcontent&key=" +
                        binding.edSs.getText() + "", map);
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