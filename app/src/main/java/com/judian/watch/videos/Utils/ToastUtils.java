package com.judian.watch.videos.Utils;

import android.view.Gravity;
import android.widget.Toast;


public class ToastUtils {
    private static Toast toast = new Toast(MyApplication.getInstance());

    public static void show(Object info) {
        if (info.toString().length() > 30) {
            return;
        }
        if (info.toString().length() == 0) {
            return;
        }
        try {
            toast.cancel();
            toast.makeText(MyApplication.getInstance(), info + "", Toast.LENGTH_SHORT).show();
        } catch (Exception ignored) {
            LogUtils.e(ignored.getMessage());
        }

    }

    public static void showCENTER(Object info) {
        if (info.toString().length() > 30) {
            return;
        }
        if (info.toString().length() == 0) {
            return;
        }
        try {
            toast.cancel();
            Toast toast = Toast.makeText(MyApplication.getInstance(), info + "", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception ignored) {
            LogUtils.e(ignored.getMessage());
        }

    }

}
