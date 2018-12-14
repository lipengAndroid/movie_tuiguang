package com.judian.watch.videos.View.Video;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.judian.watch.videos.DiyView.RV.PAdapter;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Mode.LeixinListMode;
import com.judian.watch.videos.Mode.shaixuanMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.ImgLoadUtils;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Home.PAYWebActivityTwo;
import com.judian.watch.videos.databinding.BrushActivityBinding;
import com.judian.watch.videos.databinding.TextviewLayoutBinding;
import com.judian.watch.videos.databinding.TypeItemItemBinding;
import com.judian.watch.videos.databinding.VideoListItemBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.judian.watch.videos.Http.UrlUtils.URI.filter;
import static com.judian.watch.videos.Http.UrlUtils.URI.filterclass;

/**
 * Created by 李鹏 2017/12/1 0001.
 */

public class ClassBrushFragment extends Fragment implements View.OnClickListener, MyHttpCallBack {
    private BrushActivityBinding binding;
    private int conter = 1;
    private String type = "", year = "", area = "";
    private List<LeixinListMode.ListBean> lists = new ArrayList<>();
    private boolean isclear = false;
    private PAdapter videoadapter;
    private int p = 0;
    private Context mContext;
    private String id = "1";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding==null){
            mContext = getActivity();
            binding = DataBindingUtil.inflate(inflater, R.layout.brush_activity, container, false);
//        StatusBarUtils.transparencyBar(this);
            binding.gb.setVisibility(View.INVISIBLE);
            binding.title.setText("分类");


            new OkHttpUtils(84, this)
                    .SetApiUrl(filterclass)
                    .SetKey("id")
                    .SetValue(id)
                    .POST();

            getdata();

            videoadapter = new PAdapter<>(lists, R.layout.video_list_item, (bb, position) -> {
                VideoListItemBinding nn = (VideoListItemBinding) bb;
                ImgLoadUtils.init(mContext)
                        .Uri(lists.get(position).getVod_pic())
                        .Show(nn.img);
                nn.name.setText(lists.get(position).getVod_name());

                nn.rootView.setOnClickListener(v -> {

                    p = position;
//                if (MyApplication.getInstance().getUser().getGuoqi().equals("0")) {//非vip
//
//                    MyApplication.getInstance().setUri(mode.getDomain()
//                            + lists.get(p).getVod_id());
//
//                    MyApplication.getInstance().setVid(lists.get(p).getVod_id() + "");
//                    MyApplication.getInstance().setTitle(lists.get(p).getVod_name());
//                    VipDialog.show(mContext, ButtType -> startActivity(new Intent(mContext, ShareWebActivity.class)
//                            .putExtra(KEY.TITLE, getString(R.string.fxyj))
//                            .putExtra(KEY.URL, share_app)));
//                } else {//vip
                    startActivity(new Intent(mContext, PAYWebActivityTwo.class)
                            .putExtra(KEY.URL,
                                    mode.getDomain()
                                            + lists.get(p).getVod_id())
                            .putExtra(KEY.TITLE, lists.get(p).getVod_name())
                            .putExtra(KEY.STAR, lists.get(p).getVod_stars() + "")
                            .putExtra(KEY.VOD_ID, lists.get(p).getVod_id() + "")
                            .putExtra(KEY.IMG_URL, lists.get(p).getVod_pic())
                            .putExtra(KEY.video_details, lists.get(p).getVod_type() + "  "
                                    + lists.get(p).getVod_area() + "  "
                                    + lists.get(p).getVod_year() + "  "));
//                }
                });
            });
            binding.videoListView.setLayoutManager(new GridLayoutManager(mContext, 3));
            binding.videoListView.setAdapter(videoadapter);

            binding.smartRefreshLayout.setOnLoadmoreListener(refreshlayout -> {
                refreshlayout.finishLoadmore(1000);
                conter++;
                isclear = false;
                getdata();
            });
            binding.smartRefreshLayout.setOnRefreshListener(refreshlayout -> {
                refreshlayout.finishLoadmore(1000);
                conter = 1;
                isclear = true;
                getdata();
            });

        }

        return binding.getRoot();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gb:
                break;
        }
    }

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 84:
                shaixuanMode sx = new JsonUtil<shaixuanMode>()
                        .json2Bean(jsonString, shaixuanMode.class.getName());
                for (int i = 0; i < 4; i++) {

                    final TypeItemItemBinding bbb = DataBindingUtil.inflate(LayoutInflater.from(mContext)
                            , R.layout.type_item_item, null, false);
                    binding.typView.addView(bbb.getRoot());
                    List<String> names = new ArrayList<>();
                    if (i == 0) {
                        names.add("电影");
                        names.add("电视剧");
                        names.add("动漫");
                        names.add("综艺");
                    }
                    if (i == 1) {
                        names.add("全部类型");
                        names.addAll(Arrays.asList(sx.getList().getTypeX().split(",")));
                    }
                    if (i == 2) {
                        names.add("全部地区");
                        names.addAll(Arrays.asList(sx.getList().getArea().split(",")));
                    }
                    if (i == 3) {
                        names.add("全部年份");
                        names.addAll(Arrays.asList(sx.getList().getYear().split(",")));
                    }

                    for (int a = 0; a < names.size(); a++) {
                        TextviewLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext)
                                , R.layout.textview_layout, null, false);
                        binding.text.setText(names.get(a));
                        binding.text.setTextColor(ContextCompat.getColor(mContext, R.color.txt_black));
                        if (a == 0) {
                            binding.text.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_bule_v19));
                            binding.text.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                        }
                        bbb.gridLayout.addView(binding.getRoot());
                        final int finalA = a;

                        final int finalI = i;
                        bbb.gridLayout.getChildAt(a).setOnClickListener(v -> {

                            for (int b = 0; b < bbb.gridLayout.getChildCount(); b++) {
                                TextView textView = (TextView) bbb.gridLayout.getChildAt(b);
                                textView.setBackground(null);
                                textView.setTextColor(ContextCompat.getColor(mContext, R.color.txt_black));
                            }
                            TextView textView = (TextView) bbb.gridLayout.getChildAt(finalA);
                            textView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_bule_v19));
                            textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
//                                type,
//                                        year,
//                                        area
                            isclear = true;
                            if (finalI == 0) {
                                if (textView.getText().toString().equals("电影")) {
                                    id = "1";
                                }
                                if (textView.getText().toString().equals("电视剧")) {
                                    id = "2";
                                }
                                if (textView.getText().toString().equals("动漫")) {
                                    id = "3";
                                }
                                if (textView.getText().toString().equals("综艺")) {
                                    id = "4";
                                }

                            }


                            if (finalI == 1) {
                                if (textView.getText().toString().equals("全部类型")) {
                                    type = "";
                                } else {
                                    type = textView.getText() + "";
                                }
                            }
                            if (finalI == 2) {
                                if (textView.getText().toString().equals("全部地区")) {
                                    area = "";
                                } else {
                                    area = textView.getText() + "";
                                }
                            }
                            if (finalI == 3) {
                                if (textView.getText().toString().equals("全部年份")) {
                                    year = "";
                                } else {
                                    year = textView.getText() + "";
                                }
                            }
                            getdata();
                        });

                    }

                }
                break;
            case 85:
                mode = new JsonUtil<LeixinListMode>().json2Bean(jsonString, LeixinListMode.class.getName());
                if (isclear) {
                    lists.clear();
                }
                try {
                    lists.addAll(mode.getList());
                    videoadapter.notifyDataSetChanged();
                } catch (Exception ignored) {
                }


                break;
        }
    }

    private LeixinListMode mode;

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }

    public void getdata() {
        type = type.replace(" ", "");
        year = year.replace(" ", "");
        area = area.replace(" ", "");

        new OkHttpUtils(85, this)
                .SetApiUrl(filter)
                .SetKey("cid", "conter", "type", "year", "area")
                .SetValue(id,
                        conter + "",
                        type,
                        year,
                        area)
                .POST();
    }
}
