package com.judian.watch.videos.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.judian.watch.videos.R;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
import java.util.List;

import static com.judian.watch.videos.Http.UrlUtils.WX_APP_ID;
import static com.judian.watch.videos.Mode.KEY.DOWNLOAD_LINK;

/**
 * Created by Administrator on 2017/10/13 0013.
 */

public class AppUtils {


    public static void Install(Context context, String backString) {
        if (!TextUtils.isEmpty(backString)) {
            try {
                PreferencesUtil.removeKey(DOWNLOAD_LINK);
                File file = new File(backString);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri apkUri;
                //判读版本是否在7.0以上
                if (Build.VERSION.SDK_INT >= 24) {
                    apkUri = FileProvider.getUriForFile(MyApplication.getInstance(), "com.repair.car.app.business", file);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } else {
                    apkUri = Uri.fromFile(file);
                }
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception e) {
                ToastUtils.show(context.getString(R.string.gxsb));
            }
        } else {
            ToastUtils.show(context.getString(R.string.gxsb));
        }
    }

    private static IWXAPI api; // 相应的包，请集成SDK后自行引入

    /**
     * 判断微信客户端是否存在
     *
     * @return true安装, false未安装
     */
    public static boolean isWeChatAppInstalled(Context context) {


        api = WXAPIFactory.createWXAPI(context, WX_APP_ID);
        if (api.isWXAppInstalled() && api.isWXAppSupportAPI()) {
            return true;
        } else {
            final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
            if (pinfo != null) {
                for (int i = 0; i < pinfo.size(); i++) {
                    String pn = pinfo.get(i).packageName;
                    if (pn.equalsIgnoreCase("com.tencent.mm")) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /**
     * 判断微信客户端是否存在
     *
     * @return true安装, false未安装
     */
    public static boolean isTBAppInstalled(Context context) {

        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equalsIgnoreCase("com.taobao.taobao")) {
                    return true;
                }
            }
        }
        return false;
    }
}
