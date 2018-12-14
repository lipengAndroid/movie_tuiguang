package com.judian.watch.videos.View.Home;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.judian.watch.videos.DiyView.RV.PAdapter;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Interface.getUserInterFace;
import com.judian.watch.videos.Mode.HomeTopListMode;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Mode.TopicMode;
import com.judian.watch.videos.Mode.UserInfoMode;
import com.judian.watch.videos.Mode.homeListMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.GetUserInfo;
import com.judian.watch.videos.Utils.ImgLoadUtils;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.PreferencesUtil;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Dialog.VipDialog;
import com.judian.watch.videos.databinding.HomeFragmentBinding;
import com.judian.watch.videos.databinding.HomeFragmentListItemBinding;
import com.judian.watch.videos.databinding.TopViewItemBinding;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityOptionsCompat.makeSceneTransitionAnimation;
import static com.judian.watch.videos.Http.UrlUtils.URI.appvipvideo;
import static com.judian.watch.videos.Http.UrlUtils.URI.share_app;
import static com.judian.watch.videos.Http.UrlUtils.URI.topic;

/**
 * Created by 李鹏 2017/11/25 0025.
 */

public class jdyyHomeFragment extends Fragment implements MyHttpCallBack {
    private Context mContext;
    private HomeFragmentBinding binding;
    private PAdapter VideoadApter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (binding == null) {
            mContext = getActivity();

            binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.home_fragment, container, false);

            initData();

            initRootListViewTwo();

//        initRootListView();
            binding.smartRefreshLayout.setOnRefreshListener(refreshlayout -> {
                refreshlayout.finishRefresh(500);
                getdata();
            });
            binding.smartRefreshLayout.setOnLoadmoreListener(refreshlayout -> {
                refreshlayout.finishLoadmore(1);
                getdata();
            });
        }

        return binding.getRoot();
    }

    private void initRootListViewTwo() {

        binding.recView.setLayoutManager(new LinearLayoutManager(mContext));

        VideoadApter = new PAdapter<>(topics, R.layout.home_fragment_list_item, (b, position) -> {
            final HomeFragmentListItemBinding nn = (HomeFragmentListItemBinding) b;
            nn.title.setText(topics.get(position).getSpecial_name());
            ImgLoadUtils.init(mContext)
                    .Uri(topics.get(position).getSpecial_banner())
                    .Show(nn.img);
            nn.rootView.setOnClickListener(v -> {
                Intent userIntent = new Intent(mContext, HomeListItemDetailsActivity.class)
                        .putExtra(KEY.TITLE, topics.get(position).getSpecial_name())
                        .putExtra(KEY.URL, topics.get(position).getSpecial_banner())
                        .putExtra(KEY.ID, topics.get(position).getSpecial_id());

                ActivityOptionsCompat options = makeSceneTransitionAnimation(
                        getActivity(), Pair.create(nn.img, "img"));


                startActivityForResult(userIntent, 1, options.toBundle());
            });
        });
        binding.recView.setAdapter(VideoadApter);
    }


    private void initData() {

        getdata();

    }

    private int finalIi = 0;
    private boolean isshuaxin = false;
    private List<homeListMode.ListBean> listBeans = new ArrayList<>();

    private List<TopicMode.ListBean> topics = new ArrayList<>();
    private List<HomeTopListMode.ListBean> topViewDatas = new ArrayList<>();

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 281:
                homeListMode modes = new JsonUtil<homeListMode>()
                        .json2Bean(jsonString, homeListMode.class.getName());
                if (isshuaxin) listBeans.clear();
                isshuaxin = false;
                if (modes != null && modes.getList().size() > 0) {
                    listBeans.addAll(modes.getList());
                }
                break;
            case 282:
                TopicMode mode = new JsonUtil<TopicMode>()
                        .json2Bean(jsonString, TopicMode.class.getName());
                topics.clear();
                try {
                    topics.addAll(mode.getList());
                    isshuaxin = false;
                    VideoadApter.notifyDataSetChanged();
                } catch (Exception ignored) {
                }
                break;
            case 283:
                try {
                    HomeTopListMode mode1 = new JsonUtil<HomeTopListMode>().json2Bean(jsonString, HomeTopListMode.class.getName());
                    topViewDatas.clear();
                    topViewDatas.addAll(mode1.getList());
                    binding.hScrollView.removeAllViews();
                    try {
                        for (int i = 0; i < topViewDatas.size(); i++) {
                            TopViewItemBinding bb = DataBindingUtil.inflate(LayoutInflater.from(mContext)
                                    , R.layout.top_view_item, null, false);
                            bb.txt.setText(topViewDatas.get(i).getTitle());
                            ImgLoadUtils.init(mContext).Uri(topViewDatas.get(i).getImageurl())
                                    .Show(bb.img);
                            final int finalI = i;
                            bb.rootView.setOnClickListener(v -> {
                                finalIi = finalI;
                                if (MyApplication.getInstance().isLogin() && !TextUtils.isEmpty(PreferencesUtil.getString("uid"))) {
                                    new GetUserInfo().Get(PreferencesUtil.getString("uid")
                                            , new getUserInterFace() {
                                                @Override
                                                public void ok(UserInfoMode userInfoMode) {

//                                                    if (MyApplication.getInstance().getUser().getGuoqi().equals("0")) {
//                                                        VipDialog.show(mContext, ButtType ->
//                                                                startActivity(new Intent(mContext, ShareWebActivity.class)
//                                                                        .putExtra(KEY.TITLE, "分享有奖")
//                                                                        .putExtra(KEY.URL, share_app)));
//                                                    } else {
                                                    startActivity(new Intent(mContext, TXWebActivity.class)
                                                            .putExtra(KEY.TITLE, topViewDatas.get(finalIi).getTitle())
                                                            .putExtra(KEY.URL, topViewDatas.get(finalIi).getUrl()));
//                                                    }
                                                }

                                                @Override
                                                public void error(String e) {

                                                }
                                            });
                                } else {
                                    ToastUtils.showCENTER("请先登录");
                                }

                            });
                            binding.hScrollView.addView(bb.getRoot());
                        }
                    } catch (Exception ignored) {

                    }
                } catch (Exception ignored) {

                }


                break;
        }
    }


    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }

    public void getdata() {

        new OkHttpUtils(282, this)
                .SetApiUrl(topic)
                .isCache()
                .POST();
        new OkHttpUtils(283, this)
                .SetApiUrl(appvipvideo)
                .isCache()
                .POST();
    }
}
