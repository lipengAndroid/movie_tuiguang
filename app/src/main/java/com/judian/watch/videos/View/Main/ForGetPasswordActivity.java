package com.judian.watch.videos.View.Main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.Utils.TxtUtils;
import com.judian.watch.videos.databinding.ForGetPasswordActivityBinding;

import static com.judian.watch.videos.Http.UrlUtils.URI.sendvercode;
import static com.judian.watch.videos.Http.UrlUtils.URI.updatepassword;


/**
 * Created by 李鹏 2018/2/23 0023.
 */

public class ForGetPasswordActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack {
    private ForGetPasswordActivityBinding binding;
    private TimeCount time;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.for_get_password_activity);
        binding.tvGetCode.setOnClickListener(this);
        binding.tvSubmit.setOnClickListener(this);
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        time = new TimeCount(60000, 1000);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_code:
                if (TextUtils.isEmpty(binding.edPhone.getText() + "")) {
                    ToastUtils.show("请输入手机号");
                    return;
                }
                new OkHttpUtils(1, this)
                        .SetApiUrl(sendvercode)
                        .SetKey("phonenum", "sign")
                        .SetValue(
                                binding.edPhone.getText() + ""
                                , TxtUtils.Md5("judianyingyinapp" + binding.edPhone.getText() + ""))
                        .POST();
                break;
            case R.id.tv_submit:
                if (TextUtils.isEmpty(binding.edPhone.getText() + "")) {
                    ToastUtils.show("请输入手机号");
                    return;
                }
                if (TextUtils.isEmpty(binding.edSmsCode.getText() + "")) {
                    ToastUtils.show("请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(binding.edPassWord.getText() + "")) {
                    ToastUtils.show("请输入密码");
                    return;
                }
                if (binding.edPassWord.getText().length() < 6) {
                    ToastUtils.show("请输入至少6位密码");
                    return;
                }
                if (TextUtils.isEmpty(binding.edPassWordTwo.getText() + "")) {
                    ToastUtils.show("请再次输入密码");
                    return;
                }
                if (!binding.edPassWord.getText().toString().equals(binding.edPassWordTwo.getText().toString())) {
                    ToastUtils.show("两次密码不一致");
                    return;
                }
                new OkHttpUtils(2, this)
                        .SetApiUrl(updatepassword)
                        .SetKey("phonenum", "vercode", "password")
                        .SetValue(
                                binding.edPhone.getText() + "",
                                binding.edSmsCode.getText() + "",
                                binding.edPassWord.getText() + "")
                        .POST();
                break;
        }
    }

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 1:
                time.start();
                break;
            case 2:
                ToastUtils.show("修改成功！");
                finish();
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

    private class TimeCount extends CountDownTimer {// 计时器

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            binding.tvGetCode.setText("获取验证码");
            binding.tvGetCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            binding.tvGetCode.setClickable(false);
            binding.tvGetCode.setText(millisUntilFinished / 1000 + "秒");
        }

    }
}
