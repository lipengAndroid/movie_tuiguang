package com.judian.watch.videos.Mode;

import java.io.Serializable;

/**
 * Created by 李鹏 2017/12/18 0018.
 */

public class TuiJianInfo implements Serializable{
    /**
     * vod_id : 19805
     * vod_name : 镜片超人兄弟
     * vod_pic : http://cdn.beichengo.com/Uploads/vod/2017-12-05/5a2659c277a4b.jpg
     * vod_gold : 9.0
     */

    private int vod_id;
    private String vod_name;
    private String vod_pic;
    private String vod_stars;
    /**
     * vod_gold : 9.9
     * vod_stars : 1
     * vod_area : 泰国
     * vod_year : 2017
     * vod_type : 剧情
     */

    private String vod_gold;
    private String vod_area;
    private String vod_year;
    private String vod_type;

    public String getVod_stars() {
        return vod_stars;
    }

    public void setVod_stars(String vod_stars) {
        this.vod_stars = vod_stars;
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

    public String getVod_gold() {
        return vod_gold;
    }

    public void setVod_gold(String vod_gold) {
        this.vod_gold = vod_gold;
    }


    public String getVod_area() {
        return vod_area;
    }

    public void setVod_area(String vod_area) {
        this.vod_area = vod_area;
    }

    public String getVod_year() {
        return vod_year;
    }

    public void setVod_year(String vod_year) {
        this.vod_year = vod_year;
    }

    public String getVod_type() {
        return vod_type;
    }

    public void setVod_type(String vod_type) {
        this.vod_type = vod_type;
    }
}
