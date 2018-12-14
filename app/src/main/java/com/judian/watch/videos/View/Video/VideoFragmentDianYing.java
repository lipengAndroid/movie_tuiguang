package com.judian.watch.videos.View.Video;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.judian.watch.videos.DiyView.RV.PAdapter;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.BannerInfo;
import com.judian.watch.videos.Mode.BannerMode;
import com.judian.watch.videos.Mode.ClassVideoMode;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.ImgLoadUtils;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.MyBannerUtils;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Dialog.VipDialog;
import com.judian.watch.videos.View.Home.PAYWebActivityTwo;
import com.judian.watch.videos.View.Home.ShareWebActivity;
import com.judian.watch.videos.databinding.BannerViewLayoutBinding;
import com.judian.watch.videos.databinding.VideoFragmentBinding;
import com.judian.watch.videos.databinding.VideoListItemBinding;

import java.util.ArrayList;
import java.util.List;

import static com.judian.watch.videos.Http.UrlUtils.URI.banner;
import static com.judian.watch.videos.Http.UrlUtils.URI.classlist;
import static com.judian.watch.videos.Http.UrlUtils.URI.share_app;

/**
 * Created by 李鹏 2017/11/25 0025.
 */

public class VideoFragmentDianYing extends Fragment implements MyHttpCallBack, View.OnClickListener {

    private Context mContext;

    private VideoFragmentBinding binding;

    private PAdapter adapter;

    private BannerViewLayoutBinding myTopImgView;

    private final int httpBannerType = 22;

    private final int httpClassType = 23;

    private boolean isLoad = false;

    private int conter = 1;


    //分类说明  1电影 ，2电视， 3动漫 ， 4综艺
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (binding == null) {

            mContext = getActivity();

            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.video_fragment, container, false);

            myTopImgView = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.banner_view_layout, null, false);

            initData();

            initList();
        }

        return binding.getRoot();
    }

    private void initData() {
//        new OkHttpUtils(httpBannerType, this)
//                .SetApiUrl(banner)
//                .SetKey("type")
//                .SetValue("2")
//                .isCache()
//                .POST();
        initListData();

    }

    private void initListData() {
        new OkHttpUtils(httpClassType, this)
                .SetApiUrl(classlist)
                .SetKey("cid", "conter")
                .SetValue("1", conter + "")
                .isCache()
                .POST();
    }

    private List<ClassVideoMode.ListBean> listBean = new ArrayList<>();

    private void initList() {
        binding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));


        adapter = new PAdapter<>(listBean, R.layout.video_list_item, (b, position) -> {
            VideoListItemBinding bb = (VideoListItemBinding) b;
            ImgLoadUtils.init(mContext)
                    .Uri(listBean.get(position).getVod_pic())
                    .Show(bb.img);
            bb.name.setText(listBean.get(position).getVod_name());

            bb.rootView.setOnClickListener(v -> {
                istop = false;
                positions = position;
                statView();
            });

        });

        binding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        adapter.setHeaderView(myTopImgView.bannerView);

        binding.recyclerView.setAdapter(adapter);
        binding.smartRefreshLayout.setOnLoadmoreListener(refreshlayout -> {
            refreshlayout.finishLoadmore(1000);
            conter++;
            isLoad = true;
            initListData();
        });
        binding.smartRefreshLayout.setOnRefreshListener(refreshlayout -> {
            refreshlayout.finishRefresh(1000);
            isLoad = false;
            conter = 1;
            initData();
        });
    }

    private ClassVideoMode classVideoMode;
    private BannerMode topmode;
    private boolean istop = false;

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {

            case httpClassType:
                try {
                    classVideoMode = new JsonUtil<ClassVideoMode>().json2Bean(jsonString, ClassVideoMode.class.getName());
                    if (!isLoad) {
                        listBean.clear();
                    }
                    listBean.addAll(classVideoMode.getList());
                    adapter.notifyDataSetChanged();
                } catch (Exception ignored) {
                }

                break;
            case httpBannerType:
                topmode = new JsonUtil<BannerMode>()
                        .json2Bean(jsonString, BannerMode.class.getName());
                List<BannerInfo> list = new ArrayList<>();
                try {
                    for (int i = 0; i < topmode.getList().size(); i++) {
                        BannerInfo bannerInfo = new BannerInfo();
                        bannerInfo.setPic_url(topmode.getList().get(i).getSlide_pic());
                        bannerInfo.setLink_value(topmode.getList().get(i).getSlide_url());
                        bannerInfo.setTitle(topmode.getList().get(i).getSlide_name());
                        list.add(bannerInfo);
                    }
                    new MyBannerUtils().show(mContext, list, myTopImgView.bannerView);
                    myTopImgView.bannerView.setImgViewItemListener(position -> {
                        istop = true;
                        positions = position;
                        statView();
                    });
                } catch (Exception ignored) {

                }

                break;
        }
    }

    private void statView() {
//        if (MyApplication.getInstance().getUser().getGuoqi().equals("0")) {//非vip
//
//            MyApplication.getInstance().setUri(classVideoMode.getDomain()
//                    + listBean.get(positions).getVod_id());
//            MyApplication.getInstance().setVid(listBean.get(positions).getVod_id() + "");
//
//            MyApplication.getInstance().setTitle(listBean.get(positions).getVod_name());
//            VipDialog.show(mContext, ButtType -> startActivity(new Intent(mContext, ShareWebActivity.class)
//                    .putExtra(KEY.TITLE, "分享有奖")
//                    .putExtra(KEY.URL, share_app)));
//        } else {//vip
        if (istop) {
            startActivity(new Intent(mContext, PAYWebActivityTwo.class)
                    .putExtra(KEY.URL, topmode.getList().get(positions).getSlide_url())
                    .putExtra(KEY.TITLE, topmode.getList().get(positions).getSlide_name())
                    .putExtra(KEY.STAR, topmode.getList().get(positions).getSlide_status() + "")
                    .putExtra(KEY.VOD_ID, topmode.getList().get(positions).getVod_id() + "")
                    .putExtra(KEY.IMG_URL, topmode.getList().get(positions).getSlide_pic() + "")
                    .putExtra(KEY.video_details, topmode.getList().get(positions).getVod_type() + "  "
                            + topmode.getList().get(positions).getVod_area() + "  "
                            + topmode.getList().get(positions).getVod_year() + "  ")
                    .putExtra(KEY.IS_DIAN_YING, "1"));
        } else {
            startActivity(new Intent(mContext, PAYWebActivityTwo.class)
                    .putExtra(KEY.URL,
                            classVideoMode.getDomain()
                                    + listBean.get(positions).getVod_id())
                    .putExtra(KEY.TITLE, listBean.get(positions).getVod_name())
                    .putExtra(KEY.STAR, listBean.get(positions).getVod_stars() + "")
                    .putExtra(KEY.VOD_ID, listBean.get(positions).getVod_id() + "")
                    .putExtra(KEY.IMG_URL, listBean.get(positions).getVod_pic())
                    .putExtra(KEY.video_details, listBean.get(positions).getVod_type() + "  "
                            + listBean.get(positions).getVod_area() + "  "
                            + listBean.get(positions).getVod_year() + "  ")
                    .putExtra(KEY.IS_DIAN_YING, "1"));
        }

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
