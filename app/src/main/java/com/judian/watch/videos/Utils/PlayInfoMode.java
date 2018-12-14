package com.judian.watch.videos.Utils;

import android.text.TextUtils;

import com.judian.watch.videos.Mode.PublicMode;

import java.io.Serializable;

/**
 * Created by 李鹏 2017/12/30 0030.
 */

public class PlayInfoMode extends PublicMode {

    /**
     * vod_url : 高清在线$https://v.xw0371.com/20171123/VBReI75L/index.m3u8$$$http://v.qq.com/x/cover/wi8e2p5kirdaf3j.html$$$http://www.iqiyi.com/v_19rre19on4.html$$$BD$http://cn2.okokyun.com/20171202/3ZXLARSU/index.m3u8
     * vod_play : 135m3u8$$$qq$$$iqiyi$$$ckm3u8
     * vod_content : 故事发生在非洲附近的大海上，主人公冷锋（吴京 饰）遭遇人生滑铁卢，被“开除军籍”，本想漂泊一生的他，正当他打算这么做的时候，一场突如其来的意外打破了他的计划，突然被卷入了一场非洲国家叛乱，本可以安全撤离，却因无法忘记曾经为军人的使命，孤身犯险冲回沦陷区，带领身陷屠杀中的同胞和难民，展开生死逃亡。随着斗争的持续，体内的狼性逐渐复苏，最终孤身闯入战乱区域，为同胞而战斗。
     */
    private PlayInfoModeInfo result;

    /**
     * result : {"vod_url":"TC720P高清$https://v2.xw0371.com/20180105/aXqcfLl2/index.m3u8","vod_play":"135m3u8","vod_content":"<p>一对好基友孟云（韩庚 饰）和余飞（郑恺 饰）跟女友都因为一点小事宣告分手，并且&ldquo;拒绝挽回，死不认错&rdquo;。两人在夜店、派对与交友软件上放飞人生第二春，大肆庆祝&ldquo;黄金单身期&rdquo;，从而引发了一系列好笑的故事。孟云与女友同甘共苦却难逃&ldquo;五年之痒&rdquo;，余飞与女友则棋逢敌手相爱相杀无绝期。然而现实的&ldquo;打脸&rdquo;却来得猝不及防：一对推拉纠结零往来，一对纠缠互怼全交代。两对恋人都将面对最终的选择：是再次相见？还是再也不见？<\/p>\r\n","vod_actor":"韩庚,郑恺,于文文,曾梦雪"}
     */

    public PlayInfoModeInfo getResult() {
        return result;
    }

    public void setResult(PlayInfoModeInfo result) {
        this.result = result;
    }


    public static class PlayInfoModeInfo implements Serializable {

        private String vod_url;
        private String vod_play;
        private String vod_content;
        private String vod_actor, vod_pic, vod_type, vod_area, vod_year;

        public String getVod_year() {
            return vod_year;
        }

        public void setVod_year(String vod_year) {
            this.vod_year = vod_year;
        }

        public String getVod_area() {
            return vod_area;
        }

        public void setVod_area(String vod_area) {
            this.vod_area = vod_area;
        }

        public String getVod_type() {
            if (TextUtils.isEmpty(vod_type)) {
                return "剧情";
            }
            return vod_type;
        }

        public void setVod_type(String vod_type) {
            this.vod_type = vod_type;
        }

        public String getVod_pic() {
            return vod_pic;
        }

        public void setVod_pic(String vod_pic) {
            this.vod_pic = vod_pic;
        }

        public String getVod_actor() {
            return vod_actor;
        }

        public void setVod_actor(String vod_actor) {
            this.vod_actor = vod_actor;
        }

        public String getVod_url() {
            return vod_url;
        }

        public void setVod_url(String vod_url) {
            this.vod_url = vod_url;
        }

        public String getVod_play() {
            return vod_play;
        }

        public void setVod_play(String vod_play) {
            this.vod_play = vod_play;
        }

        public String getVod_content() {
            String context = vod_content;
            context = context.replace("&ldquo", "");
            context = context.replace("&rdquo", "");
            context = context.replace("&mdash", "");
            context = context.replace("&middot", "");
            context = context.replace("&hellip", "");
            context = context.replace("&quot", "");
            context = context.replace("<p>", "");
            context = context.replace("</p>", "");
            context = context.replace(";", "");
            context = context.replace("；", "");
            context = context.replace("hellip", "");
            return context;
        }

        public void setVod_content(String vod_content) {
            this.vod_content = vod_content;
        }
    }

}
