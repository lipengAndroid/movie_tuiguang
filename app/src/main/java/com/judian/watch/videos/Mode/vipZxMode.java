package com.judian.watch.videos.Mode;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 李鹏 2017/12/7 0007.
 */

public class vipZxMode extends PublicMode {


    /**
     * viptime : 6
     * unionid : oLGrow4PNl-scg-kFvcDh4LMJrco
     * data : [{"div":0,"title":"好友邀请码","content":"填写好友邀请码即可增加一个月vip！","tag":0},{"div":1,"title":"使用兑换码","content":"请从高佣联盟后台获取兑换码！","tag":1}]
     */

    private int viptime;
    private String unionid;
    private List<DataBean> data;

    public int getViptime() {
        return viptime;
    }

    public void setViptime(int viptime) {
        this.viptime = viptime;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * div : 0
         * title : 好友邀请码
         * content : 填写好友邀请码即可增加一个月vip！
         * tag : 0
         */

        private int div;
        private String title;
        private String content;
        private int tag;

        public int getDiv() {
            return div;
        }

        public void setDiv(int div) {
            this.div = div;
        }

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

        public int getTag() {
            return tag;
        }

        public void setTag(int tag) {
            this.tag = tag;
        }
    }
}
