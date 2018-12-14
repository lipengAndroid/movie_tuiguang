package com.judian.watch.videos.View.Video;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.judian.watch.videos.Interface.DialogListener;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Dialog.DialogUtils;
import com.judian.watch.videos.databinding.VideoFatherFragmentBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李鹏 2017/12/5 0005.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    private VideoFatherFragmentBinding binding;
    private Context mContext;
    private List<Fragment> list = new ArrayList<>();
    private List<String> tabs = new ArrayList<>();
    private Animation in, out;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            mContext = getActivity();
            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.video_father_fragment, null, false);


            list.add(new VideoFragmentTuiJian());
            list.add(new VideoFragmentDianYing());
            list.add(new VideoFragmentDianShi());
            list.add(new VideoFragmentDongMan());
            list.add(new VideoFragmentZongYi());

            tabs.add("推荐");
            tabs.add("电影");
            tabs.add("电视剧");
            tabs.add("动漫");
            tabs.add("综艺");


            binding.search.setOnClickListener(this);
            binding.classs.setOnClickListener(this);
            ExamplePagerAdapter mExamplePagerAdapter = new ExamplePagerAdapter(getChildFragmentManager(), list);
            binding.viewPager.setAdapter(mExamplePagerAdapter);

            binding.indicator.setTabItemTitles(tabs);
            binding.indicator.setViewPager(binding.viewPager);

            binding.classs.setVisibility(View.INVISIBLE);
            binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0) {
                        if (binding.classs.getVisibility() != View.INVISIBLE) {
                            out = AnimationUtils.loadAnimation(mContext, R.anim.classs_view_out);
                            binding.classs.setAnimation(out);
                            binding.classs.setVisibility(View.INVISIBLE);
                        }

                    } else {

                        if (binding.classs.getVisibility() != View.VISIBLE) {
                            binding.classs.setVisibility(View.VISIBLE);
                            in = AnimationUtils.loadAnimation(mContext, R.anim.classs_view_in);
                            binding.classs.setAnimation(in);
                        }


                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            binding.viewPager.setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
        }

        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.classs:
                if (binding.viewPager.getCurrentItem() != 0) {
                    startActivity(new Intent(mContext, BrushActivity.class)
                            .putExtra(KEY.ID, binding.viewPager.getCurrentItem() + ""));
                }
                break;
            case R.id.search:
                startActivity(new Intent(mContext, SearchActivity.class));
                break;
        }
    }
}
