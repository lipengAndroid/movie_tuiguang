package com.judian.watch.videos.View.Main;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cuihai.library.StatusBarUtils;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.AppUtils;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Dialog.MyProgressDialog;
import com.judian.watch.videos.databinding.LoginActivityBinding;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import static com.judian.watch.videos.Http.UrlUtils.WX_APP_ID;

/**
 * Created by 李鹏 2017/11/28 0028.
 */

public class LoginActivity extends AppCompatActivity {

    private LoginActivityBinding binding;

    private IWXAPI api;

    private SendAuth.Req req = new SendAuth.Req();

    private static final ThreadLocal<LoginActivity> loginActivity = new ThreadLocal<>();

    private Context mContext;

    public static void Finish() {
        if (loginActivity.get() != null) {
            loginActivity.get().finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity);
        loginActivity.set(this);
        mContext = this;
        StatusBarUtils.transparencyBar(this);

        api = WXAPIFactory.createWXAPI(this, WX_APP_ID, false);

        MyApplication.getInstance().setWxLogin(true);

        binding.login.setOnClickListener(v -> {
            if (AppUtils.isWeChatAppInstalled(mContext)) {
                req.scope = "snsapi_userinfo";
                api.sendReq(req);
                binding.login.setVisibility(View.GONE);
                MyProgressDialog.getInstance().show(mContext, "登陆中...");

            } else {
                ToastUtils.show("尚未安装微信，请安装微信");
            }
        });

        binding.loginPhone.setOnClickListener(v ->
                startActivity(new Intent(mContext, LoginActivityPhone.class)));

        binding.back.setOnClickListener(v -> finish());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyProgressDialog.getInstance().cancel();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MyProgressDialog.getInstance().cancel();
    }
}
