package com.judian.watch.videos.View.Dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cuihai.library.StatusBarUtils;
import com.judian.watch.videos.R;

/**
 * Created by 李鹏 2017/12/17 0017.
 */

public class YDDialog extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ydy_dialog);
        StatusBarUtils.transparencyBar(this);
        findViewById(R.id.ok).setOnClickListener(v -> {
            startActivity(new Intent(YDDialog.this, YDDtwoialog.class));
            finish();
        });
    }
}
