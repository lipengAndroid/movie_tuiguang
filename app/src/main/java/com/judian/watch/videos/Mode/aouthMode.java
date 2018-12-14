package com.judian.watch.videos.Mode;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/21 0021.
 */

public class aouthMode implements Serializable {


    /**
     * access_token : sfCxmDAanhibaa6vEQNEm8n-Pyb3cVCx7AjvdKbLHr7Cbja2dYGt2r7rVNu13SIHsgy8v7wS_E9gSVLqYDbtUG3Qef3Y8jkbUWgprTAu51U
     * expires_in : 7200
     * refresh_token : wzZb-NdCIdDdjRA1lE6zf37gyUFiGnEu-Q7AM0Cvbsb7MLdULS0G6JzArozGoRRC-sfgdIJL5umOi4cJLH56E5_i-iBRoFPRshF_p3rdEAQ
     * openid : o6vyL0-XzSUJq9kkFrRXRi6EEWNw
     * scope : snsapi_userinfo
     * unionid : oLGrow1uKOBJ1lehZMMKzYwTiwQk
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
