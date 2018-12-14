package com.judian.watch.videos.Mode;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 李鹏 2017/12/5 0005.
 */

public class shaixuanMode extends PublicMode {


    /**
     * list : {"type":"喜剧,爱情,恐怖,动作,科幻,剧情,战争,警匪,犯罪,动画,奇幻,武侠,冒险,枪战,恐怖,悬疑,惊悚,经典,青春,文艺,微电影,古装,历史,运动,农村,儿童,网络电影","area":"内地,美国,香港,台湾,韩国,日本,法国,英国,德国,泰国,印度,欧洲,东南亚,其他","year":"2017,2016,2015,2014,2013,2012,2011,2010,2009,2008,2007,2006,2005,2004,2003,2002,2001,2000","star":"王宝强,黄渤,周迅,周冬雨,范冰冰,陈学冬,陈伟霆,郭采洁,邓超,成龙,葛优,林正英,张家辉,梁朝伟,徐峥,郑恺,吴彦祖,刘德华,周星驰,林青霞,周润发,李连杰,甄子丹,古天乐,洪金宝,姚晨,倪妮,黄晓明,彭于晏,汤唯,陈小春","state":"正片,预告片,花絮","language":"国语,英语,粤语,闽南语,韩语,日语,其它","version":"高清版,剧场版,抢先版,OVA,TV,影院版"}
     */

    private ListBean list;

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * type : 喜剧,爱情,恐怖,动作,科幻,剧情,战争,警匪,犯罪,动画,奇幻,武侠,冒险,枪战,恐怖,悬疑,惊悚,经典,青春,文艺,微电影,古装,历史,运动,农村,儿童,网络电影
         * area : 内地,美国,香港,台湾,韩国,日本,法国,英国,德国,泰国,印度,欧洲,东南亚,其他
         * year : 2017,2016,2015,2014,2013,2012,2011,2010,2009,2008,2007,2006,2005,2004,2003,2002,2001,2000
         * star : 王宝强,黄渤,周迅,周冬雨,范冰冰,陈学冬,陈伟霆,郭采洁,邓超,成龙,葛优,林正英,张家辉,梁朝伟,徐峥,郑恺,吴彦祖,刘德华,周星驰,林青霞,周润发,李连杰,甄子丹,古天乐,洪金宝,姚晨,倪妮,黄晓明,彭于晏,汤唯,陈小春
         * state : 正片,预告片,花絮
         * language : 国语,英语,粤语,闽南语,韩语,日语,其它
         * version : 高清版,剧场版,抢先版,OVA,TV,影院版
         */

        @SerializedName("type")
        private String typeX;
        private String area;
        private String year;
        private String star;
        private String state;
        private String language;
        private String version;

        public String getTypeX() {
            return typeX;
        }

        public void setTypeX(String typeX) {
            this.typeX = typeX;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
