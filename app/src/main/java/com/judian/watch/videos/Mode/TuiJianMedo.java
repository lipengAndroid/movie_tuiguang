package com.judian.watch.videos.Mode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李鹏 2017/12/6 0006.
 */

public class TuiJianMedo extends PublicMode {


    /**
     * domain : https://vod.beichengo.com/index.php/vod-read-id-
     * list : {"dy":[{"vod_id":19162,"vod_name":"蝶影惊魂","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-01/5a212d384a1ee.jpg","vod_gold":"9.0","vod_stars":1},{"vod_id":19164,"vod_name":"24小时：末路重生","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-01/5a212d3927031.jpg","vod_gold":"9.0","vod_stars":1},{"vod_id":19165,"vod_name":"盖尔王","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-01/5a212d398709f.jpg","vod_gold":"9.0","vod_stars":5},{"vod_id":19166,"vod_name":"复仇之火","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-01/5a212d39daf1c.jpg","vod_gold":"9.0","vod_stars":1},{"vod_id":19179,"vod_name":"暗金丑岛君电影版4","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-01/5a2143fac70ac.jpg","vod_gold":"9.0","vod_stars":1},{"vod_id":19185,"vod_name":"不败雄心","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-01/5a21440ac4215.jpg","vod_gold":"9.0","vod_stars":1}],"ds":[{"vod_id":19914,"vod_name":"白鹿原","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a265a0a80bdf.jpg","vod_continu":"全77集","vod_isend":0,"vod_total":0,"vod_stars":5,"vod_gold":"9.3"},{"vod_id":19177,"vod_name":"猎场 DVD版","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-01/5a213a5186a40.jpg","vod_continu":"58集全","vod_isend":1,"vod_total":58,"vod_stars":5,"vod_gold":"9.0"},{"vod_id":19337,"vod_name":"最后的战士","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a265742b519e.jpg","vod_continu":"全44集","vod_isend":0,"vod_total":0,"vod_stars":1,"vod_gold":"9.0"},{"vod_id":19505,"vod_name":"飞哥大英雄2之飞哥战队","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a265891c23eb.jpg","vod_continu":"全50集","vod_isend":0,"vod_total":0,"vod_stars":1,"vod_gold":"9.0"},{"vod_id":19510,"vod_name":"幸福来了","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a2658939090f.jpg","vod_continu":"全110集","vod_isend":0,"vod_total":0,"vod_stars":1,"vod_gold":"9.0"},{"vod_id":19513,"vod_name":"训长吴纯南","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a265895abd9b.jpg","vod_continu":"全67集","vod_isend":0,"vod_total":0,"vod_stars":1,"vod_gold":"9.0"}],"dm":[{"vod_id":19388,"vod_name":"巴巴爸爸","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a2657625a536.jpg","vod_continu":"更新至45集","vod_isend":0,"vod_total":46,"vod_stars":1,"vod_gold":"9.0"},{"vod_id":19487,"vod_name":"顽皮宝宝慢速启蒙儿歌","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a26579e0d119.jpg","vod_continu":"64集全","vod_isend":1,"vod_total":64,"vod_stars":1,"vod_gold":"9.0"},{"vod_id":19488,"vod_name":"西游记","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a26579e5f5c7.jpg","vod_continu":"全48集","vod_isend":0,"vod_total":0,"vod_stars":1,"vod_gold":"9.0"},{"vod_id":19507,"vod_name":"战神金刚：传奇的保护神第四季","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a26589297cc4.jpg","vod_continu":"全5集","vod_isend":0,"vod_total":0,"vod_stars":1,"vod_gold":"9.0"},{"vod_id":19518,"vod_name":"星球大战：义军崛起第四季","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a26589851483.jpg","vod_continu":"全9集","vod_isend":0,"vod_total":0,"vod_stars":1,"vod_gold":"9.0"},{"vod_id":19524,"vod_name":"辛普森一家第二十九季","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a26589d56cab.jpg","vod_continu":"全6集","vod_isend":0,"vod_total":0,"vod_stars":1,"vod_gold":"9.0"}],"zy":[{"vod_id":19221,"vod_name":"FNS新番对抗全明星秋季盛典 眼力王决战 Part IV","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-01/5a214ea39b9f8.jpg","vod_continu":"0期","vod_addtime":1508125198,"vod_stars":1,"vod_gold":"9.0"},{"vod_id":19268,"vod_name":"大风车","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a26571bc96e9.jpg","vod_continu":1513248812,"vod_addtime":1513248812,"vod_stars":1,"vod_gold":"9.0"},{"vod_id":19285,"vod_name":"军榜","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a265727a547a.jpg","vod_continu":1513248513,"vod_addtime":1513248513,"vod_stars":1,"vod_gold":"9.0"},{"vod_id":19296,"vod_name":"股市正能量","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a26572c38612.jpg","vod_continu":1513357579,"vod_addtime":1513357579,"vod_stars":1,"vod_gold":"9.0"},{"vod_id":19308,"vod_name":"成长大家谈","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a2657322380c.jpg","vod_continu":1513248675,"vod_addtime":1513248675,"vod_stars":1,"vod_gold":"9.0"},{"vod_id":19310,"vod_name":"体育新闻 五星体育版","vod_pic":"http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a265732ebe1a.jpg","vod_continu":1513248670,"vod_addtime":1513248670,"vod_stars":1,"vod_gold":"9.0"}]}
     */

    private String domain;
    private ListBean list;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<List<TuiJianInfo>> getList() {
        List<List<TuiJianInfo>> listBean = new ArrayList<>();
        List<TuiJianInfo> dylist = new ArrayList<>();
        try {
            for (int i = 0; i < list.getDy().size(); i++) {
                TuiJianInfo tuiJianInfo = new TuiJianInfo();
                tuiJianInfo.setVod_id(list.getDy().get(i).getVod_id());
                tuiJianInfo.setVod_name(list.getDy().get(i).getVod_name());
                tuiJianInfo.setVod_pic(list.getDy().get(i).getVod_pic());
                tuiJianInfo.setVod_stars(list.getDy().get(i).getVod_stars() + "");

                tuiJianInfo.setVod_area(list.getDy().get(i).getVod_area() + "");
                tuiJianInfo.setVod_type(list.getDy().get(i).getVod_type() + "");
                tuiJianInfo.setVod_year(list.getDy().get(i).getVod_year() + "");
                dylist.add(tuiJianInfo);
            }
        } catch (Exception ignored) {
        }

        List<TuiJianInfo> dslist = new ArrayList<>();

        try {
            for (int i = 0; i < list.getDs().size(); i++) {
                TuiJianInfo tuiJianInfo = new TuiJianInfo();
                tuiJianInfo.setVod_id(list.getDs().get(i).getVod_id());
                tuiJianInfo.setVod_name(list.getDs().get(i).getVod_name());
                tuiJianInfo.setVod_pic(list.getDs().get(i).getVod_pic());
                tuiJianInfo.setVod_stars(list.getDs().get(i).getVod_stars() + "");
                tuiJianInfo.setVod_area(list.getDs().get(i).getVod_area() + "");
                tuiJianInfo.setVod_type(list.getDs().get(i).getVod_type() + "");
                tuiJianInfo.setVod_year(list.getDs().get(i).getVod_year() + "");
                dslist.add(tuiJianInfo);
            }
        } catch (Exception ignored) {
        }

        List<TuiJianInfo> dmlist = new ArrayList<>();

        try {
            for (int i = 0; i < list.getDm().size(); i++) {
                TuiJianInfo tuiJianInfo = new TuiJianInfo();
                tuiJianInfo.setVod_id(list.getDm().get(i).getVod_id());
                tuiJianInfo.setVod_name(list.getDm().get(i).getVod_name());
                tuiJianInfo.setVod_pic(list.getDm().get(i).getVod_pic());
                tuiJianInfo.setVod_stars(list.getDm().get(i).getVod_stars() + "");
                tuiJianInfo.setVod_area(list.getDm().get(i).getVod_area() + "");
                tuiJianInfo.setVod_type(list.getDm().get(i).getVod_type() + "");
                tuiJianInfo.setVod_year(list.getDm().get(i).getVod_year() + "");

                dmlist.add(tuiJianInfo);
            }
        } catch (Exception ignored) {
        }
        List<TuiJianInfo> zylist = new ArrayList<>();
        try {

            for (int i = 0; i < list.getZy().size(); i++) {
                TuiJianInfo tuiJianInfo = new TuiJianInfo();
                tuiJianInfo.setVod_id(list.getZy().get(i).getVod_id());
                tuiJianInfo.setVod_name(list.getZy().get(i).getVod_name());
                tuiJianInfo.setVod_pic(list.getZy().get(i).getVod_pic());
                tuiJianInfo.setVod_stars(list.getZy().get(i).getVod_stars() + "");
                tuiJianInfo.setVod_area(list.getZy().get(i).getVod_area() + "");
                tuiJianInfo.setVod_type(list.getZy().get(i).getVod_type() + "");
                tuiJianInfo.setVod_year(list.getZy().get(i).getVod_year() + "");
                zylist.add(tuiJianInfo);
            }
        } catch (Exception ignored) {
        }


        listBean.add(dylist);
        listBean.add(dslist);
        listBean.add(dmlist);
        listBean.add(zylist);

        return listBean;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        private List<DyBean> dy;
        private List<DsBean> ds;
        private List<DmBean> dm;
        private List<ZyBean> zy;

        public List<DyBean> getDy() {
            return dy;
        }

        public void setDy(List<DyBean> dy) {
            this.dy = dy;
        }

        public List<DsBean> getDs() {
            return ds;
        }

        public void setDs(List<DsBean> ds) {
            this.ds = ds;
        }

        public List<DmBean> getDm() {
            return dm;
        }

        public void setDm(List<DmBean> dm) {
            this.dm = dm;
        }

        public List<ZyBean> getZy() {
            return zy;
        }

        public void setZy(List<ZyBean> zy) {
            this.zy = zy;
        }

        public static class DyBean implements Serializable {
            /**
             * vod_id : 19162
             * vod_name : 蝶影惊魂
             * vod_pic : http://cdn.beichengo.com/Uploads/vod/2017-12-01/5a212d384a1ee.jpg
             * vod_gold : 9.0
             * vod_stars : 1
             */

            private int vod_id;
            private String vod_name;
            private String vod_pic;
            private String vod_gold;
            private int vod_stars;
            /**
             * vod_area : 泰国
             * vod_year : 2017
             * vod_type : 剧情
             */

            private String vod_area;
            private int vod_year;
            private String vod_type;

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

            public int getVod_stars() {
                return vod_stars;
            }

            public void setVod_stars(int vod_stars) {
                this.vod_stars = vod_stars;
            }

            public String getVod_area() {
                return vod_area;
            }

            public void setVod_area(String vod_area) {
                this.vod_area = vod_area;
            }

            public int getVod_year() {
                return vod_year;
            }

            public void setVod_year(int vod_year) {
                this.vod_year = vod_year;
            }

            public String getVod_type() {
                return vod_type;
            }

            public void setVod_type(String vod_type) {
                this.vod_type = vod_type;
            }
        }

        public static class DsBean implements Serializable {
            public String getVod_area() {
                return vod_area;
            }

            public void setVod_area(String vod_area) {
                this.vod_area = vod_area;
            }

            public int getVod_year() {
                return vod_year;
            }

            public void setVod_year(int vod_year) {
                this.vod_year = vod_year;
            }

            public String getVod_type() {
                return vod_type;
            }

            public void setVod_type(String vod_type) {
                this.vod_type = vod_type;
            }

            /**
             * vod_id : 19914
             * vod_name : 白鹿原
             * vod_pic : http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a265a0a80bdf.jpg
             * vod_continu : 全77集
             * vod_isend : 0
             * vod_total : 0
             * vod_stars : 5
             * vod_gold : 9.3
             */

            private int vod_id;
            private String vod_name;
            private String vod_pic;
            private String vod_continu;
            private int vod_isend;
            private int vod_total;
            private int vod_stars;
            private String vod_gold;
            private String vod_area;
            private int vod_year;
            private String vod_type;

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

            public String getVod_pic() {
                return vod_pic;
            }

            public void setVod_pic(String vod_pic) {
                this.vod_pic = vod_pic;
            }

            public String getVod_continu() {
                return vod_continu;
            }

            public void setVod_continu(String vod_continu) {
                this.vod_continu = vod_continu;
            }

            public int getVod_isend() {
                return vod_isend;
            }

            public void setVod_isend(int vod_isend) {
                this.vod_isend = vod_isend;
            }

            public int getVod_total() {
                return vod_total;
            }

            public void setVod_total(int vod_total) {
                this.vod_total = vod_total;
            }

            public int getVod_stars() {
                return vod_stars;
            }

            public void setVod_stars(int vod_stars) {
                this.vod_stars = vod_stars;
            }

            public String getVod_gold() {
                return vod_gold;
            }

            public void setVod_gold(String vod_gold) {
                this.vod_gold = vod_gold;
            }
        }

        public static class DmBean implements Serializable {
            /**
             * vod_id : 19388
             * vod_name : 巴巴爸爸
             * vod_pic : http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a2657625a536.jpg
             * vod_continu : 更新至45集
             * vod_isend : 0
             * vod_total : 46
             * vod_stars : 1
             * vod_gold : 9.0
             */

            private int vod_id;
            private String vod_name;
            private String vod_pic;
            private String vod_continu;
            private int vod_isend;
            private int vod_total;
            private int vod_stars;
            private String vod_gold;
            private String vod_area;
            private int vod_year;
            private String vod_type;

            public String getVod_area() {
                return vod_area;
            }

            public void setVod_area(String vod_area) {
                this.vod_area = vod_area;
            }

            public int getVod_year() {
                return vod_year;
            }

            public void setVod_year(int vod_year) {
                this.vod_year = vod_year;
            }

            public String getVod_type() {
                return vod_type;
            }

            public void setVod_type(String vod_type) {
                this.vod_type = vod_type;
            }

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

            public String getVod_pic() {
                return vod_pic;
            }

            public void setVod_pic(String vod_pic) {
                this.vod_pic = vod_pic;
            }

            public String getVod_continu() {
                return vod_continu;
            }

            public void setVod_continu(String vod_continu) {
                this.vod_continu = vod_continu;
            }

            public int getVod_isend() {
                return vod_isend;
            }

            public void setVod_isend(int vod_isend) {
                this.vod_isend = vod_isend;
            }

            public int getVod_total() {
                return vod_total;
            }

            public void setVod_total(int vod_total) {
                this.vod_total = vod_total;
            }

            public int getVod_stars() {
                return vod_stars;
            }

            public void setVod_stars(int vod_stars) {
                this.vod_stars = vod_stars;
            }

            public String getVod_gold() {
                return vod_gold;
            }

            public void setVod_gold(String vod_gold) {
                this.vod_gold = vod_gold;
            }
        }

        public static class ZyBean implements Serializable {
            /**
             * vod_id : 19221
             * vod_name : FNS新番对抗全明星秋季盛典 眼力王决战 Part IV
             * vod_pic : http://cdn.beichengo.com/Uploads/vod/2017-12-01/5a214ea39b9f8.jpg
             * vod_continu : 0期
             * vod_addtime : 1508125198
             * vod_stars : 1
             * vod_gold : 9.0
             */

            private int vod_id;
            private String vod_name;
            private String vod_pic;
            private String vod_continu;
            private int vod_addtime;
            private int vod_stars;
            private String vod_gold;
            private String vod_area;
            private int vod_year;
            private String vod_type;

            public String getVod_area() {
                return vod_area;
            }

            public void setVod_area(String vod_area) {
                this.vod_area = vod_area;
            }

            public int getVod_year() {
                return vod_year;
            }

            public void setVod_year(int vod_year) {
                this.vod_year = vod_year;
            }

            public String getVod_type() {
                return vod_type;
            }

            public void setVod_type(String vod_type) {
                this.vod_type = vod_type;
            }

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

            public String getVod_pic() {
                return vod_pic;
            }

            public void setVod_pic(String vod_pic) {
                this.vod_pic = vod_pic;
            }

            public String getVod_continu() {
                return vod_continu;
            }

            public void setVod_continu(String vod_continu) {
                this.vod_continu = vod_continu;
            }

            public int getVod_addtime() {
                return vod_addtime;
            }

            public void setVod_addtime(int vod_addtime) {
                this.vod_addtime = vod_addtime;
            }

            public int getVod_stars() {
                return vod_stars;
            }

            public void setVod_stars(int vod_stars) {
                this.vod_stars = vod_stars;
            }

            public String getVod_gold() {
                return vod_gold;
            }

            public void setVod_gold(String vod_gold) {
                this.vod_gold = vod_gold;
            }
        }
    }
}
