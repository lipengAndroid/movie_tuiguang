package com.judian.watch.videos.Mode;

/**
 * Created by 李鹏 2017/12/30 0030.
 */

public class GoodsAdInfo {


    /**
     * result : {"adurl":"http://www.sohu.com","adimg":"http://api.judianweitao.net/upload/playad/20180421/f0bb7e08539eccca2f77f9719348f2a2.png","status":1}
     * type : 1
     * msg :
     */

    private ResultBean result;
    private int type;
    private String msg;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

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

    public static class ResultBean {
        /**
         * adurl : http://www.sohu.com
         * adimg : http://api.judianweitao.net/upload/playad/20180421/f0bb7e08539eccca2f77f9719348f2a2.png
         * status : 1
         */

        private String adurl;
        private String adimg;
        private int status;

        public String getAdurl() {
            return adurl;
        }

        public void setAdurl(String adurl) {
            this.adurl = adurl;
        }

        public String getAdimg() {
            return adimg;
        }

        public void setAdimg(String adimg) {
            this.adimg = adimg;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
