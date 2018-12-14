package com.judian.watch.videos.Mode;

import java.io.Serializable;

/**
 * Created by 李鹏 2017/12/4 0004.
 */

public class PublicMode implements Serializable {

    /**
     * code : 2
     * msg : 用户已存在！
     */

    private int type;
    private String msg;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
