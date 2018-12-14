package com.judian.watch.videos.Mode;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/21 0021.
 */

public class WxUserInfoMode implements Serializable {


    /**
     * openid : o6vyL0-XzSUJq9kkFrRXRi6EEWNw
     * nickname : 李鹏
     * sex : 1
     * language : zh_CN
     * city :
     * province :
     * country : CN
     * headimgurl : http://wx.qlogo.cn/mmopen/vi_32/9dQtP7dtB4JibicavVhF5HcqJMWP1gjKX9jicTzs3Z1a75VZhvAvBTEZ0cYepU7QgXXVHoBZGZ5ZibZ9pb3FbiayX8A/0
     * privilege : []
     * unionid : oLGrow1uKOBJ1lehZMMKzYwTiwQk
     */

    private String openid;
    private String nickname;
    private int sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    /**
     * privilege : []
     * unionid : oLGrow54sDaA-j2kAXFvu25GF8Zo
     */

    private String unionid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }


    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

}
