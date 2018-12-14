package com.judian.watch.videos.Utils;

import android.content.Context;


public class DpiUtil {

    /**
     * 根据屏幕密度，将Dip转换为Px
     *
     * @param context  上下文对象
     * @return Px值
     */
    public static int dip2px(Context context) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) ((float) 44 * (scale / 160) + 0.5f);
    }

/*    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) ((pxValue * 160) / scale + 0.5f);
    }

    public static int dip2px(float dipValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) (dipValue * (scale / 160) + 0.5f);
    }

    public static int px2dip(float pxValue, Context context) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return (int) ((pxValue * 160) / scale + 0.5f);
    }*/

}
