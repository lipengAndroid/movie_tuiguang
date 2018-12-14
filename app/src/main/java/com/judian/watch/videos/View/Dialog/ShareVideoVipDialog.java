package com.judian.watch.videos.View.Dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.ShareMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.WxShareUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import static com.judian.watch.videos.Http.UrlUtils.URI.backinformation;
import static com.judian.watch.videos.Http.UrlUtils.URI.getvipinfo;
import static com.judian.watch.videos.Mode.KEY.IMG_URL;
import static com.judian.watch.videos.Mode.KEY.VOD_ID;

/**
 * Created by 李鹏 2018/1/3 0003.
 */

public class ShareVideoVipDialog {
    @SuppressLint("SetTextI18n")
    public void show(Context mContext, String vip, ShareMode shareMode, Bitmap bitmap, String name, Activity activity) {
        Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.share_video_dialog_layout);
        Window window = dialog.getWindow();
        assert window != null;
        window.setBackgroundDrawableResource(R.color.transparent);
        TextView textView = dialog.findViewById(R.id.title);
        textView.setText("分享即送" + vip + "天VIP");
        dialog.findViewById(R.id.gb).setOnClickListener(v -> dialog.dismiss());

        dialog.findViewById(R.id.share_pyq).setOnClickListener(v -> {
            MyApplication.getInstance().setWxLogin(false);
            MyApplication.getInstance().setShareVideo(true);

            WxShareUtils.sendWxWeb(mContext, shareMode.getUrl(),
                    "《" + name + "》 " + shareMode.getTitle(),
                    SendMessageToWX.Req.WXSceneTimeline, bitmap);
            activity.finish();
            dialog.dismiss();
        });


        dialog.findViewById(R.id.share_qun).setOnClickListener(v -> {

            MyApplication.getInstance().setWxLogin(false);
            MyApplication.getInstance().setShareVideo(true);
            WxShareUtils.sendWxWeb(mContext, shareMode.getUrl(),
                    "《" + name + "》 " + shareMode.getTitle(),
                    shareMode.getContent(),
                    SendMessageToWX.Req.WXSceneSession, bitmap);
            activity.finish();
            dialog.dismiss();
        });

        dialog.show();
    }


}
