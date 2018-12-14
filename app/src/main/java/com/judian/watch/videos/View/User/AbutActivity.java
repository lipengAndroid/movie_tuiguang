package com.judian.watch.videos.View.User;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.cuihai.library.StatusBarUtils;
import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.databinding.AboutActivityBinding;

/**
 * Created by 李鹏 2017/11/28 0028.
 */

public class AbutActivity extends BaseActivity implements View.OnClickListener {


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AboutActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.about_activity);
        binding.title.imgBack.setOnClickListener(this);
        StatusBarUtils.transparencyBar(this);
        binding.title.title.setText(R.string.gywm);
        binding.logo.setOnClickListener(this);
        binding.code.setText(getString(R.string.bbh) + MyApplication.getInstance().getVersionCodeString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
