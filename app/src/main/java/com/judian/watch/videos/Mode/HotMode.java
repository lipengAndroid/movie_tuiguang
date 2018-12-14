package com.judian.watch.videos.Mode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李鹏 2017/12/5 0005.
 */

public class HotMode extends PublicMode {


    private List<ListBean> list;

    public List<ListBean> getList() {
        if (list == null) {
            return new ArrayList<>();
        } else {
            return list;
        }
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * vod_name : 猎场 DVD版
         */

        private String vod_name;

        public String getVod_name() {
            return vod_name;
        }

        public void setVod_name(String vod_name) {
            this.vod_name = vod_name;
        }
    }
}
