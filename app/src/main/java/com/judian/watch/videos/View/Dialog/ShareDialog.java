package com.judian.watch.videos.View.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.WxShareUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

/**
 * Created by 李鹏 2017/12/16 0016.
 */

public class ShareDialog {
    public static void show(final Context context, final String title, final String details, final String uri, Bitmap bitmap) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.share_dialog_layout);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        assert window != null;
        window.setGravity(Gravity.BOTTOM);
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = MyApplication.getInstance().getX(); // 宽度
//        lp.height = MyApplication.getInstance().getY() / 3; // 高度
        lp.alpha = 0.96f; // 透明度
        dialog.getWindow().setDimAmount(0.16f);
        window.setBackgroundDrawableResource(R.color.transparent);
        window.setAttributes(lp);
        dialog.findViewById(R.id.lay_we_chat).setOnClickListener(v -> {
            MyApplication.getInstance().setWxLogin(false);
            WxShareUtils.sendWxWeb(context,  uri,
                    title, details,
                    SendMessageToWX.Req.WXSceneSession, bitmap);
            dialog.dismiss();
        });
        dialog.findViewById(R.id.lay_we_fz).setOnClickListener(v -> {
            MyApplication.getInstance().setWxLogin(false);
            WxShareUtils.sendWxWeb(context, uri,
                    title, details,
                    SendMessageToWX.Req.WXSceneTimeline,bitmap);
            dialog.dismiss();
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(v -> dialog.dismiss());
        dialog.show();


    }
}
