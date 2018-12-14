package com.judian.watch.videos.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by 李鹏 2018/1/3 0003.
 */

public class BrightnessUtils {

    private static BrightnessUtils mApplication;
    private float mBrightness = -1f;

    public static synchronized BrightnessUtils getMy() {
        if (mApplication == null) {
            mApplication = new BrightnessUtils();
        }
        return mApplication;
    }

    public void onBrightnessSlide(float percent, Activity view) {
        if (mBrightness < 0) {
            mBrightness = view.getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;
        }
        WindowManager.LayoutParams lpa = view.getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;
        view.getWindow().setAttributes(lpa);
    }
}
