package com.judian.watch.videos.View.Shopping;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.judian.watch.videos.DiyView.JsInterface;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.LogUtils;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.databinding.TitleWebLayoutBinding;

import java.util.HashMap;

public class ShoppingFragment extends Fragment implements View.OnClickListener {
    public TitleWebLayoutBinding binding;
    private String uri = "https://www.judianweitao.com/public/index.php?s=h5/grab/realtime";

//    private String uri = "https://www.baidu.com";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.title_web_layout, container, false);
            binding.webView.getSettings().setBlockNetworkImage(true);
            if (Build.VERSION.SDK_INT >= 21) {
                binding.webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }

            binding.webView.getSettings().setBlockNetworkImage(false);
            binding.webView.getSettings().setDomStorageEnabled(true);
            binding.webView.getSettings().setJavaScriptEnabled(true);
            binding.webView.getSettings().setAllowContentAccess(true);
            binding.webView.getSettings().setAllowFileAccessFromFileURLs(true);
            binding.webView.getSettings().setAppCacheEnabled(false);
            binding.webView.getSettings().setLoadWithOverviewMode(true);
            binding.webView.getSettings().setUseWideViewPort(true);
            binding.webView.getSettings().setBlockNetworkImage(false);

            binding.title.imgBack.setVisibility(View.INVISIBLE);
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

                @Override
                public void onPageStarted(WebView view, String u, Bitmap favicon) {
                    if (!u.equals(uri)) {
                        binding.title.imgBack.setVisibility(View.VISIBLE);
                    } else {
                        binding.title.imgBack.setVisibility(View.INVISIBLE);
                    }
                    super.onPageStarted(view, u, favicon);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //此处进行逻辑处理
                    view.loadUrl(url);
                    return true;
                }

            };

            binding.webView.setWebChromeClient(new WebChromeClient() {
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

            binding.webView.setWebViewClient(webViewClient);
            binding.webView.addJavascriptInterface(new JsInterface(getActivity(), binding.webView, webViewClient), "app");
            binding.webView.loadUrl(uri, map);
            binding.title.title.setText("疯抢榜");

        }

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                binding.webView.goBack();
                break;
        }
    }
}
