package com.judian.watch.videos.DiyView;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.ToastUtils;

import java.util.List;

/**
 * Created by Administrator on 2015/12/28.
 */
public class MyBannerView extends RelativeLayout {

    private List<View> mViewList = null;

    private CirclePageIndicator indicator;

    private ViewPager mViewPager;

    private int index = 0;

    private TimeCount time;

    private int bannerCount;

    public MyBannerView(Context context) {
        super(context);
    }

    public MyBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        LayoutInflater.from(getContext()).inflate(R.layout.banner_view, this);

        mViewPager = findViewById(R.id.main_view_pager);

        indicator = findViewById(R.id.indicator);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int height = 0;

        // 下面遍历所有child的高度

        for (int i = 0; i < getChildCount(); i++) {

            View child = getChildAt(i);

            child.measure(widthMeasureSpec,

                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

            int h = child.getMeasuredHeight();

            if (h > height) // 采用最大的view的高度。

                height = h;

        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,

                MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setValue(List<View> viewList, boolean IsJup,boolean IsDot) {

        this.mViewList = viewList;

        bannerCount = mViewList.size();

        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return mViewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(mViewList.get(position));
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {

                container.addView(mViewList.get(position));
                View view = mViewList.get(position);
                view.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        if (listener != null) {
                            listener.ItemListener(position);
                        }

                    }
                });
                return mViewList.get(position);
            }
        });

        indicator.setViewPager(mViewPager);

        time = new TimeCount(Integer.MAX_VALUE, 3500);
        if (!IsJup) {
            indicator.setVisibility(GONE);
        } else {
            indicator.setVisibility(VISIBLE);
        }

        if (!isStart & IsDot) {
            time.start();

            isStart = true;
        }
    }

    private boolean isStart = false;

    private boolean isPause = false;


    class TimeCount extends CountDownTimer {// 计时器

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示

            if (!isPause) {
                index = mViewPager.getCurrentItem();

                index++;

                if (index == bannerCount) {
                    index = 0;
                }
                mViewPager.setCurrentItem(index);
            } else {
                isPause = !isPause;
            }

        }

    }

    private ImgViewItemListener listener;

    public void setImgViewItemListener(ImgViewItemListener listener) {
        this.listener = listener;
    }

    public interface ImgViewItemListener {
        void ItemListener(int position);

    }

}