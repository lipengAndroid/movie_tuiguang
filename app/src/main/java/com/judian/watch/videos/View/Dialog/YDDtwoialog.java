package com.judian.watch.videos.View.Dialog;

import android.app.Activity;
import android.os.Bundle;

import com.cuihai.library.StatusBarUtils;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.PreferencesUtil;

/**
 * Created by 李鹏 2017/12/17 0017.
 */

public class YDDtwoialog extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ydy_dialog_two);
        StatusBarUtils.transparencyBar(this);
        findViewById(R.id.ok).setOnClickListener(v -> {
            PreferencesUtil.putString("YDDtwoialog", "001");
            finish();
        });
    }

}
