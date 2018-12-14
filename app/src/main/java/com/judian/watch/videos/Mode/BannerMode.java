package com.judian.watch.videos.Mode;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 李鹏 2017/12/5 0005.
 */

public class BannerMode extends PublicMode {

    private String domain;
    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public static class ListBean implements Serializable {
        public String getVod_type() {
            return vod_type;
        }

        public void setVod_type(String vod_type) {
            this.vod_type = vod_type;
        }

        public String getVod_content() {
            return vod_content;
        }

        public void setVod_content(String vod_content) {
            this.vod_content = vod_content;
        }

        public String getVod_year() {
            return vod_year;
        }

        public void setVod_year(String vod_year) {
            this.vod_year = vod_year;
        }

        public String getVod_area() {
            return vod_area;
        }

        public void setVod_area(String vod_area) {
            this.vod_area = vod_area;
        }

        public String getVod_id() {
            return vod_id;
        }

        public void setVod_id(String vod_id) {
            this.vod_id = vod_id;
        }

        /**
         * slide_id : 7
         * slide_oid : 6
         * slide_cid : 3
         * slide_name : 电视
         * slide_logo : http://cdn.beichengo.com/Uploads/slide/2017-12-05/5a2651f6e2f89.png
         * slide_pic : http://cdn.beichengo.com/Uploads/slide/2017-12-05/5a2651fe4bf19.png
         * slide_url : www.baidu.com
         * slide_content : 电视
         * slide_status : 1
         */


        private int slide_id;
        private int slide_oid;
        private int slide_cid;
        private String slide_name, vod_type, vod_content, vod_year, vod_area,vod_id;
        private String slide_logo;
        private String slide_pic;
        private String slide_url;
        private String slide_content;
        private int slide_status;

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
    }
}
