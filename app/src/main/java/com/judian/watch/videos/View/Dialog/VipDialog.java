package com.judian.watch.videos.View.Dialog;

import android.app.Dialog;
import android.content.Context;

import com.judian.watch.videos.Interface.DialogListener;
import com.judian.watch.videos.R;

/**
 * Created by 李鹏 2017/12/7 0007.
 */

public class VipDialog {

    public static void show(Context context, final DialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.dialog);

        dialog.setContentView(R.layout.vip_dialog);
        dialog.findViewById(R.id.fx).setOnClickListener(v -> {
            listener.buttType(0);
            dialog.dismiss();
        });
        dialog.show();
    }
}
