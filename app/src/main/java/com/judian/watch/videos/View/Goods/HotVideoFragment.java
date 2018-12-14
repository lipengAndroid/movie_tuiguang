package com.judian.watch.videos.View.Goods;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.judian.watch.videos.DiyView.RV.PAdapter;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.HotVideoMode;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.ImgLoadUtils;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Dialog.VipDialog;
import com.judian.watch.videos.View.Home.PAYWebActivityTwo;
import com.judian.watch.videos.View.Home.ShareWebActivity;
import com.judian.watch.videos.databinding.HomeFragmentListItemBinding;
import com.judian.watch.videos.databinding.HotVideoFragmentBinding;

import java.util.ArrayList;
import java.util.List;

import static com.judian.watch.videos.Http.UrlUtils.URI.remen;
import static com.judian.watch.videos.Http.UrlUtils.URI.share_app;

/**
 * Created by 李鹏 2017/11/25 0025.
 */

public class HotVideoFragment extends Fragment implements MyHttpCallBack, View.OnClickListener {

    private Context mContext;

    private HotVideoFragmentBinding binding;

    private PAdapter adapter;

    private final int httpClassType = 23;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (binding == null) {

            mContext = getActivity();

            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.hot_video_fragment, container, false);


            binding.title.title.setText("热播");

            binding.title.imgBack.setVisibility(View.INVISIBLE);

            initListData();

            initList();

        }

        return binding.getRoot();
    }


    private void initListData() {
        new OkHttpUtils(httpClassType, this)
                .SetApiUrl(remen)
                .isCache()
                .GET();
    }

    private List<HotVideoMode.ListBean> listBean = new ArrayList<>();

    private void initList() {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));


        adapter = new PAdapter<>(listBean, R.layout.home_fragment_list_item, (b, position) -> {
            HomeFragmentListItemBinding bb = (HomeFragmentListItemBinding) b;
            ImgLoadUtils.init(mContext)
                    .Uri(listBean.get(position).getSlide_pic())
                    .Show(bb.img);
            bb.title.setText(listBean.get(position).getSlide_name());

            bb.rootView.setOnClickListener(v -> {
                positions = position;
                statView();
            });

        });

        binding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 1));
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(MyApplication.getInstance().getX(), (
//                (MyApplication.getInstance().getX() / 10) * 4));
//        topLayouyBinding.img.setLayoutParams(params);
//        adapter.setHeaderView(topLayouyBinding.img);
        binding.recyclerView.setAdapter(adapter);
        binding.smartRefreshLayout.setOnRefreshListener(refreshlayout -> {
            refreshlayout.finishRefresh(1000);
            initListData();
        });

    }

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {

            case httpClassType:
                try {
                    HotVideoMode classVideoMode = new JsonUtil<HotVideoMode>().json2Bean(jsonString, HotVideoMode.class.getName());
                    listBean.clear();
                    listBean.addAll(classVideoMode.getList());
                    adapter.notifyDataSetChanged();
                } catch (Exception ignored) {
                }

                break;

        }
    }

    private void statView() {
//        if (MyApplication.getInstance().getUser().getGuoqi().equals("0")) {//非vip
//
////            MyApplication.getInstance().setUri(classVideoMode.getDomain()
////                    + listBean.get(positions).getVod_id());
//            MyApplication.getInstance().setVid(listBean.get(positions).getVod_id() + "");
//
//            MyApplication.getInstance().setTitle(listBean.get(positions).getSlide_name());
//            VipDialog.show(mContext, ButtType -> startActivity(new Intent(mContext, ShareWebActivity.class)
//                    .putExtra(KEY.TITLE, "分享有奖")
//                    .putExtra(KEY.URL, share_app)));
//        } else {//vip

            startActivity(new Intent(mContext, PAYWebActivityTwo.class)
//                    .putExtra(KEY.URL,
//                            classVideoMode.getDomain()
//                                    + listBean.get(positions).getVod_id())
                    .putExtra(KEY.TITLE, listBean.get(positions).getSlide_name())
                    .putExtra(KEY.STAR, listBean.get(positions).getVod_stars() + "")
                    .putExtra(KEY.VOD_ID, listBean.get(positions).getVod_id() + "")
                    .putExtra(KEY.IMG_URL, listBean.get(positions).getSlide_pic())
                    .putExtra(KEY.video_details, listBean.get(positions).getVod_type()));
            // + "  "
//            + listBean.get(positions).getVod_area() + "  "
//                    + listBean.get(positions).getVod_year() + "  "

//        }
    }

    private int positions = 0;

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }

    @Override
    public void onClick(View v) {

    }

}
