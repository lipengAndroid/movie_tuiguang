package com.judian.watch.videos.Utils;

import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Interface.getUserInterFace;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Mode.UserInfoMode;

import static com.judian.watch.videos.Http.UrlUtils.URI.getmyself;

/**
 * Created by 李鹏 2017/12/27 0027.
 */

public class GetUserInfo {

    public void Get(String unionid, getUserInterFace interFace) {

        new OkHttpUtils(514, new MyHttpCallBack() {
            @Override
            public void ok(String jsonString, int httpTY) {
                UserInfoMode userInfoMode = new JsonUtil<UserInfoMode>()
                        .json2Bean(jsonString, UserInfoMode.class.getName());
                MyApplication.getInstance().setUser(userInfoMode);
                MyApplication.getInstance().setLogin(true);
                PreferencesUtil.putString(KEY.USER_INFO, jsonString);
                interFace.ok(userInfoMode);
            }

            @Override
            public void error(String e, int uriType) {
                ToastUtils.show(e);
                interFace.error(e);
            }

            @Override
            public void downloadUpProgress(long Percentile, int httpTY) {

            }
        }).SetApiUrl(getmyself)
                .SetKey("unionid")
                .SetValue(unionid)
                .POST();
    }
}
