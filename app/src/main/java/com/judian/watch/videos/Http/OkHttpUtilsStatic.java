package com.judian.watch.videos.Http;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.CacheMode;
import com.judian.watch.videos.Mode.PublicMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.FileUtils;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.LogUtils;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.NetWorkUtils;
import com.judian.watch.videos.Utils.PreferencesUtil;
import com.judian.watch.videos.Utils.StringUtils;
import com.judian.watch.videos.Utils.TimeUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

import static com.judian.watch.videos.Http.UrlUtils.DOWNLOAD;
import static com.judian.watch.videos.Http.UrlUtils.DOWNLOAD_png;
import static com.judian.watch.videos.Http.UrlUtils.GET;
import static com.judian.watch.videos.Http.UrlUtils.POST;
import static com.judian.watch.videos.Http.UrlUtils.POST_TWO;
import static com.judian.watch.videos.Http.UrlUtils.REQUEST_URL;
import static com.judian.watch.videos.Http.UrlUtils.UP_FILE;
import static com.judian.watch.videos.Http.UrlUtils.URI.GET_WX_USER_INFO;
import static com.judian.watch.videos.Http.UrlUtils.URI.OAUTH;
import static com.judian.watch.videos.Http.UrlUtils.URI.WX_TOKEY_IS_OLD;


/**
 * Created by 10237 on 2016/11/11.
 * <p>
 * 网络请求
 */

public class OkHttpUtilsStatic {
    private static OkHttpUtilsStatic okHttpUtilsStatic;
    private static OkHttpClient okHttpClient;
    private Request request = null;
    private String RequestUri = REQUEST_URL;// 服务器地址
    private String ApiUri;
    private String[] key, value;
    private boolean Cache = false;
    private File file;
    private static final int ok = 0;
    private static final int error = 1;
    private HttpHandler handler;
    private String StringJson = MyApplication.getInstance().getString(R.string.qpsb);
    private final static String errorString = MyApplication.getInstance().getString(R.string.qpsb);
    private static MyHttpCallBack callBack;
    private static int httpType = 0;
    private long oldPercentage;
    private long UserPercentage;
    private long stimr;
    private static Call call;

    public static synchronized OkHttpUtilsStatic get(int httpTypes, MyHttpCallBack callBacks) {
        if (okHttpUtilsStatic == null) {
            okHttpUtilsStatic = new OkHttpUtilsStatic();
        }
        httpType = httpTypes;
        callBack = callBacks;

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .sslSocketFactory(createSSLSocketFactory(), new X509TrustManagers())
                .hostnameVerifier(new HostnameVerifiers())
                .build();
        return okHttpUtilsStatic;
    }

    public OkHttpClient get() {
        return okHttpClient;
    }


    public OkHttpUtilsStatic SetApiUrl(String uri) {
        ApiUri = uri;
        return this;
    }

    public OkHttpUtilsStatic SetKey(String... key) {
        this.key = key;
        return this;
    }

    public OkHttpUtilsStatic SetValue(String... value) {
        this.value = value;
        return this;
    }

    public OkHttpUtilsStatic SetFile(File file) {
        this.file = file;
        return this;
    }

    public OkHttpUtilsStatic isCache() {
        this.Cache = true;
        return this;
    }

    public void GET() {
        request = new Request.Builder()
                .url(RequestUri + ApiUri + ParamString(key, value))
                .build();
        Request(GET);
    }

    public void GetNoUrl() {
        request = new Request.Builder()
                .url(ApiUri + ParamString(key, value))
                .tag("0")
                .build();
        Request(GET);
    }

    public OkHttpUtilsStatic POST() {

        request = new Request.Builder()
                .url(RequestUri + ApiUri)
                .post(mRequestBody(key, value))
                .build();
        Request(POST);
        return this;
    }

    public void PostNoUrl() {
        request = new Request.Builder()
                .url(ApiUri)
                .post(mRequestBody(key, value))
                .build();
        Request(POST);
    }


    //    .addHeader("timestamp", timestamp)
//                .addHeader("sign", TxtUtils.Md5(timestamp + "zsj2017"))
//            .addHeader("token", tonken)
    public void Download() {
        request = new Request.Builder()
                .url(ApiUri)
                .build();
        Request(DOWNLOAD);
    }

    /*    public OkHttpUtils DownloadPng() {
            request = new Request.Builder()
                    .url(ApiUri)
                    .build();
            Request(DOWNLOAD_png);
            return this;
        }*/
    public OkHttpUtilsStatic cancleAll() {
        try {
            call.cancel();
        } catch (Exception ignored) {
        }

        return this;
    }


    public void UpFile() {
        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        multipartBody.addFormDataPart("qrcode", file.getName(), createCustomRequestBody(MultipartBody.FORM, file));
        multipartBody.addFormDataPart("unionid", MyApplication.getInstance().getUser().getData().getUnionid());
        request = new Request.Builder()
                .url(REQUEST_URL + ApiUri)
                .post(multipartBody.build())
                .build();
        Request(UP_FILE);
    }

    private void Request(final int RequestType) {

        stimr = System.currentTimeMillis();
        handler = new HttpHandler();

        if (Cache && !TextUtils.isEmpty(PreferencesUtil.getString(ApiUri + ParamString(key, value)))) {
            CacheMode cacheString = new JsonUtil<CacheMode>().json2Bean(PreferencesUtil.getString(ApiUri + ParamString(key, value)),
                    CacheMode.class.getName());
            StringJson = cacheString.getJsonString();
            handler.sendEmptyMessage(ok);

            LogUtils.e("缓存已加载");
        }
        if (!NetWorkUtils.isNetworkConnected()) {
            callBack.error("网络未连接", httpType);
            return;
        }

        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                LogUtils.e("onFailure" + e.getMessage());
//                handler.sendEmptyMessage(error);

            }

            @Override
            public void onResponse(Call call, Response response) {
                switch (RequestType) {
                    case DOWNLOAD:

                        File file = FileUtils.newApkFile();
                        InputStream is = null;
                        byte[] buf = new byte[2048];
                        int len;
                        FileOutputStream fos = null;
                        try {
                            long total = response.body().contentLength();
                            long current = 0;
                            is = response.body().byteStream();
                            fos = new FileOutputStream(file);
                            int Percentile = (int) (total / 100);
                            while ((len = is.read(buf)) != -1) {
                                current += len;
                                fos.write(buf, 0, len);
                                if ((current != 0) && (Percentile != 0) && StringUtils.isInteger((current / Percentile) + "")) {
                                    UserPercentage = (current / Percentile);
                                    callBack.downloadUpProgress((current / Percentile), httpType);
                                    handler.sendEmptyMessage(DOWNLOAD);
                                }
                                oldPercentage = UserPercentage;

                            }

                            StringJson = file.getPath();
                            handler.sendEmptyMessage(ok);
                            fos.close();
                            is.close();
                        } catch (IOException ignored) {
                        } finally {
                            try {
                                if (is != null) {
                                    is.close();
                                }
                                if (fos != null) {
                                    fos.close();
                                }
                            } catch (IOException e) {
                                e.fillInStackTrace();
                            }
                        }
                        break;
                    case DOWNLOAD_png:

                        File pngFile = FileUtils.newPngFile();
                        InputStream pngis = null;
                        byte[] pngbuf = new byte[2048];
                        int pnglen;
                        FileOutputStream pngfos = null;
                        try {
                            long total = response.body().contentLength();
                            long current = 0;
                            pngis = response.body().byteStream();
                            pngfos = new FileOutputStream(pngFile);
                            int Percentile = (int) (total / 100);
                            while ((pnglen = pngis.read(pngbuf)) != -1) {
                                current += pnglen;
                                pngfos.write(pngbuf, 0, pnglen);
                                if ((current != 0) && (Percentile != 0) && StringUtils.isInteger((current / Percentile) + "")) {
                                    UserPercentage = (current / Percentile);
                                    callBack.downloadUpProgress((current / Percentile), httpType);
                                    handler.sendEmptyMessage(DOWNLOAD);
                                }
                                oldPercentage = UserPercentage;

                            }

                            StringJson = pngFile.getPath();
                            handler.sendEmptyMessage(ok);
                            pngfos.close();
                            pngis.close();
                        } catch (IOException ignored) {
                        } finally {
                            try {
                                if (pngis != null) {
                                    pngis.close();
                                }
                                if (pngfos != null) {
                                    pngfos.close();
                                }
                            } catch (IOException e) {
                                e.fillInStackTrace();
                            }
                        }
                        break;
                    case GET:
                    case POST:
                    case POST_TWO:

                        LogUtils.e("api==" + ApiUri + "_______耗时=" + (System.currentTimeMillis() - stimr) + "");
                        try {
                            StringJson = response.body().string();
                            LogUtils.e(StringJson);


                            PublicMode mode = new JsonUtil<PublicMode>()
                                    .json2Bean(StringJson, PublicMode.class.getName());
                            if (ApiUri.equals(OAUTH) || ApiUri.equals(GET_WX_USER_INFO) ||
                                    ApiUri.equals(WX_TOKEY_IS_OLD)) {
                                handler.sendEmptyMessage(ok);
                            } else {
                                try {
                                    if (mode.getType() == 1) {
                                        if (Cache) {
                                            CacheMode cacheMode = new CacheMode();
                                            cacheMode.setTime(TimeUtils.getYearMonthAndDay(System.currentTimeMillis()));
                                            cacheMode.setJsonString(StringJson);
                                            String cacheString = new JsonUtil<CacheMode>().bean2Json(cacheMode);
                                            PreferencesUtil.putString(ApiUri + ParamString(key, value), cacheString);
                                        }
                                        handler.sendEmptyMessage(ok);
                                    } else {
                                        StringJson = mode.getMsg();
                                        handler.sendEmptyMessage(error);
                                    }
                                } catch (Exception e) {
                                    StringJson = errorString;
                                    handler.sendEmptyMessage(error);
                                }

                            }
                        } catch (IOException e) {
                            StringJson = errorString;
                            handler.sendEmptyMessage(error);
                        }
                        break;
                    case UP_FILE:
                        try {
                            String json = response.body().string();
                            JSONObject jsonObject = new JSONObject(json);
                            String url = jsonObject.getString("url");
                            String code = jsonObject.getString("type");

                            LogUtils.e("UP_FILE" + json);
                            if (code.equals("1")) {
                                StringJson = url;
                                handler.sendEmptyMessage(ok);
                            } else {
                                StringJson = "上传失败";
                                LogUtils.e("2");
                                handler.sendEmptyMessage(error);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(error);
                            LogUtils.e("11");
                            StringJson = "上传失败";
                        }
                        break;

                }
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private class HttpHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ok:
//                    try {
                    callBack.ok(StringJson, httpType);
//                    } catch (Exception ignored) {
//                        LogUtils.e(ignored.getMessage());
//                    }

                    break;
                case error:
                    callBack.error(StringJson, httpType);
                    break;
                case UP_FILE:
                    callBack.downloadUpProgress(UserPercentage, httpType);
                    break;

            }
        }
    }

    private String ParamString(String[] key, String[] value) {
        StringBuilder mParamString = new StringBuilder();
        if (key == null || key.length == 0) {
            return mParamString.toString();
        } else {
            for (int i = 0; i < key.length; i++) {
                if (!TextUtils.isEmpty(key[i])) {
                    if (value[i] == null) {
                        value[i] = "";
                    }
                    if (i == 0) {
                        mParamString.append("?").append(key[i]).append("=").append(value[i]);
                    } else {
                        mParamString.append("&").append(key[i]).append("=").append(value[i]);
                    }

                }
                LogUtils.e("参数" + "key=  " + key[i] + "\t\t\tvalue=  " + value[i]);

            }
        }
        return mParamString.toString();

    }

    private RequestBody mRequestBody(String[] key, String[] value) {
        RequestBody requestBody;
        FormBody.Builder builder = new FormBody.Builder();
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                if (!TextUtils.isEmpty(key[i])) {
                    if (TextUtils.isEmpty(value[i])) {
                        value[i] = "";
                    }
                    builder.add(key[i], value[i]);
                    LogUtils.e("参数" + "key=  " + key[i] + "\t\t\tvalue=  " + value[i]);
                }
            }
        }

        requestBody = builder.build();

        return requestBody;
    }

    private RequestBody createCustomRequestBody(final MediaType contentType, final File file) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) {
                Source source;
                long len;//记录本次读了多少数据
                long currSize = 0;//记录目前一共读了多少数据
                long totalSize = contentLength();//一共有多少数据
                int Percentile = (int) (contentLength() / 100);
                try {
                    source = Okio.source(file);
                    Buffer buffer = new Buffer();
                    while ((len = source.read(buffer, 100)) != -1) {
                        sink.write(buffer, len);
                        sink.flush();
                        currSize += len;
                        if (oldPercentage != (currSize / Percentile)) {
                            if (currSize == totalSize) {
                                UserPercentage = 100;
                                handler.sendEmptyMessage(UP_FILE);
                            } else if (currSize != 0 && StringUtils.isInteger(currSize / Percentile + "")) {
                                UserPercentage = (currSize / Percentile);
                                handler.sendEmptyMessage(UP_FILE);
                            }
                            oldPercentage = (currSize / Percentile);
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(error);
                }


            }
        };
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception ignored) {
        }

        return ssfFactory;
    }

    private static class X509TrustManagers implements X509TrustManager {

        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @SuppressLint("TrustAllX509TrustManager")
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class HostnameVerifiers implements HostnameVerifier {
        @SuppressLint("BadHostnameVerifier")
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}



