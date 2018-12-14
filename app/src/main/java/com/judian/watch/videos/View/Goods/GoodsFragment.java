package com.judian.watch.videos.View.Goods;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.judian.watch.videos.DiyView.JsInterface;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.LogUtils;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.databinding.GoodsFragmentBinding;

import java.util.HashMap;

import static com.judian.watch.videos.Http.UrlUtils.URI.weitaoindex;

/**
 * Created by 李鹏 2017/11/25 0025.
 */

public class GoodsFragment extends Fragment implements MyHttpCallBack, View.OnClickListener {
    private Context mContext;
    private GoodsFragmentBinding binding;
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
            if (s.equals(weitaoindex)) {
                binding.back.setVisibility(View.GONE);
            } else {
                binding.back.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView webView, String url) {
            super.onPageFinished(webView, url);
            if (!url.equals(weitaoindex)) {
                binding.textView.setText(binding.web.getTitle());
            }
        }
    };

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            mContext = getActivity();

            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.goods_fragment, container, false);
            binding.web.setWebViewClient(webViewClient);
            binding.web.getSettings().setLoadsImagesAutomatically(true);
            binding.web.getSettings().setBlockNetworkImage(false);
            binding.web.addJavascriptInterface(new JsInterface(getActivity(), binding.web, webViewClient), "app");//
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

            binding.sousuo.setOnClickListener(this);
            binding.back.setOnClickListener(this);
            if (Build.VERSION.SDK_INT >= 21) {
                binding.web.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }


            HashMap<String, String> map = new HashMap<>();
            if (MyApplication.getInstance().isLogin()) {
                map.put("openid", MyApplication.getInstance().getUser().getData().getUnionid());
            } else {
                map.put("openid", "");
            }
            binding.web.loadUrl(weitaoindex, map);
            LogUtils.e(binding.web.getUrl());
            binding.smartRefreshLayout.setEnableLoadmore(false);
            binding.smartRefreshLayout.setOnRefreshListener(refreshlayout -> {

                refreshlayout.finishRefresh(1000);
                binding.web.reload();
            });

            binding.web.setWebChromeClient(new WebChromeClient() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);

                    if (newProgress > 70) {
                        binding.tvInfo.setVisibility(View.GONE);

                    } else if (newProgress % 10 == 0) {
                        if (binding.tvInfo.getVisibility() != View.VISIBLE) {
                            binding.tvInfo.setVisibility(View.VISIBLE);
                        }
                        binding.tvInfo.setText(newProgress + "%");
                    }
                }
            });
        }

        return binding.getRoot();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                binding.web.goBack();
                break;
            case R.id.sousuo:
                startActivity(new Intent(mContext, GoodsSsWebActivity.class));
                break;
        }
    }


}

