package com.judian.watch.videos.Utils;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;

import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.View.Dialog.DialogUtils;

import static com.judian.watch.videos.Mode.KEY.DOWNLOAD_LINK;


/*
 * Created by Administrator on 2017/10/13 0013.
 */

public class DownloadUtils {

    private static String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,};

    private Activity activity;

    private String uri;
    private String msg;
    private boolean isUpdate;
    private MyHttpCallBack httpBack;

    public DownloadUtils init(Activity a) {
        activity = a;
        return this;
    }

    public void Down(String uri, String msg, boolean isUpdate, MyHttpCallBack httpBack) {
        this.httpBack = httpBack;
        this.uri = uri;
        this.msg = msg;
        this.isUpdate = isUpdate;
//        if (ActivityCompat.checkSelfPermission(activity, needPermissions[0]) != 0
//                || ActivityCompat.checkSelfPermission(activity, needPermissions[1]) != 0
//                || ActivityCompat.checkSelfPermission(activity, needPermissions[2]) != 0) {
//            ActivityCompat.requestPermissions(activity, needPermissions, 1210);
//        } else {
        upDataDialog(uri, msg, isUpdate, httpBack);
//        }
    }

    public void onRequestPermissionsResult(int requestCode, int[] grantResults) {
        if (requestCode == 1210) {
            if (grantResults[0] == 0 && grantResults[1] == 0 && grantResults[2] == 0) {
                upDataDialog(uri, msg, isUpdate, httpBack);
            } else {
                new DialogUtils().init(activity)
                        .setTitle("没有文件读写权限")
                        .setOne("确定", ButtType -> {
                            if (isUpdate) {
                                activity.finish();
                            }
                        });

            }
        }
    }

    private void upDataDialog(final String uri, final String msg, final boolean isUpdate, final MyHttpCallBack httpBack) {
        PreferencesUtil.putString(DOWNLOAD_LINK, uri);
        MyApplication.getInstance().setUpDialogisShow(true);
        new DialogUtils().init(activity)
                .setTitle("发现新版本" + msg)
                .setOne("\t\t升级\t\t", ButtType ->
                        Download(uri, httpBack)).setTwo("暂不升级", ButtType -> {

            if (isUpdate) {
                new DialogUtils().init(activity)
                        .setTitle("此版本必须升级哦，不升级将导致软件无法使用")
                        .setOne("\t\t升级\t\t", ButtType12 -> Download(uri, httpBack)).setTwo("先不升级", ButtType1 -> {
                    MyApplication.getInstance().setUpDialogisShow(false);
                    PreferencesUtil.removeKey(DOWNLOAD_LINK);
                    activity.finish();
                });
            } else {
                MyApplication.getInstance().setUpDialogisShow(false);
                PreferencesUtil.removeKey(DOWNLOAD_LINK);
            }

        }).isTouchcCancel(false);
    }

    private void Download(String uri, MyHttpCallBack httpBack) {
        ToastUtils.show("后台下载中...");
        MyApplication.getInstance().setUpDialogisShow(false);
        new OkHttpUtils(333, httpBack)
                .SetApiUrl(uri)
                .Download();

    }
}
