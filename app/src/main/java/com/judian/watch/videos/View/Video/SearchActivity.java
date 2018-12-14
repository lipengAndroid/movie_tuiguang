package com.judian.watch.videos.View.Video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.cuihai.library.StatusBarUtils;
import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.DiyView.RV.PAdapter;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Http.OkHttpUtilsStatic;
import com.judian.watch.videos.Http.UrlUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.HotMode;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Mode.SearchList;
import com.judian.watch.videos.Mode.SearchListMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.ImgLoadUtils;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.StringUtils;
import com.judian.watch.videos.Utils.TextViewParser;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Dialog.VipDialog;
import com.judian.watch.videos.View.Home.PAYWebActivityTwo;
import com.judian.watch.videos.View.Home.ShareWebActivity;
import com.judian.watch.videos.databinding.SearchActivityBinding;
import com.judian.watch.videos.databinding.SearchActivityItemBinding;
import com.judian.watch.videos.databinding.SearchListItemBinding;
import com.judian.watch.videos.databinding.SearchListItemLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import static com.judian.watch.videos.Http.UrlUtils.URI.getvodbystar;
import static com.judian.watch.videos.Http.UrlUtils.URI.search;
import static com.judian.watch.videos.Http.UrlUtils.URI.share_app;

/**
 * Created by 李鹏 2017/12/1 0001.
 */

public class SearchActivity extends BaseActivity implements MyHttpCallBack, View.OnClickListener {
    private SearchActivityBinding binding;
    private PAdapter TOP10adapter;
    private PAdapter videoListAdapter;
    private int conter = 1;
    private PAdapter FuzzySearchAdapter;
    private boolean IsCkItem = false;

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.search_activity);
        StatusBarUtils.transparencyBar(this);
        binding.back.setOnClickListener(this);

        binding.editQuery.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    IsCkItem = true;
                    binding.layReMen.setVisibility(View.GONE);
                    binding.videoListView.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(searchString)) {
                        binding.searchList.setVisibility(View.VISIBLE);
                    }

                    break;
            }
            return false;
        });
        binding.btSs.setOnClickListener(this);
        binding.editQuery.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                ss();
                return true;
            }
            return false;
        });
        binding.editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchString = s.toString();
                if (s.length() == 0) {
                    binding.searchList.setVisibility(View.GONE);
                    binding.layReMen.setVisibility(View.VISIBLE);
                } else {
                    if (IsCkItem) {
                        OkHttpUtilsStatic.get(55, SearchActivity.this)
                                .SetApiUrl(UrlUtils.URI.API_SEARCH_VOD)
                                .SetKey("wd")
                                .SetValue(searchString)
                                .cancleAll()
                                .PostNoUrl();
                    }
                }
            }
        });

        TOP10adapter = new PAdapter<>(hotList, R.layout.search_activity_item, (b, position) -> {
            SearchActivityItemBinding bbc = (SearchActivityItemBinding) b;
            bbc.txt.setText(hotList.get(position).getVod_name());
            bbc.rootView.setOnClickListener(v -> {
                searchString = hotList.get(position).getVod_name();
                binding.editQuery.setText(searchString);
                getSearchData();//搜索列表
                binding.layReMen.setVisibility(View.GONE);
            });

            bbc.img.setText((position + 1) + "");
            switch (position) {
                case 0:
                    bbc.img.setBackgroundResource(R.color.chun_RED);
                    break;
                case 1:
                    bbc.img.setBackgroundResource(R.color.red_two);
                    break;
                case 2:
                    bbc.img.setBackgroundResource(R.color.red_three);
                    break;

            }

        });
        binding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        binding.recyclerView.setAdapter(TOP10adapter);


        videoListAdapter = new PAdapter<>(listBeans, R.layout.search_list_item, (b, position) -> {
            SearchListItemBinding bb = (SearchListItemBinding) b;

            bb.vodActor.setVisibility(View.VISIBLE);
            bb.vodVersion.setVisibility(View.VISIBLE);
            bb.vodActor.setText("主演：" + listBeans.get(position).getVod_actor());
            if (TextUtils.isEmpty(listBeans.get(position).getVod_version())) {
                bb.vodVersion.setText("版本：高清");
            } else {
                bb.vodVersion.setText("版本：" + listBeans.get(position).getVod_version());
            }
            ImgLoadUtils.init(mContext)
                    .Uri(listBeans.get(position).getVod_pic())
                    .Show(bb.img);
            bb.buqueding.setVisibility(View.VISIBLE);
            bb.name.setText(listBeans.get(position).getVod_name());

            bb.types.setText("类型：" + listBeans.get(position).getVod_type());
            bb.buqueding.setText("地区：" + listBeans.get(position).getVod_area());
            bb.yer.setText("年代：" + listBeans.get(position).getVod_year());

            bb.rootView.setOnClickListener(v -> {
                p = position;
                try {
                    startActivity(new Intent(mContext, PAYWebActivityTwo.class)
                            .putExtra(KEY.TITLE, listBeans.get(p).getVod_name())
                            .putExtra(KEY.STAR, listBeans.get(p).getVod_stars() + "")
                            .putExtra(KEY.VOD_ID, listBeans.get(p).getVod_id() + "")
                            .putExtra(KEY.IMG_URL, listBeans.get(p).getVod_pic())
                            .putExtra(KEY.video_details, listBeans.get(p).getVod_type() + "  "
                                    + listBeans.get(p).getVod_area() + "  "
                                    + listBeans.get(p).getVod_year() + "  "));
                } catch (Exception e) {
                    ToastUtils.show("此视频暂时无法播放");
                }
            });

        });
        binding.videoListView.setLayoutManager(new LinearLayoutManager(mContext));
        binding.videoListView.setAdapter(videoListAdapter);
        binding.smartRefreshLayout.setEnableRefresh(false);
        binding.smartRefreshLayout.setOnLoadmoreListener(refreshlayout -> {
            refreshlayout.finishLoadmore(1000);
            conter++;
            getSearchData();
        });
        new OkHttpUtils(54, this)
                .SetApiUrl(getvodbystar)
                .POST();

        FuzzySearchAdapter = new PAdapter<>(searchlist, R.layout.search_list_item_layout, (b, position) -> {
            SearchListItemLayoutBinding bb = (SearchListItemLayoutBinding) b;
            if (searchlist.get(position).getVod_name().contains(searchString)) {
                String string = searchlist.get(position).getVod_name();
                String[] aa = string.split(StringUtils.HandlingSpecialCharacters(searchString));
                if (aa.length == 2) {
                    TextViewParser textViewParser = new TextViewParser();
                    textViewParser.append(aa[0], 32, ContextCompat.getColor(mContext, R.color.txt_black));
                    textViewParser.append(searchString, 32, ContextCompat.getColor(mContext, R.color.app_blue));
                    textViewParser.append(aa[1], 32, ContextCompat.getColor(mContext, R.color.txt_black));
                    textViewParser.parse(bb.text);
                } else if (aa.length > 2) {
                    TextViewParser textViewParser = new TextViewParser();
                    textViewParser.append(aa[0], 32, ContextCompat.getColor(mContext, R.color.txt_black));
                    textViewParser.append(searchString, 32, ContextCompat.getColor(mContext, R.color.app_blue));

                    for (int i = 0; i < aa.length; i++) {
                        if (i != 0) {
                            if (i == 1) {
                                textViewParser.append(aa[i], 32, ContextCompat.getColor(mContext, R.color.txt_black));
                            } else {
                                textViewParser.append(searchString + aa[i], 32, ContextCompat.getColor(mContext, R.color.txt_black));
                            }
                        }
                    }

                    textViewParser.parse(bb.text);
                } else if (aa.length == 1) {
                    if (searchlist.get(position).getVod_name().indexOf(searchString) == 0) {
                        TextViewParser textViewParser = new TextViewParser();
                        textViewParser.append(searchString, 32, ContextCompat.getColor(mContext, R.color.app_blue));
                        textViewParser.append(aa[0], 32, ContextCompat.getColor(mContext, R.color.txt_black));
                        textViewParser.parse(bb.text);
                    } else {
                        TextViewParser textViewParser = new TextViewParser();
                        textViewParser.append(aa[0], 32, ContextCompat.getColor(mContext, R.color.txt_black));
                        textViewParser.append(searchString, 32, ContextCompat.getColor(mContext, R.color.app_blue));
                        textViewParser.parse(bb.text);
                    }

                } else {
                    bb.text.setText(searchlist.get(position).getVod_name());
                }

            } else {
                bb.text.setText(searchlist.get(position).getVod_name());
            }

            bb.text.setOnClickListener(v -> {
                IsCkItem = false;
                binding.searchList.setVisibility(View.GONE);
                searchString = searchlist.get(position).getVod_name();
                conter = 1;
                binding.editQuery.setText(searchString);
                getSearchData();

            });
        });
        binding.searchList.setLayoutManager(new LinearLayoutManager(mContext));
        binding.searchList.setAdapter(FuzzySearchAdapter);
    }

    private void ss() {
        if (TextUtils.isEmpty(binding.editQuery.getText() + "")) {
            ToastUtils.show("请输入电影名称");
        } else {
            if (!searchString.equals(binding.editQuery.getText() + "")) {
                searchString = binding.editQuery.getText() + "";
                getSearchData();
            }
        }
    }

    private int p = 0;
    private List<HotMode.ListBean> hotList = new ArrayList<>();
    private List<SearchListMode.ListBean> listBeans = new ArrayList<>();
    private List<SearchList.ListBean> searchlist = new ArrayList<>();

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 41:
                binding.videoListView.setVisibility(View.VISIBLE);
                searchlist.clear();
                FuzzySearchAdapter.notifyDataSetChanged();
                SearchListMode searchListMode = new JsonUtil<SearchListMode>()
                        .json2Bean(jsonString, SearchListMode.class.getName());
                try {
                    if (searchListMode.getList() != null) {
                        listBeans.clear();
                        listBeans.addAll(searchListMode.getList());
                        if (listBeans.size() > 0) {
                            binding.layReMen.setVisibility(View.GONE);
                        }
                        videoListAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.show("未找到资源");
                    }
                } catch (Exception ignored) {
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null;
                imm.showSoftInput(binding.editQuery, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(binding.editQuery.getWindowToken(), 0); //强制隐藏键盘
                break;
            case 54:
                HotMode hotMode = new JsonUtil<HotMode>()
                        .json2Bean(jsonString, HotMode.class.getName());
                if (hotMode.getList() != null) {
                    hotList.clear();
                    hotList.addAll(hotMode.getList());
                    TOP10adapter.notifyDataSetChanged();
                }

                break;
            case 55:
                SearchList list = new JsonUtil<SearchList>().json2Bean(jsonString, SearchList.class.getName());
                if (!TextUtils.isEmpty(searchString)) {
                    searchlist.clear();
                    binding.videoListView.setVisibility(View.GONE);
                    searchlist.addAll(list.getList());
                    FuzzySearchAdapter.notifyDataSetChanged();
                    binding.searchList.setVisibility(View.VISIBLE);
                    binding.layReMen.setVisibility(View.GONE);
                } else {
                    searchlist.clear();
                    binding.videoListView.setVisibility(View.VISIBLE);
                    binding.layReMen.setVisibility(View.VISIBLE);
                    FuzzySearchAdapter.notifyDataSetChanged();
                    binding.searchList.setVisibility(View.GONE);
                    binding.videoListView.setVisibility(View.GONE);
                }

                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        switch (uriType) {
            case 55:
                searchlist.clear();
                FuzzySearchAdapter.notifyDataSetChanged();
                break;
        }
        ToastUtils.show(e);
    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.bt_ss:
                ss();
                break;
        }
    }

    private String searchString = "";

    public void getSearchData() {
        new OkHttpUtils(41, SearchActivity.this)
                .SetApiUrl(search)
                .SetKey("vod_name", "conter")
                .SetValue(searchString + "", conter + "")
                .POST();
    }
}
