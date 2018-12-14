package com.judian.watch.videos.Mode;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 李鹏 2017/12/4 0004.
 */

public class UserInfoMode extends PublicMode {

    /**
     * data : {"id":432,"nickname":"。","imageurl":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKprKJxEOIWfMQ9PE4AaEPzcon841YZXEg9wrMVp53OY84gwXEsyFH86T2JkAEEicDpE2icVxiaq8mfA/132","unionid":"oLGrow4PNl-scg-kFvcDh4LMJrco","leadid":"","createtime":"2018-04-19 16:55:19","yaoqingnumber":0,"type":1,"vip":"2018-04-26","haschangeread":0,"haschangeyao":0,"haschangephone":0,"uid":0,"pstatus":0,"cstatus":0,"sex":"男","password":"111111","phonenum":"15773638677","openid":"o-_yZ0Vxeal0PSdK3vkK4qQzLp00"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * id : 432
         * nickname : 。
         * imageurl : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKprKJxEOIWfMQ9PE4AaEPzcon841YZXEg9wrMVp53OY84gwXEsyFH86T2JkAEEicDpE2icVxiaq8mfA/132
         * unionid : oLGrow4PNl-scg-kFvcDh4LMJrco
         * leadid :
         * createtime : 2018-04-19 16:55:19
         * yaoqingnumber : 0
         * type : 1
         * vip : 2018-04-26
         * haschangeread : 0
         * haschangeyao : 0
         * haschangephone : 0
         * uid : 0
         * pstatus : 0
         * cstatus : 0
         * sex : 男
         * password : 111111
         * phonenum : 15773638677
         * openid : o-_yZ0Vxeal0PSdK3vkK4qQzLp00
         */

        private int id;
        private String nickname;
        private String imageurl;
        private String unionid;
        private String leadid;
        private String createtime;
        private int yaoqingnumber;
        @SerializedName("type")
        private int typeX;
        private String vip;
        private int haschangeread, guoqi;
        private int haschangeyao;
        private int haschangephone;
        private int uid;
        private int pstatus;
        private int cstatus;
        private String sex;
        private String password;
        private String phonenum;
        private String openid;

        public int getGuoqi() {
            return guoqi;
        }

        public void setGuoqi(int guoqi) {
            this.guoqi = guoqi;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getLeadid() {
            return leadid;
        }

        public void setLeadid(String leadid) {
            this.leadid = leadid;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getYaoqingnumber() {
            return yaoqingnumber;
        }

        public void setYaoqingnumber(int yaoqingnumber) {
            this.yaoqingnumber = yaoqingnumber;
        }

        public int getTypeX() {
            return typeX;
        }

        public void setTypeX(int typeX) {
            this.typeX = typeX;
        }

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }

        public int getHaschangeread() {
            return haschangeread;
        }

        public void setHaschangeread(int haschangeread) {
            this.haschangeread = haschangeread;
        }

        public int getHaschangeyao() {
            return haschangeyao;
        }

        public void setHaschangeyao(int haschangeyao) {
            this.haschangeyao = haschangeyao;
        }

        public int getHaschangephone() {
            return haschangephone;
        }

        public void setHaschangephone(int haschangephone) {
            this.haschangephone = haschangephone;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getPstatus() {
            return pstatus;
        }

        public void setPstatus(int pstatus) {
            this.pstatus = pstatus;
        }

        public int getCstatus() {
            return cstatus;
        }

        public void setCstatus(int cstatus) {
            this.cstatus = cstatus;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhonenum() {
            return phonenum;
        }

        public void setPhonenum(String phonenum) {
            this.phonenum = phonenum;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }
    }
}
