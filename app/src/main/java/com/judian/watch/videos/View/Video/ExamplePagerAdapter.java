package com.judian.watch.videos.View.Video;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/*
 * Created by hackware on 2016/9/10.
 */

public class ExamplePagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mDataList;

    ExamplePagerAdapter(FragmentManager fm, List<Fragment> dataList) {
        super(fm);
        mDataList = dataList;
    }


    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }


    @Override
    public Fragment getItem(int position) {
        return mDataList.get(position);
    }
}
