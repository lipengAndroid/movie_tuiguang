package com.judian.watch.videos.View.Main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.judian.watch.videos.BR;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Interface.getUserInterFace;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Mode.UserInfoMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.GetUserInfo;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.PreferencesUtil;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Home.TXWebActivity;
import com.judian.watch.videos.databinding.LoginActivityPhoneBinding;

import static com.judian.watch.videos.Http.UrlUtils.URI.phone_applogin;


/**
 * Created by Administrator on 2017/9/21 0021.
 */

public class LoginActivityPhone extends AppCompatActivity implements View.OnClickListener, MyHttpCallBack {
    private LoginActivityPhoneBinding binding;
    private Context mContext;

    @SuppressLint("StaticFieldLeak")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity_phone);
        binding.tvRegister.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        binding.tvLogin.setOnClickListener(this);
        binding.tvForGetPassWord.setOnClickListener(this);
        binding.tvRegister.setOnClickListener(this);
        binding.tvForGetPassWord.setOnClickListener(this);
        binding.back.setOnClickListener(this);
//        new GetUserInfo().Get("oLGrow4PNl-scg-kFvcDh4LMJrco", new getUserInterFace() {
//            @Override
//            public void ok(UserInfoMode userInfoMode) {
//                PreferencesUtil.putString(KEY.USER_INFO, new JsonUtil<UserInfoMode>()
//                        .bean2Json(userInfoMode));
//                MyApplication.getInstance().setLogin(true);
//                MyApplication.getInstance().setUser(userInfoMode);
//
//                ToastUtils.show("登陆成功！");
//                startActivity(new Intent(mContext, MainActivity.class));
//                finish();
//            }
//
//            @Override
//            public void error(String e) {
//
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
//                startActivity(new Intent(mContext, RegisterActivity.class));
                break;
            case R.id.tv_login:
                if (TextUtils.isEmpty(binding.edPhone.getText() + "")) {
                    ToastUtils.show("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(binding.edPassWord.getText() + "")) {
                    ToastUtils.show("请输入密码");
                    return;
                }
                new OkHttpUtils(1, this)
                        .SetApiUrl(phone_applogin)
                        .SetKey("phonenum", "password")
                        .SetValue(binding.edPhone.getText() + "",
                                binding.edPassWord.getText() + "")
                        .POST();
                break;
            case R.id.tv_for_get_pass_word:
                startActivity(new Intent(mContext, ForGetPasswordActivity.class));
                break;
            case R.id.back:
                finish();
                break;
        }

    }

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 1:
                UserInfoMode umo = new JsonUtil<UserInfoMode>()
                        .json2Bean(jsonString, UserInfoMode.class.getName());
                new GetUserInfo().Get(umo.getData().getUnionid()
                        , new getUserInterFace() {
                            @Override
                            public void ok(UserInfoMode userInfoMode) {
                                PreferencesUtil.putString(KEY.USER_INFO, new JsonUtil<UserInfoMode>()
                                        .bean2Json(userInfoMode));
                                MyApplication.getInstance().setLogin(true);
                                MyApplication.getInstance().setUser(userInfoMode);
                                PreferencesUtil.putString("uid", userInfoMode.getData().getUnionid());
                                ToastUtils.show("登陆成功！");
                                LoginActivity.Finish();
                                MainActivity.Finish();
                                startActivity(new Intent(mContext, MainActivity.class));
                                finish();
                            }

                            @Override
                            public void error(String e) {

                            }
                        });

                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }

}
