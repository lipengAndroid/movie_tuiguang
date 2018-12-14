package com.judian.watch.videos.Mode;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 李鹏 2017/11/29 0029.
 */

public class homeListMode extends PublicMode {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * title : 就因为去趟阿拉善英雄会，回来就买普拉多？
         * content :
         * article_id : 5522
         * cover_picture : http://downcdn.bcws.cn/newsimg/830204-246905b76a3a84b6747e22bafc655564
         * content_describe : 自从去过阿拉善英雄会，我回到家后便暗暗下决心一定要买辆硬派越野车。想过去天津买中东版的霸道，但是天津港买车水太深。最后还是在本地买辆二手的吧，没买这辆普拉多2700前看过一辆2010年的4000顶配。让懂车的朋友看了下，车出过事故，而且是翻过车的。最后还是算了吧！
         * display_time : 2017-11-29 10:44:36
         * mode : 2
         * cover_picture2 : http://downcdn.bcws.cn/newsimg/830204-3d134c9af9cf04dabf0f14ff3c2d3a13
         * cover_picture3 : http://downcdn.bcws.cn/newsimg/830204-37694eeb4b3bcf4c0a14444e56cb5aea
         * url : https://www.judianweitao.net/index/home/article?article_id=5522
         */

        private String title;
        private String content;
        private int article_id;
        private String cover_picture;
        private String content_describe;
        private String display_time;
        private int mode;
        private String cover_picture2;
        private String cover_picture3;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getArticle_id() {
            return article_id;
        }

        public void setArticle_id(int article_id) {
            this.article_id = article_id;
        }

        public String getCover_picture() {
            return cover_picture;
        }

        public void setCover_picture(String cover_picture) {
            this.cover_picture = cover_picture;
        }

        public String getContent_describe() {
            return content_describe;
        }

        public void setContent_describe(String content_describe) {
            this.content_describe = content_describe;
        }

        public String getDisplay_time() {
            return display_time;
        }

        public void setDisplay_time(String display_time) {
            this.display_time = display_time;
        }

        public int getMode() {
            return mode;
        }

        public void setMode(int mode) {
            this.mode = mode;
        }

        public String getCover_picture2() {
            return cover_picture2;
        }

        public void setCover_picture2(String cover_picture2) {
            this.cover_picture2 = cover_picture2;
        }

        public String getCover_picture3() {
            return cover_picture3;
        }

        public void setCover_picture3(String cover_picture3) {
            this.cover_picture3 = cover_picture3;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
