package com.judian.watch.videos.DiyView;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by fcl on 2017/6/17.
 * description:透明动画
 */

public class AlphaPageTransformer implements ViewPager.PageTransformer {

    private float mMinAlpha = 0.8f;

    /**
     * 根据position来处理每一个view
     *
     * @param page
     * @param position
     */
    @Override
    public void transformPage(View page, float position) {
        Log.i("AlphaPageTransformer", "--position:" + position);
        if (position < -1) {     //左边的page
            page.setAlpha(1);

        } else if (position <= 1) {  //区间 [-1,1]

            if (position < 0) {  //当前的currentItem向左滑动，position由0-->-1
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
                page.setAlpha(factor);

            } else {            //页2向左滑时，position由1-->0
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
                page.setAlpha(factor);
            }

        } else {        //右边的page
            page.setAlpha(1);
        }
    }
}
