package com.judian.watch.videos.Mode;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 李鹏 2017/12/12 0012.
 */

public class PosterMode extends PublicMode {

    /**
     * id : 5
     * qrcode : http://judian.s1.natapp.cc/movie/public/problem/qrcode/20171212/70e4c37eee49cff7e7f3e18dd9efd352.png
     * wxaccount : 111
     * list : [{"id":4,"posterurl":"http://judian.s1.natapp.cc/movie/public/problem/poster/20171211/667ce842fe7237bedbaead1c93c32a4a.JPG","createtime":"2017-12-11 16:26:20","content":"海报样式1","status":0,"miaoshu":"Hi~,加我微信：111,免费领取VIP兑换码，即可免费观看全网VIP付费视频~快来领取吧。"},{"id":5,"posterurl":"http://judian.s1.natapp.cc/movie/public/problem/poster/20171211/725f58b649b37c1259cff9beb517c043.JPG","createtime":"2017-12-11 16:26:27","content":"海报样式2","status":0,"miaoshu":"Hi~,加我微信：111,免费领取VIP兑换码，即可免费观看全网VIP付费视频~快来领取吧。"},{"id":6,"posterurl":"http://judian.s1.natapp.cc/movie/public/problem/poster/20171211/eb18b9292e9d11f87e06e218e59c31fb.JPG","createtime":"2017-12-11 16:26:35","content":"海报样式3","status":0,"miaoshu":"Hi~,加我微信：111,免费领取VIP兑换码，即可免费观看全网VIP付费视频~快来领取吧。"}]
     */

    private int id;
    private String qrcode;
    private String wxaccount;
    private List<ListBean> list;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getWxaccount() {
        return wxaccount;
    }

    public void setWxaccount(String wxaccount) {
        this.wxaccount = wxaccount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * id : 4
         * posterurl : http://judian.s1.natapp.cc/movie/public/problem/poster/20171211/667ce842fe7237bedbaead1c93c32a4a.JPG
         * createtime : 2017-12-11 16:26:20
         * content : 海报样式1
         * status : 0
         * miaoshu : Hi~,加我微信：111,免费领取VIP兑换码，即可免费观看全网VIP付费视频~快来领取吧。
         */

        private int id;
        private String posterurl;
        private String createtime;
        private String content;
        private int status;
        private String miaoshu;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPosterurl() {
            return posterurl;
        }

        public void setPosterurl(String posterurl) {
            this.posterurl = posterurl;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMiaoshu() {
            return miaoshu;
        }

        public void setMiaoshu(String miaoshu) {
            this.miaoshu = miaoshu;
        }
    }
}
