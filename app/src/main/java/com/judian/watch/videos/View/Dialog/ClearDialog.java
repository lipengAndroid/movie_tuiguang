package com.judian.watch.videos.View.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Message;

import com.judian.watch.videos.R;

/**
 * Created by 李鹏 2017/11/30 0030.
 */

public class ClearDialog {
    private Dialog dialog;

    public void show(Context context) {
        dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.cleardialog);
        dialog.show();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                handler.sendEmptyMessage(9);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


    }

    @SuppressLint("HandlerLeak")
    private
    android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 9:
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                    break;
            }
        }
    };

}
