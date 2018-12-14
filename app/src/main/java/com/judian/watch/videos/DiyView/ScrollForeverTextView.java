package com.judian.watch.videos.DiyView;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * 解决多个textview 是跑马灯无效textview
 */
public class ScrollForeverTextView extends android.support.v7.widget.AppCompatTextView {
    public ScrollForeverTextView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public ScrollForeverTextView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public ScrollForeverTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }
    //转载请注明出处：http://blog.csdn.net/u014071694/article/details/52004542
    @Override
    public boolean isFocused() {
        // TODO Auto-generated method stub
        return true;// 重点
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        // TODO Auto-generated method stub
        super.onFocusChanged(true, direction, previouslyFocusedRect);// 重点
    }
}