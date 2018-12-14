package com.judian.watch.videos.Mode;

import java.util.List;

/**
 * Created by 李鹏 2018/1/4 0004.
 */

public class HotVideoMode extends PublicMode {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * slide_id : 2
         * slide_oid : 2
         * slide_cid : 10
         * slide_name : 琅铘榜之风起长林
         * slide_logo : http://cdn.beichengo.com/Uploads/slide/2018-01-09/5a541c35f3ee5.jpg
         * slide_pic : http://cdn.beichengo.com/Uploads/slide/2018-01-09/5a541c368eb26.jpg
         * slide_url : https://vod.beichengo.com/index.php/vod-read-id-155680.html
         * slide_content : 琅铘榜之风起长林
         * slide_status : 1
         * vod_type : 古装,青春
         * vod_id : 155680
         * vod_stars : 3
         */

        private int slide_id;
        private int slide_oid;
        private int slide_cid;
        private String slide_name;
        private String slide_logo;
        private String slide_pic;
        private String slide_url;
        private String slide_content;
        private int slide_status;
        private String vod_type;
        private int vod_id;
        private int vod_stars;

        public int getSlide_id() {
            return slide_id;
        }

        public void setSlide_id(int slide_id) {
            this.slide_id = slide_id;
        }

        public int getSlide_oid() {
            return slide_oid;
        }

        public void setSlide_oid(int slide_oid) {
            this.slide_oid = slide_oid;
        }

        public int getSlide_cid() {
            return slide_cid;
        }

        public void setSlide_cid(int slide_cid) {
            this.slide_cid = slide_cid;
        }

        public String getSlide_name() {
            return slide_name;
        }

        public void setSlide_name(String slide_name) {
            this.slide_name = slide_name;
        }

        public String getSlide_logo() {
            return slide_logo;
        }

        public void setSlide_logo(String slide_logo) {
            this.slide_logo = slide_logo;
        }

        public String getSlide_pic() {
            return slide_pic;
        }

        public void setSlide_pic(String slide_pic) {
            this.slide_pic = slide_pic;
        }

        public String getSlide_url() {
            return slide_url;
        }

        public void setSlide_url(String slide_url) {
            this.slide_url = slide_url;
        }

        public String getSlide_content() {
            return slide_content;
        }

        public void setSlide_content(String slide_content) {
            this.slide_content = slide_content;
        }

        public int getSlide_status() {
            return slide_status;
        }

        public void setSlide_status(int slide_status) {
            this.slide_status = slide_status;
        }

        public String getVod_type() {
            return vod_type;
        }

        public void setVod_type(String vod_type) {
            this.vod_type = vod_type;
        }

        public int getVod_id() {
            return vod_id;
        }

        public void setVod_id(int vod_id) {
            this.vod_id = vod_id;
        }

        public int getVod_stars() {
            return vod_stars;
        }

        public void setVod_stars(int vod_stars) {
            this.vod_stars = vod_stars;
        }
    }
}
