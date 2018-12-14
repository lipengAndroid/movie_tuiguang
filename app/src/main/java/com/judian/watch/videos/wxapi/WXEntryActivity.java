package com.judian.watch.videos.wxapi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.cuihai.library.StatusBarUtils;
import com.google.gson.JsonObject;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.EditTextDialogInterface;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Mode.UriListMode;
import com.judian.watch.videos.Mode.UserInfoMode;
import com.judian.watch.videos.Mode.WxUserInfoMode;
import com.judian.watch.videos.Mode.aouthMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.LogUtils;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.PreferencesUtil;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Dialog.EdDialogUtils;
import com.judian.watch.videos.View.Home.PAYWebActivityTwo;
import com.judian.watch.videos.View.Main.LoginActivity;
import com.judian.watch.videos.View.User.BinderPhoneActivity;
import com.judian.watch.videos.databinding.LoginActivityBinding;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.judian.watch.videos.Http.UrlUtils.URI.GET_WX_USER_INFO;
import static com.judian.watch.videos.Http.UrlUtils.URI.OAUTH;
import static com.judian.watch.videos.Http.UrlUtils.URI.getmyself;
import static com.judian.watch.videos.Http.UrlUtils.URI.savewxuser;
import static com.judian.watch.videos.Http.UrlUtils.URI.sharefilmvip;
import static com.judian.watch.videos.Http.UrlUtils.WX_APP_ID;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler, MyHttpCallBack {

    private IWXAPI api;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        StatusBarUtils.transparencyBar(this);
        if (api == null) {
            api = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
            api.registerApp(WX_APP_ID);
        }

        api.handleIntent(getIntent(), this);
        if (MyApplication.getInstance().isWxLogin()) {
            handleIntent(getIntent());
        }
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {//返回时的
        LogUtils.e("WX_onResp.errCode=" + resp.errCode);

        switch (resp.errCode) {
            case 0:
                if (!MyApplication.getInstance().isWxLogin()) {
                    ToastUtils.show("分享成功！");
                    UriListMode.ban ban = new UriListMode.ban();
                    ban.setVid(MyApplication.getInstance().getVid());
                    UriListMode mode;
                    if (TextUtils.isEmpty(PreferencesUtil.getString(KEY.URL_LIST))) {
                        mode = new UriListMode();
                        mode.setList(new ArrayList<>());
                        mode.getList().add(ban);
                    } else {
                        mode = new JsonUtil<UriListMode>()
                                .json2Bean(PreferencesUtil.getString(KEY.URL_LIST), UriListMode.class.getName());
                        mode.getList().add(ban);

                    }
                    PreferencesUtil.putString(KEY.URL_LIST, new JsonUtil<UriListMode>()
                            .bean2Json(mode));
                    if (MyApplication.getInstance().isShareVideo()) {
                        new OkHttpUtils(9, new MyHttpCallBack() {
                            @Override
                            public void ok(String jsonString, int httpTY) {
                                new OkHttpUtils(99, new MyHttpCallBack() {
                                    @Override
                                    public void ok(String jsonString, int httpTY) {
                                        UserInfoMode userInfoMode = new JsonUtil<UserInfoMode>()
                                                .json2Bean(jsonString, UserInfoMode.class.getName());
                                        MyApplication.getInstance().setUser(userInfoMode);
                                        MyApplication.getInstance().setLogin(true);
                                        PreferencesUtil.putString(KEY.USER_INFO, jsonString);
                                        if (!TextUtils.isEmpty(MyApplication.getInstance().getUri())) {
                                            startActivity(new Intent(MyApplication.getInstance(), PAYWebActivityTwo.class)
                                                    .putExtra(KEY.TITLE, MyApplication.getInstance().getTitle())
                                                    .putExtra(KEY.VOD_ID, MyApplication.getInstance().getVid())
                                                    .putExtra(KEY.video_details, MyApplication.getInstance().getVideo_details()));
                                        }
                                    }

                                    @Override
                                    public void error(String e, int uriType) {
                                        ToastUtils.show("获取VIP天数失败");
                                    }

                                    @Override
                                    public void downloadUpProgress(long Percentile, int httpTY) {

                                    }
                                }).SetApiUrl(getmyself)
                                        .SetKey("unionid")
                                        .SetValue(MyApplication.getInstance().getUser().getData().getUnionid())
                                        .POST();
                            }

                            @Override
                            public void error(String e, int uriType) {
                                ToastUtils.show("保存VIP天数失败");
                            }

                            @Override
                            public void downloadUpProgress(long Percentile, int httpTY) {

                            }
                        })
                                .SetApiUrl(sharefilmvip)
                                .SetKey("unionid", "vodid", "type")
                                .SetValue(MyApplication.getInstance().getUser().getData().getUnionid(),
                                        MyApplication.getInstance().getVid(), "1")
                                .POST();
                    }


                }
                break;
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void handleIntent(Intent intent) {
        SendAuth.Resp resp = new SendAuth.Resp(intent.getExtras());
        String wxCode = resp.code;
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {//用户同意
            getWxTonken(wxCode);
        } else {
            MyApplication.getInstance().setWxLogin(false);
            ToastUtils.show(getString(R.string.dlsbqshcs));
            LoginActivity.Finish();
            finish();
        }

    }


    private void wxfinish() {
        MyApplication.getInstance().setWxLogin(false);
        ToastUtils.show(getString(R.string.dlsb));
        finish();

    }

    private void getWxTonken(String code) {
        new OkHttpUtils(11, this).
                SetApiUrl(OAUTH)
                .SetKey("appid", "secret", "code", "grant_type")
                .SetValue(WX_APP_ID, "16a99c988c261914eeb852984408d61c", code, "authorization_code")
                .GetNoUrl();
    }


    private WxUserInfoMode userinfo;

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 11:// 微信获取用户token;
                try {
                    aouthMode mode = new JsonUtil<aouthMode>().json2Bean(jsonString, aouthMode.class.getName());
                    new OkHttpUtils(12, this)
                            .SetApiUrl(GET_WX_USER_INFO)
                            .SetKey("access_token", "openid")
                            .SetValue(mode.getAccess_token(),
                                    mode.getOpenid())
                            .GetNoUrl();

                } catch (Exception e) {
                    wxfinish();
                    ToastUtils.show("微信获取用户token失败");
                    LogUtils.e(e.getMessage());
                }
                break;

            case 12:// 微信获取用户信息
                userinfo = new JsonUtil<WxUserInfoMode>().json2Bean(
                        jsonString, WxUserInfoMode.class.getName());

                new OkHttpUtils(14, this)
                        .SetApiUrl(savewxuser)
                        .SetKey("nickname", "imageurl", "unionid", "openid", "type", "sex")
                        .SetValue(userinfo.getNickname(),
                                userinfo.getHeadimgurl(),
                                userinfo.getUnionid(),
                                userinfo.getOpenid(),
                                "1",
                                userinfo.getSex() + "")
                        .POST();

                break;

            case 14://保存微信用户信息;
                PreferencesUtil.putString(KEY.IS_ED_FIRST_CODE, "0");
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    if (jsonObject.has("code") && !jsonObject.getString("code").equals("0")) {
                        PreferencesUtil.putString(KEY.IS_ED_FIRST_CODE, "1");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new OkHttpUtils(15, this).SetApiUrl(getmyself)
                        .SetKey("unionid")
                        .SetValue(userinfo.getUnionid())
                        .POST();
                break;
            case 15:

                UserInfoMode userInfoMode = new JsonUtil<UserInfoMode>()
                        .json2Bean(jsonString, UserInfoMode.class.getName());
                PreferencesUtil.putString("uid", userInfoMode.getData().getUnionid());
                MyApplication.getInstance().setUser(userInfoMode);
                MyApplication.getInstance().setLogin(true);
                PreferencesUtil.putString(KEY.USER_INFO, jsonString);
//                if (userInfoMode.getData().getPhonenum() == null || TextUtils.isEmpty(userInfoMode.getData().getPhonenum())) {
//                    startActivity(new Intent(mContext, BinderPhoneActivity.class));
//                    LoginActivity.Finish();
//                    finish();
//                } else {
                    initPush(userInfoMode.getData().getCreatetime());
                    LoginActivity.Finish();
                    finish();
//                }
                break;

        }

    }

    private void initPush(String createTime) {
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.bindAccount(createTime, new CommonCallback() {
            @Override
            public void onSuccess(String s) {
            }

            @Override
            public void onFailed(String s, String s1) {

            }
        });
    }

    @Override
    public void error(String e, int uriType) {
        PreferencesUtil.removeKey(KEY.USER_INFO);
        switch (uriType) {
            case 11:
                ToastUtils.show("获取微信token信息失败code:1");
                finish();
                break;
            case 12:
                ToastUtils.show("获取微信信息失败code:2");
                finish();
                break;
            case 14:
                ToastUtils.show("保存用户信息失败code:3");
                finish();
                break;
            case 15:
                ToastUtils.show("获取用户信息失败code:4");
                finish();
                break;
        }
    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }
}