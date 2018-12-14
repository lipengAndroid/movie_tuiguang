package com.judian.watch.videos.View.Home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;

import com.cuihai.library.StatusBarUtils;
import com.github.florent37.fiftyshadesof.FiftyShadesOf;
import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.DiyView.RV.PAdapter;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Mode.TopicDetailsMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.ImgLoadUtils;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Dialog.VipDialog;
import com.judian.watch.videos.databinding.HomeDetailsTopViewBinding;
import com.judian.watch.videos.databinding.ListActivityBinding;
import com.judian.watch.videos.databinding.VideoListItemBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import static com.judian.watch.videos.Http.UrlUtils.URI.share_app;
import static com.judian.watch.videos.Http.UrlUtils.URI.topicdetails;

/**
 * Created by 李鹏 2017/12/11 0011.
 */

public class HomeListItemDetailsActivity extends BaseActivity implements MyHttpCallBack, View.OnClickListener {
    private HomeDetailsTopViewBinding topViewBinding;
    private PAdapter adapter;
    private int p;
    private FiftyShadesOf fiftyShadesOf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.list_activity);
        binding.title.title.setText(getIntent().getStringExtra(KEY.TITLE));
        binding.title.imgBack.setOnClickListener(this);
        fiftyShadesOf = FiftyShadesOf.with(mContext);
        StatusBarUtils.transparencyBar(this);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        topViewBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.home_details_top_view, null, false);


        topViewBinding.dy.setVisibility(View.GONE);
        if (getIntent().hasExtra(KEY.URL)) {
            ImgLoadUtils.init(mContext)
                    .Uri(getIntent().getStringExtra(KEY.URL))
                    .Show(topViewBinding.img);
        }

        fiftyShadesOf.on(
                topViewBinding.tvDetails,
                topViewBinding.tvDetails1,
                topViewBinding.tvDetails2,
                topViewBinding.tvDetails3,
                topViewBinding.ztxq,
                topViewBinding.one.details,
                topViewBinding.one.name,
                topViewBinding.one.img,
                topViewBinding.one.details,

                topViewBinding.two.name,
                topViewBinding.two.img,
                topViewBinding.two.details,

                topViewBinding.three.name,
                topViewBinding.three.img,
                topViewBinding.three.details)
                .fadein(false).start();

        adapter = new PAdapter<>(listBeans, R.layout.video_list_item, (b, position) -> {
            VideoListItemBinding bin = (VideoListItemBinding) b;
            bin.details.setVisibility(View.VISIBLE);
            bin.details.setTextColor(ContextCompat.getColor(mContext, R.color.txt_black));
            ImgLoadUtils.init(mContext)
                    .Uri(listBeans.get(position).getVod_pic())
                    .Show(bin.img);
            bin.details.setText(listBeans.get(position).getVod_name());
            bin.name.setVisibility(View.GONE);

            bin.rootView.setOnClickListener(v -> {
                p = position;
                statView();
//                        https://vod.beichengo.com/index.php/vod-read-id-19724.html
            });
        });
        adapter.setHeaderView(topViewBinding.getRoot());
        binding.recyclerView.setAdapter(adapter);
        binding.smartRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(1000);
                getdata();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1000);
                getdata();
            }
        });
        getdata();
        ViewCompat.setTransitionName(topViewBinding.img, "img");

    }

    private void statView() {
//        if (MyApplication.getInstance().getUser().getGuoqi().equals("0")) {//非vip
//
//            MyApplication.getInstance().setUri(mode.getDomain()
//                    + listBeans.get(p).getVod_id());
//            MyApplication.getInstance().setVid(listBeans.get(p).getVod_id() + "");
//            MyApplication.getInstance().setTitle(listBeans.get(p).getVod_name());
//            VipDialog.show(mContext, ButtType -> startActivity(new Intent(mContext, ShareWebActivity.class)
//                    .putExtra(KEY.TITLE, "分享有奖")
//                    .putExtra(KEY.URL, share_app)));
//        } else {//vip
//            startActivity(new Intent(mContext, PAYWebActivityTwo.class)
//                    .putExtra(KEY.URL,
//                            mode.getDomain()
//                                    + listBeans.get(p).getVod_id())
//                    .putExtra(KEY.TITLE, listBeans.get(p).getVod_name())
//                    .putExtra(KEY.STAR, listBeans.get(p).getVod_stars() + "")
//                    .putExtra(KEY.VOD_ID, listBeans.get(p).getVod_id()));

            startActivity(new Intent(mContext, PAYWebActivityTwo.class)
                    .putExtra(KEY.TITLE, listBeans.get(p).getVod_name() + "")
                    .putExtra(KEY.STAR, listBeans.get(p).getVod_stars() + "")
                    .putExtra(KEY.VOD_ID, listBeans.get(p).getVod_id() + "")
            );
            //.putExtra(KEY.video_details, listBeans.get(p).getVod_type() + "  "
            //  +listBeans.get(p).getVod_area() + "  "
            //   + listBeans.get(p).getVod_year() + "  ")
//        }
    }

    private List<TopicDetailsMode.ListBean> listBeans = new ArrayList<>();
    private TopicDetailsMode mode;

    @SuppressLint("SetTextI18n")
    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 99:
                mode = new JsonUtil<TopicDetailsMode>()
                        .json2Bean(jsonString, TopicDetailsMode.class.getName());
                listBeans.clear();
                listBeans.addAll(mode.getList());
                ImgLoadUtils.init(mContext).Uri(mode.getBanner())
                        .Show(topViewBinding.img);
                if (mode.getContent().contains("<p>") && mode.getContent().contains("</p>")) {
                    String ss = mode.getContent().replace("<p>", "");
                    String sss = ss.replace("</p>", "");
                    topViewBinding.tvDetails.setText("                " + sss);
                } else {
                    topViewBinding.tvDetails.setText("                " + mode.getContent());

                }
                topViewBinding.dy.setVisibility(View.VISIBLE);
                topViewBinding.tvDetails1.setVisibility(View.GONE);
                topViewBinding.tvDetails2.setVisibility(View.GONE);
                topViewBinding.tvDetails3.setVisibility(View.GONE);
                topViewBinding.ztxq.setText(R.string.ztxq);
                topViewBinding.ssLay.setVisibility(View.GONE);
                fiftyShadesOf.stop();

                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        if (uriType == 99) {
            fiftyShadesOf.stop();
            topViewBinding.ssLay.setVisibility(View.GONE);
            ToastUtils.show("暂无数据");
        }
    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    public void getdata() {
        new OkHttpUtils(99, this)
                .SetApiUrl(topicdetails)
                .SetKey("id")
                .SetValue(getIntent().getStringExtra(KEY.ID))
                .POST();
    }
}
