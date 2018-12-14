package com.judian.watch.videos.Interface;

/**
 * Created by 10237 on 2016/11/11.
 */

public interface MyHttpCallBack {
    void ok(String jsonString, int httpTY);

    void error(String e, int uriType);

    void downloadUpProgress(long Percentile, int httpTY);
}
