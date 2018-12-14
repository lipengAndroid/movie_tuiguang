package com.judian.watch.videos.Mode;

/**
 * Created by 李鹏 2017/12/13 0013.
 */

public class WxNumAndQrImgMode extends PublicMode {

    /**
     * wxaccount : test
     * qrcode : http://judian.s1.natapp.cc/movie/public/problem/qrcode/20171211/512c3055d900359e2f32a97c608d6b77.png
     * url :
     */

    private String wxaccount;
    private String qrcode;
    private String url;

    public String getWxaccount() {
        return wxaccount;
    }

    public void setWxaccount(String wxaccount) {
        this.wxaccount = wxaccount;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
