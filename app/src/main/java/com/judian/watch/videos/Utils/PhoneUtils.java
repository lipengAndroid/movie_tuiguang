package com.judian.watch.videos.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import static android.content.Context.TELEPHONY_SERVICE;

/*
 * Created by Administrator on 2017/10/30 0030.
 */

public class PhoneUtils {

    @SuppressLint("HardwareIds")
    public static String getImei(Context context) {
        TelephonyManager mTm;
        mTm = (TelephonyManager) MyApplication.getInstance().getSystemService(TELEPHONY_SERVICE);


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_PHONE_STATE}, 100);
        } else {
            assert mTm != null;
            return mTm.getDeviceId();
        }
        return "null";
    }
}
