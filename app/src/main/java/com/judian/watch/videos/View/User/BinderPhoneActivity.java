package com.judian.watch.videos.View.User;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;

import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Interface.getUserInterFace;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Mode.UserInfoMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.GetUserInfo;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.NetWorkUtils;
import com.judian.watch.videos.Utils.PhoneUtils;
import com.judian.watch.videos.Utils.PreferencesUtil;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.Utils.TxtUtils;
import com.judian.watch.videos.View.Dialog.DialogUtils;
import com.judian.watch.videos.View.Main.MainActivity;
import com.judian.watch.videos.databinding.BandPhoneActivityBinding;

import static com.judian.watch.videos.Http.UrlUtils.URI.bindphonenumber;
import static com.judian.watch.videos.Http.UrlUtils.URI.saveappuser;
import static com.judian.watch.videos.Http.UrlUtils.URI.saveuserdevice;
import static com.judian.watch.videos.Http.UrlUtils.URI.sendvercode;
import static com.judian.watch.videos.Mode.KEY.USER_INFO;

/**
 * Created by 李鹏 2017/12/4 0004.
 */

public class BinderPhoneActivity extends BaseActivity implements MyHttpCallBack, OnClickListener {

    private BandPhoneActivityBinding binding;

    private TimeCount time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.band_phone_activity);
        binding.tvSendCode.setOnClickListener(this);
        binding.tvSubmit.setOnClickListener(this);
        time = new TimeCount(60000, 1000);
        if (!TextUtils.isEmpty(MyApplication.getInstance().getUser().getData().getUnionid())) {
            new OkHttpUtils(74, BinderPhoneActivity.this).SetApiUrl(saveuserdevice)
                    .SetKey("system", "sharpness", "operator", "network", "model", "channelid", "imei", "type", "unionid")
                    .SetValue(android.os.Build.VERSION.RELEASE + "",
                            (MyApplication.getInstance().getY() + "" + "*" + MyApplication.getInstance().getX()),
                            NetWorkUtils.getNetworkName(),
                            NetWorkUtils.getNetworkType(),
                            android.os.Build.MODEL,
                            "yyb",
                            PhoneUtils.getImei(mContext),
                            "1"
                            , MyApplication.getInstance().getUser().getData().getUnionid())
                    .POST();
        }

        binding.tvCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() < 6) {
                    binding.tvSubmit.setBackgroundResource(R.drawable.bg_hui_yuan);
                } else {

                    binding.tvSubmit.setBackgroundResource(R.drawable.bg_bule_fillet);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_send_code:
                if (binding.tvPhone.getText().length() != 11) {
                    ToastUtils.show(getString(R.string.qsrzqsjh));
                    return;
                }
                new OkHttpUtils(142, this)
                        .SetApiUrl(sendvercode)
                        .SetKey("unionid", "phonenum", "sign")
                        .SetValue(MyApplication.getInstance().getUser().getData().getUnionid(),
                                binding.tvPhone.getText() + ""
                                , TxtUtils.Md5("judianyingyinapp" + binding.tvPhone.getText() + "" +
                                        MyApplication.getInstance().getUser().getData().getUnionid()))
                        .POST();
                time.start();
                binding.tvSubmit.setBackgroundResource(R.drawable.bg_bule_fillet);
                break;
            case R.id.tv_submit:
                if (TextUtils.isEmpty(binding.tvPhone.getText() + "")) {
                    ToastUtils.show(getString(R.string.qsrsjh));
                    return;
                }
                if (binding.tvPhone.getText().length() != 11) {
                    ToastUtils.show(getString(R.string.qsrzqsjh));
                    return;
                }
                if (TextUtils.isEmpty(binding.tvCode.getText() + "")) {
                    ToastUtils.show(getString(R.string.qsryzm));
                    return;
                }
                if (TextUtils.isEmpty(PreferencesUtil.getString("uid"))) {
                    ToastUtils.show("用户信息获取失败");
                    return;
                }
                new OkHttpUtils(143, this)
                        .SetApiUrl(saveappuser)
                        .SetKey("unionid", "vercode", "phonenum", "password")
                        .SetValue(PreferencesUtil.getString("uid"),
                                binding.tvCode.getText() + "",
                                binding.tvPhone.getText() + "",
                                binding.tvPassword.getText() + "")
                        .POST();
                break;
        }
    }

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 142:
                ToastUtils.show(getString(R.string.fscg));
                break;
            case 143:
                ToastUtils.show(getString(R.string.bdcg));
//                UserInfoMode umo = new JsonUtil<UserInfoMode>()
//                        .json2Bean(jsonString, UserInfoMode.class.getName());
                new GetUserInfo().Get(PreferencesUtil.getString("uid")
                        , new getUserInterFace() {
                            @Override
                            public void ok(UserInfoMode userInfoMode) {
                                PreferencesUtil.putString(KEY.USER_INFO, new JsonUtil<UserInfoMode>()
                                        .bean2Json(userInfoMode));
                                MyApplication.getInstance().setLogin(true);
                                MyApplication.getInstance().setUser(userInfoMode);
                                ToastUtils.show("登陆成功！");
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

    class TimeCount extends CountDownTimer {// 计时器

        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            binding.tvSendCode.setText(R.string.hqyzm);
            binding.tvSendCode.setClickable(true);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            binding.tvSendCode.setClickable(false);
            binding.tvSendCode.setText(millisUntilFinished / 1000 + getString(R.string.m));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyApplication.getInstance().setUser(null);
        MyApplication.getInstance().setLogin(false);

        PreferencesUtil.removeKey(USER_INFO);

//        System.exit(0);
    }
}
