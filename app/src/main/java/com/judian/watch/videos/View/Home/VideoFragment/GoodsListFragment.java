package com.judian.watch.videos.View.Home.VideoFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.judian.watch.videos.DiyView.RV.PAdapter;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Mode.VideoListMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.ImgLoadUtils;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Dialog.VipDialog;
import com.judian.watch.videos.View.Home.PAYWebActivityTwo;
import com.judian.watch.videos.View.Home.ShareWebActivity;
import com.judian.watch.videos.databinding.GoodsListFragmentBinding;
import com.judian.watch.videos.databinding.VideoListOneItemBinding;

import static com.judian.watch.videos.Http.UrlUtils.URI.getplayfivestars;
import static com.judian.watch.videos.Http.UrlUtils.URI.share_app;

/**
 * Created by 李鹏 2018/1/2 0002.
 */

public class GoodsListFragment extends Fragment implements MyHttpCallBack {
    private GoodsListFragmentBinding binding;
    private Context mContext;
    private int p = 0;
//    private PayTopLayBinding topLayBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.goods_list_fragment, null, false);
//        topLayBinding=DataBindingUtil.inflate(LayoutInflater.from(mContext),R.layout.pay_top_lay,null,false);
        new OkHttpUtils(2, this)
                .SetApiUrl(getplayfivestars)
                .GET();
        return binding.getRoot();
    }


    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 2:
                VideoListMode mode = new JsonUtil<VideoListMode>()
                        .json2Bean(jsonString, VideoListMode.class.getName());
                binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

                @SuppressLint("SetTextI18n")
                PAdapter adapter = new PAdapter<>(mode.getList(), R.layout.video_list_one_item, (b, position) -> {
                    VideoListOneItemBinding bb = (VideoListOneItemBinding) b;
                    bb.line.setVisibility(View.VISIBLE);

                    bb.vodActor.setVisibility(View.VISIBLE);
                    bb.vodVersion.setVisibility(View.VISIBLE);
                    bb.vodActor.setText("主演：" + mode.getList().get(position).getVod_actor());
                    bb.vodActor.setSelected(true);
                    if (TextUtils.isEmpty(mode.getList().get(position).getVod_version())) {
                        bb.vodVersion.setText("版本：高清");
                    } else {
                        bb.vodVersion.setText("版本：" + mode.getList().get(position).getVod_version());
                    }
                    ImgLoadUtils.init(mContext)
                            .Uri(mode.getList().get(position).getVod_pic())
                            .Show(bb.img);
                    bb.buqueding.setVisibility(View.VISIBLE);
                    bb.name.setText(mode.getList().get(position).getVod_name());

                    bb.types.setText("类型：" + mode.getList().get(position).getVod_type());
                    bb.buqueding.setText("地区：" + mode.getList().get(position).getVod_area());
                    bb.yer.setText("年代：" + mode.getList().get(position).getVod_year());

                    bb.rootView.setOnClickListener(v -> {
                        p = position;

//                        if (MyApplication.getInstance().getUser().getGuoqi().equals("0")) {//非vip
//
//                            MyApplication.getInstance().setVid(mode.getList().get(p).getVod_id() + "");
//                            MyApplication.getInstance().setTitle(mode.getList().get(p).getVod_name());
//                            VipDialog.show(mContext, ButtType ->
//                                    startActivity(new Intent(mContext, ShareWebActivity.class)
//                                            .putExtra(KEY.TITLE, "分享有奖")
//                                            .putExtra(KEY.URL, share_app)));
//                        } else {//vip
                        try {
                            startActivity(new Intent(mContext, PAYWebActivityTwo.class)
                                    .putExtra(KEY.TITLE, mode.getList().get(p).getVod_name())
                                    .putExtra(KEY.STAR, mode.getList().get(p).getVod_stars() + "")
                                    .putExtra(KEY.VOD_ID, mode.getList().get(p).getVod_id() + "")
                                    .putExtra(KEY.IMG_URL, mode.getList().get(p).getVod_pic())
                                    .putExtra(KEY.video_details, mode.getList().get(p).getVod_type() + "  "
                                            + mode.getList().get(p).getVod_area() + "  "
                                            + mode.getList().get(p).getVod_year() + "  "));
                            getActivity().finish();
                        } catch (Exception e) {
                            ToastUtils.show("此视频暂时无法播放");
                        }

//                        }
                    });

                });

                binding.recyclerView.setNestedScrollingEnabled(false);

                binding.recyclerView.setAdapter(adapter);


                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        binding.rootView.setVisibility(View.GONE);
    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }
}
