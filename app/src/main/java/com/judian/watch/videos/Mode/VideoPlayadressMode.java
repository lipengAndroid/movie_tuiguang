package com.judian.watch.videos.Mode;

import java.io.Serializable;
import java.util.List;

public class VideoPlayadressMode extends PublicMode {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 1
         * name : 腾讯1
         * http : qq.com
         * createtime : 2018-04-20 09:57:45
         */

        private int id;
        private int typestatus;
        private String name;
        private String http;
        private String createtime;

        public int getTypestatus() {
            return typestatus;
        }

        public void setTypestatus(int typestatus) {
            this.typestatus = typestatus;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHttp() {
            return http;
        }

        public void setHttp(String http) {
            this.http = http;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }
}
