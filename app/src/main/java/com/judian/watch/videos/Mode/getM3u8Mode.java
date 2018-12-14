package com.judian.watch.videos.Mode;

import java.util.List;

/**
 * Created by 李鹏 2018/1/2 0002.
 */

public class getM3u8Mode {


    /**
     * type : 1
     * url : [{"url":"http://27.221.15.179/6572CCB890E30829C3A94528C7/0300010E005A424E4440284451ED9C6B56296C-B562-4542-B291-E95E04459939.flv?ccode=0512&duration=195&expire=18000&psid=999bf30208b475b55cb35858fd89392a&ups_client_netip=2f6427ec&ups_ts=1514863485&ups_userid=&utid=fenREmfB8gwCAS9kJ%2BwCtnAC&vid=XMzI3MTMyMjE0NA%3D%3D&vkey=Acee485fdc32af88972a23e34fb55a7f3&showid=24efbfbdefbfbd3befbf","size":53474953,"seconds":195.2},{"url":"http://27.221.15.179/69764C9EA113482D5196FF3038/0300010E015A424E4440284451ED9C6B56296C-B562-4542-B291-E95E04459939.flv?ccode=0512&duration=197&expire=18000&psid=999bf30208b475b55cb35858fd89392a&ups_client_netip=2f6427ec&ups_ts=1514863485&ups_userid=&utid=fenREmfB8gwCAS9kJ%2BwCtnAC&vid=XMzI3MTMyMjE0NA%3D%3D&vkey=A7d670d7f1f5561dd14f193e02c53133f&showid=24efbfbdefbfbd3befbf","size":25582836,"seconds":197.559},{"url":"http://27.221.15.179/657433144923C8346D7272330A/0300010E025A424E4440284451ED9C6B56296C-B562-4542-B291-E95E04459939.flv?ccode=0512&duration=196&expire=18000&psid=999bf30208b475b55cb35858fd89392a&ups_client_netip=2f6427ec&ups_ts=1514863485&ups_userid=&utid=fenREmfB8gwCAS9kJ%2BwCtnAC&vid=XMzI3MTMyMjE0NA%3D%3D&vkey=Adf5c70ba8b809b50d32d91b3e9f78371&showid=24efbfbdefbfbd3befbf","size":41924080,"seconds":196.48},{"url":"http://27.221.15.179/67764C9E5AA3F83717E4BD29D4/0300010E035A424E4440284451ED9C6B56296C-B562-4542-B291-E95E04459939.flv?ccode=0512&duration=194&expire=18000&psid=999bf30208b475b55cb35858fd89392a&ups_client_netip=2f6427ec&ups_ts=1514863485&ups_userid=&utid=fenREmfB8gwCAS9kJ%2BwCtnAC&vid=XMzI3MTMyMjE0NA%3D%3D&vkey=A89f4a31e273d797f2481be3dc5eb13f3&showid=24efbfbdefbfbd3befbf","size":15281104,"seconds":194.239},{"url":"http://27.221.15.179/657599707E439831C300274297/0300010E045A424E4440284451ED9C6B56296C-B562-4542-B291-E95E04459939.flv?ccode=0512&duration=195&expire=18000&psid=999bf30208b475b55cb35858fd89392a&ups_client_netip=2f6427ec&ups_ts=1514863485&ups_userid=&utid=fenREmfB8gwCAS9kJ%2BwCtnAC&vid=XMzI3MTMyMjE0NA%3D%3D&vkey=A28ea434a04d086696bdb82744bfce338&showid=24efbfbdefbfbd3befbf","size":27279118,"seconds":195.12},{"url":"http://27.221.15.179/6777B2FA6473682F188DDB34C0/0300010E055A424E4440284451ED9C6B56296C-B562-4542-B291-E95E04459939.flv?ccode=0512&duration=191&expire=18000&psid=999bf30208b475b55cb35858fd89392a&ups_client_netip=2f6427ec&ups_ts=1514863485&ups_userid=&utid=fenREmfB8gwCAS9kJ%2BwCtnAC&vid=XMzI3MTMyMjE0NA%3D%3D&vkey=A95cd7962635d43ea41c7f3a971e29daf&showid=24efbfbdefbfbd3befbf","size":67167973,"seconds":191.72},{"url":"http://27.221.15.179/6576FFCC8233682F188DDB2F22/0300010E065A424E4440284451ED9C6B56296C-B562-4542-B291-E95E04459939.flv?ccode=0512&duration=197&expire=18000&psid=999bf30208b475b55cb35858fd89392a&ups_client_netip=2f6427ec&ups_ts=1514863485&ups_userid=&utid=fenREmfB8gwCAS9kJ%2BwCtnAC&vid=XMzI3MTMyMjE0NA%3D%3D&vkey=A546226163dbb397deb656b7cd257244e&showid=24efbfbdefbfbd3befbf","size":73818803,"seconds":197.039},{"url":"http://27.221.15.179/6577B2FA81F3182AA724B3308B/0300010E075A424E4440284451ED9C6B56296C-B562-4542-B291-E95E04459939.flv?ccode=0512&duration=192&expire=18000&psid=999bf30208b475b55cb35858fd89392a&ups_client_netip=2f6427ec&ups_ts=1514863485&ups_userid=&utid=fenREmfB8gwCAS9kJ%2BwCtnAC&vid=XMzI3MTMyMjE0NA%3D%3D&vkey=A1d2153d8a47c10284f6692507fdda5b6&showid=24efbfbdefbfbd3befbf","size":29693729,"seconds":192.599},{"url":"http://27.221.15.179/697B32E0B874883F173B9F30F1/0300010E085A424E4440284451ED9C6B56296C-B562-4542-B291-E95E04459939.flv?ccode=0512&duration=199&expire=18000&psid=999bf30208b475b55cb35858fd89392a&ups_client_netip=2f6427ec&ups_ts=1514863485&ups_userid=&utid=fenREmfB8gwCAS9kJ%2BwCtnAC&vid=XMzI3MTMyMjE0NA%3D%3D&vkey=A18e23bc09cc5309ea5d7849c3bb8edbf&showid=24efbfbdefbfbd3befbf","size":18554443,"seconds":199.12}]
     */

    private int type;
    private List<UrlBean> url;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<UrlBean> getUrl() {
        return url;
    }

    public void setUrl(List<UrlBean> url) {
        this.url = url;
    }

    public static class UrlBean {
        /**
         * url : http://27.221.15.179/6572CCB890E30829C3A94528C7/0300010E005A424E4440284451ED9C6B56296C-B562-4542-B291-E95E04459939.flv?ccode=0512&duration=195&expire=18000&psid=999bf30208b475b55cb35858fd89392a&ups_client_netip=2f6427ec&ups_ts=1514863485&ups_userid=&utid=fenREmfB8gwCAS9kJ%2BwCtnAC&vid=XMzI3MTMyMjE0NA%3D%3D&vkey=Acee485fdc32af88972a23e34fb55a7f3&showid=24efbfbdefbfbd3befbf
         * size : 53474953
         * seconds : 195.2
         */

        private String url;
        private int size;
        private double seconds;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public double getSeconds() {
            return seconds;
        }

        public void setSeconds(double seconds) {
            this.seconds = seconds;
        }
    }
}
