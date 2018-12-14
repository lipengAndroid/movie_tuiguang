package com.judian.watch.videos.View.User;

import com.cuihai.library.StatusBarUtils;
import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.LogUtils;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.databinding.FeedBackActivityBinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import static com.judian.watch.videos.Http.UrlUtils.URI.submitsuggest;

/**
 * Created by 李鹏 2017/11/28 0028.
 */

public class FeedBackActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack {
    private FeedBackActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.feed_back_activity);
        binding.title.imgBack.setOnClickListener(this);
        binding.tvSubmit.setOnClickListener(this);
        StatusBarUtils.transparencyBar(this);
        binding.title.title.setText(R.string.jyfk);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_submit:
                if (TextUtils.isEmpty(binding.phoneOrQq.getText() + "")) {
                    ToastUtils.show(getString(R.string.qsrlxfs));
                    return;
                }
                if (TextUtils.isEmpty(binding.details.getText() + "")) {
                    ToastUtils.show(getString(R.string.qsrfklr));
                    return;
                }
                LogUtils.e(MyApplication.getInstance().getUser().getData().getUnionid() +
                        binding.phoneOrQq.getText() + "" +
                        binding.details.getText() + "");
                new OkHttpUtils(541, FeedBackActivity.this)
                        .SetApiUrl(submitsuggest)
                        .SetKey("unionid", "title", "content", "type")
                        .SetValue(MyApplication.getInstance().getUser().getData().getUnionid(),
                                binding.phoneOrQq.getText() + "",
                                binding.details.getText() + "", "1")
                        .POST();
                break;
        }
    }

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 541:
                ToastUtils.show(getString(R.string.fkcg));
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
}
