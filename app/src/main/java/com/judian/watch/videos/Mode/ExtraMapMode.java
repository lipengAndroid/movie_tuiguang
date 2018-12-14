package com.judian.watch.videos.Mode;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/29 0029.
 */

public class ExtraMapMode implements Serializable {


    /**
     * _ALIYUN_NOTIFICATION_ID_ : 370735
     * id : 2006
     * type : 2
     * url :
     */

    private String _ALIYUN_NOTIFICATION_ID_;
    private String id;
    private int type;
    private String url;

    public String get_ALIYUN_NOTIFICATION_ID_() {
        return _ALIYUN_NOTIFICATION_ID_;
    }

    public void set_ALIYUN_NOTIFICATION_ID_(String _ALIYUN_NOTIFICATION_ID_) {
        this._ALIYUN_NOTIFICATION_ID_ = _ALIYUN_NOTIFICATION_ID_;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
