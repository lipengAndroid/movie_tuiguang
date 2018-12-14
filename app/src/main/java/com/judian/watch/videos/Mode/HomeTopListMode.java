package com.judian.watch.videos.Mode;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 李鹏 2017/12/18 0018.
 */

public class HomeTopListMode extends PublicMode {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * id : 1
         * title : 优酷
         * imageurl : http://www.judianweitao.net/problem/vipzone/20171218/d35846f56a87655e0e0dc785993e0bd3.png
         * url : http://tv.youku.com
         * status : 1
         * createtime : 2017-12-17 15:36:21
         */

        private int id;
        private String title;
        private String imageurl;
        private String url;
        private int status;
        private String createtime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }
}
