package com.judian.watch.videos.Mode;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 李鹏 2017/12/12 0012.
 */

public class TopicMode extends PublicMode {


    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * special_id : 专题ID
         * special_name : 专题名称
         * special_banner : 专题图片
         */

        private String special_id;
        private String special_name;
        private String special_banner;

        public String getSpecial_id() {
            return special_id;
        }

        public void setSpecial_id(String special_id) {
            this.special_id = special_id;
        }

        public String getSpecial_name() {
            return special_name;
        }

        public void setSpecial_name(String special_name) {
            this.special_name = special_name;
        }

        public String getSpecial_banner() {
            return special_banner;
        }

        public void setSpecial_banner(String special_banner) {
            this.special_banner = special_banner;
        }
    }
}
