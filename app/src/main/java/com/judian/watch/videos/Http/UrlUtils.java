package com.judian.watch.videos.Http;

/**
 * Created by 10237 on 2016/11/11.
 * <p>
 * api 配置类
 */

public class UrlUtils {
//    public static String TEST_URL = "http://judian.s1.natapp.cc/weitao/";


//    public static String TEST_URL_TWO = "http://judian.s1.natapp.cc/movie/public/";


    public static String FORMAL_URL = "https://api.judianweitao.net/";


    public static String WX_APP_ID = "wxdb60d311eedfbbaf";//  wx44c6424547910646//zhege

    public static String REQUEST_URL = FORMAL_URL;//在这里修改正式or测试环境

    static final int GET = 0;
    static final int POST = 1;
    static final int POST_TWO = 4;
    static final int UP_FILE = 2;
    static final int DOWNLOAD = 3;
    static final int DOWNLOAD_png = 5;
//    www.dz0736.cn

    public static class URI {


        public static final String UPLOAD_IMG = "index.php?s=admin/api/savewxqrcode";

        public static final String OAUTH = "https://api.weixin.qq.com/sns/oauth2/access_token";

        public static final String GET_WX_USER_INFO = "https://api.weixin.qq.com/sns/userinfo";

        public static final String share_app = REQUEST_URL + "index.php?s=index/share/share_app";

        public static final String problem = REQUEST_URL + "index.php?s=index/problem/problem";

        public static final String share_h5 = REQUEST_URL + "index.php?s=index/share/share_h5&id=";

        public static final String sendvercode = "index.php/admin/Api/sendvercode";

        public static final String bindphonenumber = "index.php/admin/Api/bindphonenumber";

        public static final String saveuserdevice = "index.php/admin/Api/saveuserdevice";

        public static final String savewxuser = "index.php?s=admin/api/saveweixinuser";

        public static final String getversioninfo = "index.php/admin/Api/getversioninfo";

        public static final String getmyself = "index.php/admin/Api/getmyself";//获取

        public static final String submitsuggest = "index.php/admin/Api/submitsuggest";

        public static final String banner = "index.php/film/Api/banner";

        public static final String reconmmend = "index.php/film/Api/recommend";

        public static final String classlist = "index.php/film/Api/classlist";

        public static final String filter = "index.php/film/Api/filter";//

        public static final String search = "index.php/film/Api/search";//

        public static final String getvodbystar = "index.php/film/Api/getvodbystar";//

        public static final String filterclass = "index.php/film/Api/filterclass";//

        public static final String weitaoindex = "https://www.judianweitao.com/App.php?s=/Index/index";//

        public static final String vipcneterinfo = "index.php/admin/api/vipcenterinfo/";//

        public static final String addvipandguanying = "index.php/admin/Api/add_vip_guanying/";//

        public static final String getdomain = "index.php/admin/Api/getdomain/";//

        public static final String createvoucher = "index.php/admin/api/createvoucher/";//

        public static final String usevoucher = "index.php/admin/api/usevoucher/";//

        public static final String getpostervalue = "admin/api/getpostervalue/";//

        public static final String savewxaccount = "admin/api/savewxaccount/";//

        public static final String topic = "index.php/film/Api/topic/";//

        public static final String topicdetails = "index.php/film/Api/topicdetails/";//

        public static final String searchproxyinfo = "admin/api/searchproxyinfo/";//

        public static final String appvipvideo = "index.php/admin/api/appvipvideo/";//

        public static final String backinformation = "index.php/admin/api/backinformation/";//

        public static final String getadinfo = "index.php/admin/api/getadinfo/";//

//        public static final String getnewuservip = "index.php/admin/api/getnewuservip/";//

        static final String WX_TOKEY_IS_OLD = "https://api.weixin.qq.com/sns/auth";//

        public static final String checkorderfromyingyin = "https://www.judianweitao.com/index.php?s=Api/checkorderfromyingyin";//

        public static final String getPlayUrl = "index.php/film/Api/getPlayUrl/id/";//

        public static final String getplayfivestars = "/index.php/film/Api/getplayfivestars";//

        public static final String appplayad = "index.php/adapi/Playad/appplayad/";//

        public static final String jiexim3u8 = "/index.php/film/Api/jiexim3u8";//

        public static final String getvipinfo = "/index.php/admin/api/getvipinfo";//

        public static final String getfivestars = "index.php/film/Api/getfivestars";//

        public static final String sharefilmvip = "index.php/admin/api/sharefilmvip";//

        public static final String remen = "/index.php/film/Api/remen";//

        public static final String API_SEARCH_VOD = "https://www.judianweitao.net/index.php/film/Api/searchvod";//

        public static final String phone_applogin = "index.php?s=admin/api/applogin";//

        public static final String saveappuser = "index.php?s=admin/api/saveappuser";//

        public static final String updatepassword = "index.php?s=admin/api/updatepassword";//

        public static final String getvideoplayadress = "index.php?s=admin/api/getvideoplayadress";//

        public static final String usevoucher_two = "index.php?s=admin/api/usevoucher";//

        public static final String h5uri_h = "https://www.beichengo.com/index.php?s=/vod-play-id-";

        public static final String h5uri_f = "-sid-1-pid-1.html";

        public static final String bindcode = "index.php?s=admin/api/bindcode";//

    }


}
