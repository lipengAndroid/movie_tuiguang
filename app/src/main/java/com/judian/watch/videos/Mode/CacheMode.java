package com.judian.watch.videos.Mode;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class CacheMode implements Serializable {
    String time;
    String jsonString;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }
}
