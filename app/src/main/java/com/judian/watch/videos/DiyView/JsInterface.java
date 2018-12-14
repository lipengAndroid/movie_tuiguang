package com.judian.watch.videos.DiyView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ali.auth.third.core.storage.aes.MD5;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.trade.biz.AlibcConstants;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Utils.AppUtils;
import com.judian.watch.videos.Utils.LogUtils;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.PreferencesUtil;

import java.util.HashMap;
import java.util.Map;

import static com.judian.watch.videos.Http.UrlUtils.REQUEST_URL;
import static com.judian.watch.videos.Http.UrlUtils.URI.backinformation;
import static com.judian.watch.videos.Http.UrlUtils.URI.checkorderfromyingyin;

/**
 * Created by 李鹏 2017/12/16 0016.
 */

public class JsInterface {
    private Activity Activity;
    private WebView webView;
    private WebViewClient webViewClient;
    private String uris, ids, type = "2", vip;
    private MyHttpCallBack myHttpCallBack;

    public JsInterface(Activity activity, WebView webView, WebViewClient webViewClient) {
        this.Activity = activity;
        this.webView = webView;
        this.webViewClient = webViewClient;

    }

    public JsInterface(Activity activity, WebView webView, WebViewClient webViewClient, MyHttpCallBack myHttpCallBack, String type) {
        this.Activity = activity;
        this.webView = webView;
        this.webViewClient = webViewClient;
        this.myHttpCallBack = myHttpCallBack;
        this.type = type;

    }

    public JsInterface(Activity activity, MyHttpCallBack myHttpCallBack, String vip) {
        this.Activity = activity;
        this.myHttpCallBack = myHttpCallBack;
        this.vip = vip;

    }

    private String id, URL;

    @JavascriptInterface
    public void openAd(String id, String URL) {
        LogUtils.e("_____________" + URL);
        if (id.equals("1")) {
            this.id = id;
            this.URL = URL;
            handler.sendEmptyMessage(51436);
        }
    }

    @JavascriptInterface
    public void purchasing(String uris) {
        this.uris = uris;
        handler.sendEmptyMessage(9);
    }

    private String order_id;

    @JavascriptInterface
    public void vip(String order_id) {
        this.order_id = order_id;
        handler.sendEmptyMessage(145);
    }

    @JavascriptInterface
    public void problem(String id) {
        ids = id;
        handler.sendEmptyMessage(99);
    }

    @JavascriptInterface
    public void shareUser() {
        handler.sendEmptyMessage(90);
    }

    @JavascriptInterface
    public void sharePyq() {
        handler.sendEmptyMessage(91);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 9:
                    AlibcTaokeParams taokeParams = new AlibcTaokeParams();
                    taokeParams.pid = "";
                    AlibcPage tradePage = new AlibcPage(uris);
                    AlibcShowParams showParams;
                    if (AppUtils.isTBAppInstalled(MyApplication.getInstance())) {
                        showParams = new AlibcShowParams(OpenType.Native, true);
                    } else {
                        showParams = new AlibcShowParams(OpenType.H5, true);
                    }
                    Map<String, String> exParams = new HashMap<>();
                    exParams.put(AlibcConstants.ISV_CODE, MyApplication.getInstance().getVersionCodeString());
                    AlibcTrade.show(
                            Activity,
                            null,
                            webViewClient,
                            null,
                            tradePage,
                            showParams,
                            taokeParams,
                            exParams,
                            new AlibcTradeCallback() {

                                @Override
                                public void onTradeSuccess(AlibcTradeResult tradeResult) {

                                    LogUtils.e("onFailure=code" + tradeResult.payResult + "msg" + tradeResult.resultType);
                                    //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                                }

                                @Override
                                public void onFailure(int code, String msg) {
                                    LogUtils.e("onFailure=code" + code + "msg" + msg);
                                    //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                                }
                            });
                    break;
                case 99:
                    webView.loadUrl(REQUEST_URL + "/index.php?s=index/Problem/problem_content/id/" + ids);

                    LogUtils.e(REQUEST_URL + "/index.php?s=index/Problem/problem_content/id/" + ids);
                    break;
                case 91:

                    new OkHttpUtils(11, myHttpCallBack)
                            .SetApiUrl(backinformation)
                            .SetKey("type", "id", "unionid")
                            .SetValue(type, "", PreferencesUtil.getString("uid"))
                            .POST();
                    break;
                case 90:
                    new OkHttpUtils(12, myHttpCallBack)
                            .SetApiUrl(backinformation)
                            .SetKey("type", "id", "unionid")
                            .SetValue(type, "", PreferencesUtil.getString("uid"))
                            .POST();
                    break;
                case 51436:
                    Uri uri = Uri.parse(URL);
                    Intent viewIntent = new Intent(Intent.ACTION_VIEW, uri);
                    Activity.startActivity(viewIntent);
                    break;
                case 145:
                    new OkHttpUtils(1478, myHttpCallBack)
                            .SetApiUrl(checkorderfromyingyin)
                            .SetKey("orderid", "unionid", "vip", "sign")
                            .SetValue(order_id,
                                    MyApplication.getInstance().getUser().getData().getUnionid()
                                    , vip,
                                    MD5.getMD5(vip + "judian88"))
                            .PostNoUrl();
                    break;
            }
        }
    };

}