package com.judian.watch.videos.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 判断是否有网
 * Created by Administrator on 2017/1/11 0011.
 */

public class NetWorkUtils {

    public static boolean isNetworkConnected() {

        ConnectivityManager mConnectivityManager = (ConnectivityManager) MyApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert mConnectivityManager != null;
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null;

    }

    public static String getNetworkName() {
        if (isNetworkConnected()) {

            TelephonyManager telManager = (TelephonyManager) MyApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);

            assert telManager != null;
            String operator = telManager.getSimOperator();

            if (operator != null) {

                switch (operator) {
                    case "46000":
                    case "46002":
                    case "46007":
                        return "中国移动";
                    case "46001":
                        return "中国联通";
                    case "46003":
                        return "中国电信";
                }
            }
            return "获取失败";
        } else {
            return "获取失败";
        }

    }

    public static String getNetworkType() {
        if (isNetworkConnected()) {
            ConnectivityManager connManager = (ConnectivityManager) MyApplication.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            assert connManager != null;
            NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (null != wifiInfo) {
                NetworkInfo.State state = wifiInfo.getState();
                if (null != state)
                    if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                        return "wifi";
                    }
            }

            TelephonyManager telManager = (TelephonyManager) MyApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);

            assert telManager != null;
            String operator = telManager.getSimOperator();

            if (operator != null) {

                switch (operator) {
                    case "46000":
                    case "46002":
                    case "46007":
                        return "中国移动4g";
                    case "46001":
                        return "中国联通4g";
                    case "46003":
                        return "中国电信4g";
                }
            }
            return "获取失败";
        } else {
            return "获取失败";
        }

    }

}
