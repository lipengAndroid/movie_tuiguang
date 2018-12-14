package com.judian.watch.videos.Mode;

import java.util.List;

/**
 * Created by 李鹏 2018/1/9 0009.
 */

public class SearchList {

    /**
     * type : 1
     * list : [{"vod_id":19258,"vod_name":"综艺哈哈秀[2017]"},{"vod_id":19266,"vod_name":"猪猪侠之超星萌宠第13季"},{"vod_id":19269,"vod_name":"选择[2017]"},{"vod_id":19270,"vod_name":"爸爸带娃记（2017）"},{"vod_id":19279,"vod_name":"世界新奇玩具试体验[2017]"},{"vod_id":19285,"vod_name":"军榜[2017]"},{"vod_id":19287,"vod_name":"老梁体育评书[2017]"},{"vod_id":19290,"vod_name":"我是大美人[2017]"},{"vod_id":19292,"vod_name":"papi酱（2017）"},{"vod_id":19293,"vod_name":"青春旅社（2017）"}]
     * msg : ok
     */

    private int type;
    private String msg;
    private List<ListBean> list;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * vod_id : 19258
         * vod_name : 综艺哈哈秀[2017]
         */

        private int vod_id;
        private String vod_name;

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
    }
}
