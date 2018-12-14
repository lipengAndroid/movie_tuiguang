package com.judian.watch.videos.View.Video;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.judian.watch.videos.DiyView.RV.PAdapter;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.BannerInfo;
import com.judian.watch.videos.Mode.BannerMode;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Mode.TuiJianInfo;
import com.judian.watch.videos.Mode.TuiJianMedo;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.ImgLoadUtils;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.LogUtils;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.MyBannerUtils;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Dialog.VipDialog;
import com.judian.watch.videos.View.Home.PAYWebActivityTwo;
import com.judian.watch.videos.View.Home.ShareWebActivity;
import com.judian.watch.videos.databinding.BannerViewLayoutBinding;
import com.judian.watch.videos.databinding.TuijianVideoListItemBinding;
import com.judian.watch.videos.databinding.VideoFragmentBinding;

import java.util.ArrayList;
import java.util.List;

import static com.judian.watch.videos.Http.UrlUtils.URI.banner;
import static com.judian.watch.videos.Http.UrlUtils.URI.reconmmend;
import static com.judian.watch.videos.Http.UrlUtils.URI.share_app;

/**
 * Created by 李鹏 2017/11/25 0025.
 */

public class VideoFragmentTuiJian extends Fragment implements MyHttpCallBack {

    private Context mContext;

    private VideoFragmentBinding binding;

    private BannerViewLayoutBinding myTopImgView;

    private final int httpBannerType = 2212;

    private final int httpClassType = 2213;

    //分类说明  1电影 ，2电视， 3动漫 ， 4综艺
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (binding == null) {

            mContext = getActivity();

            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.video_fragment, container, false);

            myTopImgView = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.banner_view_layout, null, false);

            initData();


        }

        return binding.getRoot();
    }

    private void initData() {
//        new OkHttpUtils(httpBannerType, this)
//                .SetApiUrl(banner)
//                .SetKey("type")
//                .SetValue("1")
//                .isCache()
//                .POST();
        initListData();

    }

    private void initListData() {
        new OkHttpUtils(httpClassType, this)
                .SetApiUrl(reconmmend)
                .isCache()
                .POST();
    }

    private List<List<TuiJianInfo>> listBean = new ArrayList<>();
    private TuiJianInfo info;
    private String[] titles = new String[]{"电影", "电视剧", "动漫", "综艺"};

    private void initList() {
        PAdapter adapter = new PAdapter<>(4, R.layout.tuijian_video_list_item, (b, position) -> {
            TuijianVideoListItemBinding bb = (TuijianVideoListItemBinding) b;

            bb.typeName.setText(titles[position]);

            List<ImageView> imgViews = new ArrayList<>();
            List<TextView> titleViews = new ArrayList<>();
            intiImgOrTxtView(bb, imgViews, titleViews);
            bb.typeName.setText(titles[position]);
            for (int i = 0; i < imgViews.size(); i++) {

                if (listBean.get(position).size() == 0) {
                    continue;
                }
                if (listBean.get(position).size() < i + 1) {
                    continue;
                }
                ImgLoadUtils.init(mContext)
                        .Uri(listBean.get(position).get(i).getVod_pic())
                        .Show(imgViews.get(i));
                titleViews.get(i).setText(listBean.get(position).get(i).getVod_name());
                final int finalI = i;
                imgViews.get(i).setOnClickListener(v -> {
                    info = listBean.get(position).get(finalI);
                    startView();
                });
            }
        });
        adapter.setHeaderView(myTopImgView.bannerView);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        binding.recyclerView.setAdapter(adapter);

        binding.smartRefreshLayout.setLoadmoreFinished(false);
        binding.smartRefreshLayout.setOnRefreshListener(refreshlayout -> {
            refreshlayout.finishRefresh(1000);
            initData();
        });
    }

    private void intiImgOrTxtView(TuijianVideoListItemBinding bb, List<ImageView> imgViews,
                                  List<TextView> titleViews) {
        imgViews.clear();
        titleViews.clear();
        imgViews.add(bb.one.img);
        imgViews.add(bb.two.img);
        imgViews.add(bb.three.img);
        imgViews.add(bb.four.img);
        imgViews.add(bb.fiv.img);
        imgViews.add(bb.six.img);

        titleViews.add(bb.one.name);
        titleViews.add(bb.two.name);
        titleViews.add(bb.three.name);
        titleViews.add(bb.four.name);
        titleViews.add(bb.fiv.name);
        titleViews.add(bb.six.name);
    }


    private TuiJianMedo classVideoMode;
    private BannerMode topmode;
    private boolean istop = false;

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case httpClassType:
                classVideoMode = new JsonUtil<TuiJianMedo>().json2Bean(jsonString, TuiJianMedo.class.getName());
                listBean.clear();
                listBean.addAll(classVideoMode.getList());
                initList();
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
                        startView();
                    });
                } catch (Exception e) {
                    LogUtils.e(e.getMessage());
                }

                break;

        }
    }

    private void startView() {
        if (istop) {
            startActivity(new Intent(mContext, PAYWebActivityTwo.class)
                    .putExtra(KEY.URL, topmode.getList().get(positions).getSlide_url())
                    .putExtra(KEY.TITLE, topmode.getList().get(positions).getSlide_name())
                    .putExtra(KEY.STAR, topmode.getList().get(positions).getSlide_status() + "")
                    .putExtra(KEY.VOD_ID, topmode.getList().get(positions).getVod_id() + "")
                    .putExtra(KEY.IMG_URL, topmode.getList().get(positions).getSlide_pic() + "")
                    .putExtra(KEY.video_details, topmode.getList().get(positions).getVod_type() + "  "
                            + topmode.getList().get(positions).getVod_area() + "  "
                            + topmode.getList().get(positions).getVod_year() + "  "));
            istop = false;
        } else {
            startActivity(new Intent(mContext, PAYWebActivityTwo.class)
                    .putExtra(KEY.URL,
                            classVideoMode.getDomain() + info.getVod_id())
                    .putExtra(KEY.TITLE, info.getVod_name())
                    .putExtra(KEY.IMG_URL, info.getVod_pic())
                    .putExtra(KEY.STAR, info.getVod_stars())
                    .putExtra(KEY.VOD_ID, info.getVod_id() + "")
                    .putExtra(KEY.video_details, info.getVod_area() + "  " + info.getVod_type() + "  " + info.getVod_year() + ""));
        }
    }

    private int positions = 0;

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }


}
