package com.judian.watch.videos.View.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;

import com.judian.watch.videos.Interface.DialogListener;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.ImgLoadUtils;

/**
 * Created by 李鹏 2017/12/7 0007.
 */

public class PyqDialog {

    public static void show(Context context, final DialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.pyq_dialog);
        ImageView imageView = dialog.findViewById(R.id.img);
        ImgLoadUtils.init(context)
                .Uri(R.drawable.pop_share)
                .Show(imageView);
        dialog.findViewById(R.id.fx).setOnClickListener(v -> {
            listener.buttType(9);
            dialog.dismiss();
        });
        dialog.findViewById(R.id.guanbi).setOnClickListener(v -> {
            listener.buttType(1);
            dialog.dismiss();
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener((dialog1, keyCode, event) -> true);
        dialog.show();
    }
}
