package com.judian.watch.videos.Mode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李鹏 2017/12/5 0005.
 */

public class ClassVideoMode extends PublicMode {

    private String domain;
    private List<ListBean> list;

    public List<ListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        } else {
            return list;
        }
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * vod_addtime : 1514522316
         * vod_area : 美国
         * vod_year : 2014
         * vod_type : 动作,剧情
         */

        private int vod_addtime;
        private String vod_area;
        private int vod_year;
        private String vod_type;

        public int getVod_stars() {
            return vod_stars;
        }

        public void setVod_stars(int vod_stars) {
            this.vod_stars = vod_stars;
        }

        /**
         * vod_cid : 3
         * vod_id : 19174
         * vod_name : 金光御九界之鬼途奇行录
         * vod_pic : http://cdn.beichengo.com/Uploads/vod/2017-12-01/5a212eb61efd9.jpg
         * vod_gold : 8.0
         * vod_isend : 0
         * vod_total : 0
         * vod_continu : 全2集
         */

        private int vod_stars;
        private int vod_cid;
        private int vod_id;
        private String vod_name;
        private String vod_pic;
        private String vod_gold;
        private int vod_isend;
        private int vod_total;
        private String vod_continu;

        public int getVod_cid() {
            return vod_cid;
        }

        public void setVod_cid(int vod_cid) {
            this.vod_cid = vod_cid;
        }

        public int getVod_id() {
            return vod_id;
        }

        public void setVod_id(int vod_id) {
            this.vod_id = vod_id;
        }

        public String getVod_name() {
            return vod_name;
        }

        public void setVod_name(String vod_name) {
            this.vod_name = vod_name;
        }

        public String getVod_pic() {
            return vod_pic;
        }

        public void setVod_pic(String vod_pic) {
            this.vod_pic = vod_pic;
        }

        public String getVod_gold() {
            return vod_gold;
        }

        public void setVod_gold(String vod_gold) {
            this.vod_gold = vod_gold;
        }

        public int getVod_isend() {
            return vod_isend;
        }

        public void setVod_isend(int vod_isend) {
            this.vod_isend = vod_isend;
        }

        public int getVod_total() {
            return vod_total;
        }

        public void setVod_total(int vod_total) {
            this.vod_total = vod_total;
        }

        public String getVod_continu() {
            return vod_continu;
        }

        public void setVod_continu(String vod_continu) {
            this.vod_continu = vod_continu;
        }

        public int getVod_addtime() {
            return vod_addtime;
        }

        public void setVod_addtime(int vod_addtime) {
            this.vod_addtime = vod_addtime;
        }

        public String getVod_area() {
            return vod_area;
        }

        public void setVod_area(String vod_area) {
            this.vod_area = vod_area;
        }

        public int getVod_year() {
            return vod_year;
        }

        public void setVod_year(int vod_year) {
            this.vod_year = vod_year;
        }

        public String getVod_type() {
            return vod_type;
        }

        public void setVod_type(String vod_type) {
            this.vod_type = vod_type;
        }
    }
}
