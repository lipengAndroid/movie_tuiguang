package com.judian.watch.videos.Mode;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 李鹏 2017/12/12 0012.
 */

public class TopicDetailsMode extends PublicMode {

    /**
     * banner : 专题图片
     * content : 专题简介
     * list : [{"vod_id":"电影ID","vod_name":"电影名称","vod_pic":"电影图片","vod_gold":"电影评分"}]
     */

    private String banner;
    private String content,domain;
    private List<ListBean> list;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * vod_id : 电影ID
         * vod_name : 电影名称
         * vod_pic : 电影图片
         * vod_gold : 电影评分
         */

        private String vod_id;
        private String vod_name;
        private String vod_pic;
        private String vod_gold, vod_stars;

        public String getVod_stars() {
            return vod_stars;
        }

        public void setVod_stars(String vod_stars) {
            this.vod_stars = vod_stars;
        }

        public String getVod_id() {
            return vod_id;
        }

        public void setVod_id(String vod_id) {
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
    }
}
