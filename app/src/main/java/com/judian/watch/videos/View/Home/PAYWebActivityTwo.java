package com.judian.watch.videos.View.Home;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.DiyView.RV.PAdapter;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.GoodsAdInfo;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Mode.VideoPlayadressMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.LogUtils;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.PlayInfoMode;
import com.judian.watch.videos.Utils.PreferencesUtil;
import com.judian.watch.videos.Utils.StringUtils;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Dialog.ListDialog;
import com.judian.watch.videos.View.Main.LoginActivity;
import com.judian.watch.videos.databinding.JishuListItemBinding;
import com.judian.watch.videos.databinding.PayWebLayoutTwoBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.judian.watch.videos.Http.UrlUtils.URI.appplayad;
import static com.judian.watch.videos.Http.UrlUtils.URI.getPlayUrl;
import static com.judian.watch.videos.Http.UrlUtils.URI.h5uri_f;
import static com.judian.watch.videos.Http.UrlUtils.URI.h5uri_h;
import static com.judian.watch.videos.Mode.KEY.VOD_ID;

/**
 * Created by 李鹏 2017/11/29 0029.
 */

public class PAYWebActivityTwo extends BaseActivity implements View.OnClickListener, MyHttpCallBack {

    public PayWebLayoutTwoBinding binding;
    private HashMap<String, String> map = new HashMap<>();
    private String payuri;//播放地址
    private boolean isJieXi = false;// 判断uri 是不是解析过得uri
    private ViewGroup.LayoutParams mVideoViewLayoutParams;
    private Intent intent;
    private boolean isToWeb = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.pay_web_layout_two);
        map.put("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Mobile Safari/537.36");

        initView();
//        initAdView();
        intent = new Intent(mContext, TXWebActivity.class);

        new OkHttpUtils(1, this)
                .SetApiUrl(getPlayUrl + getIntent().getStringExtra(VOD_ID))
                .GET();
        binding.video.getPay().setOnClickListener(v -> {
            if (!MyApplication.getInstance().isLogin()) {
                ToastUtils.show("请先登录");
                startActivity(new Intent(mContext, LoginActivity.class));
                return;
            }
            if (MyApplication.getInstance().getUser().getData().getGuoqi() != 1) {
                ToastUtils.show("您的VIP已过期");
                return;
            }

            if (TextUtils.isEmpty(payuri)) {
                ToastUtils.show("数据获取中，请稍后再试");
                return;
            }
            binding.video.setPayisv(true);
            if (payuri.contains(".m3u8") ||
                    payuri.contains(".mp4")
                    || payuri.contains(".MP4")
                    || payuri.contains(".avi")
                    || payuri.contains(".rmvb")
                    || isJieXi) {
                binding.video.getPay().setVisibility(GONE);
                if (binding.video.getVideoPlayer().isPlaying()) {
                    binding.video.getVideoPlayer().pause();//暂停播放
                    ToastUtils.show("暂停");
                    binding.video.getPay().setImageResource(R.drawable.ic_pay);
                } else {
                    binding.video.getProgressBar2().setVisibility(VISIBLE);
                    binding.video.getPay().setImageResource(R.drawable.ic_post);
                    ToastUtils.show("播放");
                    if (binding.video.isIspaying()) {
                        binding.video.getVideoPlayer().start();
                    } else {
                        try {
                            binding.video.getVideoPlayer().prepareAsync();
                        } catch (Exception ignored) {
                        }
                    }
                }
            } else {

//                binding.video.getProgressBar2().setVisibility(VISIBLE);
//                new OkHttpUtils(4, PAYWebActivityTwo.this)
//                        .SetApiUrl(jiexim3u8)
//                        .SetKey("url")
//                        .SetValue(payuri)
//                        .POST();
                binding.video.setPayisv(false);
                startActivity(new Intent(mContext, TitleWebActivity.class)
                        .putExtra(KEY.TITLE, getIntent().getStringExtra(KEY.TITLE))
                        .putExtra(KEY.URL, h5uri_h + getIntent().getStringExtra(VOD_ID) + h5uri_f));
            }
        });


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        binding.video.getVideoPlayer().setOnErrorListener((mp, what, extra) -> {
            if (p < VideoSourceString.size()) {
                try {
                    binding.video.getTitle().setText("来源：" + VideoSourceString.get(p++) + "   " + urls.get(p++));
                    binding.video.getVideoPlayer().stop();
                    binding.video.getVideoPlayer().reset();
                    binding.video.stop();
                    binding.video.ViewVisible();
                    binding.video.getImageView().setVisibility(VISIBLE);
                    adapter.notifyDataSetChanged();
                    ToastUtils.show("播放失败！播放源切换为" + VideoSourceString.get(p++));
                    payuri = lists.get(1).get(p++);
                    binding.video.getVideoPlayer().setDataSource(mContext, Uri.parse(payuri), map);
                } catch (Exception we) {
                }

            } else {
//                ToastUtils.show("播放失败，请稍后重试");
            }

            return false;
        });

        binding.video.getToWeb().setOnClickListener(v -> {
            if (!MyApplication.getInstance().isLogin()) {
                ToastUtils.show("请先登录");
                startActivity(new Intent(mContext, LoginActivity.class));
                return;
            }
            if (MyApplication.getInstance().getUser().getData().getGuoqi() != 1) {
                ToastUtils.show("您的VIP已过期");
                return;
            }

            for (int i = 0; i < data.size(); i++) {
                if (intent.getStringExtra(KEY.URL).contains(data.get(i).getHttp())) {
                    if (data.get(i).getTypestatus() == 0) {
                        intent.putExtra(KEY.DISPLAY_SCREEN_VIEW, "1");
                    }
                }
            }

            startActivity(intent);
        });

        binding.video.getBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    binding.video.getVideoPlayer().release();
                } catch (Exception e) {
                    LogUtils.e(e.getMessage());
                }

//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
                finish();
            }
        });
    }

    private void initAdView() {
        new OkHttpUtils(3, this)//广告
                .SetApiUrl(appplayad)
                .GET();
    }

    private void initView() {
        binding.video.setActivity(this);
        binding.top.name.setText(getIntent().getStringExtra(KEY.TITLE));
        binding.top.type.setText(getIntent().getStringExtra(KEY.video_details));
        binding.top.type.setVisibility(VISIBLE);
//        binding.shareLayout.share.setOnClickListener(this);
        binding.top.lineLay.setOnClickListener(this);
        binding.top.btJuqing.setOnClickListener(this);
//        binding.video.getPay().setVisibility(GONE);
        binding.video.getToWebLay().setVisibility(GONE);
        binding.top.details.setVisibility(VISIBLE);
        binding.top.recyclerView.setNestedScrollingEnabled(false);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int rot = getWindowManager().getDefaultDisplay().getRotation();

        if (rot == Surface.ROTATION_90 || rot == Surface.ROTATION_270) {
            mVideoViewLayoutParams = binding.rootView.getLayoutParams();
            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            binding.rootView.setLayoutParams(layoutParams);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else if (rot == Surface.ROTATION_0) {
            if (mVideoViewLayoutParams != null) {
                binding.rootView.setLayoutParams(mVideoViewLayoutParams);
            }

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.line_lay:
                if (UrlTagsInfo.size() == 0) {
                    return;
                }

                new ListDialog().show(mContext, VideoSourceString, position -> {
                    binding.top.lineRod.setText(VideoSourceString.get(position));
                    lists.clear();
                    lists.addAll(StringUtils.getUrls(UrlTagsInfo.get(position)));
                    titles.clear();
                    urls.clear();
                    titles.addAll(lists.get(0));
                    urls.addAll(lists.get(1));
                    nock = true;
                    isJieXi = false;
                    binding.video.getTitle().setText("来源：" + VideoSourceString.get(position) + "   " + urls.get(0));
                    binding.video.getVideoPlayer().stop();
                    binding.video.getVideoPlayer().reset();
                    binding.video.stop();
                    binding.video.ViewVisible();
                    binding.video.getImageView().setVisibility(VISIBLE);
                    adapter.notifyDataSetChanged();
                    ToastUtils.show("播放源切换为" + VideoSourceString.get(position));

                    binding.video.getProgressBar2().setVisibility(GONE);
                    payuri = lists.get(1).get(0);
                    for (int i = 0; i < data.size(); i++) {
                        if (lists.get(1).get(0).contains(data.get(i).getHttp())) {
                            binding.video.getPay().setVisibility(GONE);
                            binding.video.getToWebLay().setVisibility(VISIBLE);
                            binding.rootView.setBackgroundResource(R.drawable.video_bg);
                            if (urls.get(0).contains("http") && !urls.get(0).contains("https")) {
                                urls.get(0).replace("http", "https");
                            }
                            intent.putExtra(KEY.TITLE, VideoSourceString.get(position))
                                    .putExtra(KEY.URL, urls.get(0))
                                    .putExtra(KEY.TYPE, "1");
                            binding.video.setPayisv(false);
                            isToWeb = true;
                            break;
                        } else {
                            isToWeb = false;
                        }
//                        LogUtils.e(lists.get(1).get(0) + "____" + data.get(i).getHttp());
                    }
                    if (!isToWeb) {
                        binding.video.setPayisv(true);
                        binding.video.getPay().setVisibility(VISIBLE);
                        binding.rootView.setBackground(null);
                        binding.video.getToWebLay().setVisibility(GONE);
                    }
                });


                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.bt_juqing:

                Drawable drawable;
                if (binding.top.details.getVisibility() == VISIBLE) {
                    binding.top.details.setVisibility(GONE);
                    drawable = getResources().getDrawable(R.drawable.ic_down);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getMinimumHeight());

                } else {
                    drawable = getResources().getDrawable(R.drawable.ic_up_hui);
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getMinimumHeight());
                    binding.top.details.setVisibility(VISIBLE);
                }

                binding.top.btJuqing.setCompoundDrawables(null, null, drawable, null);
                break;
        }
    }


    private List<String> UrlTagsInfo = new ArrayList<>();
    private int p = 0;
    private boolean nock = true;
    private PAdapter<String> adapter;
    private List<String> titles = new ArrayList<>();
    private List<String> urls = new ArrayList<>();

    private List<List<String>> lists = new ArrayList<>();
    private List<String> VideoSourceString = new ArrayList<>();// 线路

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 1:
                PlayInfoMode infoMode = new JsonUtil<PlayInfoMode>()
                        .json2Bean(jsonString, PlayInfoMode.class.getName());
                if (TextUtils.isEmpty(infoMode.getResult().getVod_type())) {
                    binding.top.type.setText("主演：" + infoMode.getResult().getVod_actor() + "\n"
                            + infoMode.getResult().getVod_area() + "    " + infoMode.getResult().getVod_year());
                } else {
                    binding.top.type.setText("主演：" + infoMode.getResult().getVod_actor() + "\n"
                            + infoMode.getResult().getVod_type() + "    " +
                            infoMode.getResult().getVod_area() + "    " + infoMode.getResult().getVod_year());
                }

                binding.top.details.setText("      " + infoMode.getResult().getVod_content());


                if (!TextUtils.isEmpty(infoMode.getResult().getVod_url())) {
                    if (!infoMode.getResult().getVod_url().contains("$$$")) {
                        lists.addAll(StringUtils.getUrls(infoMode.getResult().getVod_url()));
                    } else {
                        Collections.addAll(UrlTagsInfo, infoMode.getResult().getVod_url().split("\\$\\$\\$"));
                    }
                }
                List<String> VideoSourceStrings = new ArrayList<>();
                if (infoMode.getResult().getVod_play().contains("$$$")) {
                    VideoSourceStrings.addAll(Arrays.asList(infoMode.getResult().getVod_play().split("\\$\\$\\$")));
                } else {
                    VideoSourceStrings.add(infoMode.getResult().getVod_play());
                }

                for (int a = 0; a < VideoSourceStrings.size(); a++) {
                    String b = StringUtils.initTitleData(VideoSourceStrings.get(a));
                    if (b != null) {
                        VideoSourceString.add(b);
                    } else {
                        UrlTagsInfo.remove(a);
                    }
                }
                if (UrlTagsInfo != null && UrlTagsInfo.size() > 0) {
                    lists.clear();
                    lists.addAll(StringUtils.getUrls(UrlTagsInfo.get(0)));
                }

                try {
                    payuri = lists.get(1).get(0);
                    binding.video.getVideoPlayer().setDataSource(mContext, Uri.parse(payuri), map);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                initListView();


                binding.top.lineRod.setText(VideoSourceString.get(0));
                binding.video.getTitle().setText("来源：" + urls.get(0));
                if (urls.get(0).contains("http") && !urls.get(0).contains("https")) {

                    urls.get(0).replace("http", "https");
                }
                intent.putExtra(KEY.TITLE, VideoSourceString.get(0))
                        .putExtra(KEY.URL, payuri)
                        .putExtra(KEY.TYPE, "1");
//                new OkHttpUtils(2, this)
//                        .SetApiUrl(getvideoplayadress)
//                        .GET();
//                startActivity(new Intent(mContext, TXWebActivity.class)
//                        .putExtra(KEY.TITLE, VideoSourceString.get(0))
//                        .putExtra(KEY.URL, payuri));
//                finish();
                break;

            case 3:
                GoodsAdInfo info = new JsonUtil<GoodsAdInfo>()
                        .json2Bean(jsonString, GoodsAdInfo.class.getName());
                if (info.getResult().getStatus() == 1) {
                    binding.top.img.setVisibility(VISIBLE);
//                    String url = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1511093235,3069958387&fm=27&gp=0.jpg";
//                    String gif = "http://img.zcool.cn/community/01c6e25889bd4ca8012060c80f8067.gif";
//                    ImgLoadUtils.init(mContext)
//                            .Uri(gif)
//                            .Show(binding.top.img);

                    Glide.with(mContext).load(info.getResult().getAdimg()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new BitmapImageViewTarget(binding.top.img) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            super.setResource(resource);
                            int width = resource.getWidth();
                            int height = resource.getHeight();
                            int imageViewWidth = binding.top.img.getWidth();
                            float sy = (float) (imageViewWidth * 0.1) / (float) (width * 0.1);
                            int imageViewHeight = (int) (height * sy);
                            ViewGroup.LayoutParams params = binding.top.img.getLayoutParams();
                            params.height = imageViewHeight;
                            binding.top.img.setLayoutParams(params);
                        }
                    });


                    binding.top.img.setOnClickListener(v ->
                            startActivity(new Intent(mContext, TitleWebActivity.class)
                                    .putExtra(KEY.TITLE, "广告")
                                    .putExtra(KEY.URL, info.getResult().getAdurl())));
                }
                break;
//            case 2:
//                VideoPlayadressMode videoPlayadressMode = new JsonUtil<VideoPlayadressMode>().json2Bean(jsonString,
//                        VideoPlayadressMode.class.getName());
//                data.clear();
//                data.addAll(videoPlayadressMode.getData());
//
//                for (int i = 0; i < data.size(); i++) {
//                    if (lists.get(1).get(p).contains(data.get(i).getHttp())) {
//                        binding.video.getPay().setVisibility(GONE);
//                        binding.video.getToWebLay().setVisibility(VISIBLE);
//                        binding.rootView.setBackgroundResource(R.drawable.video_bg);
//                        binding.video.setPayisv(false);
//                        isToWeb = true;
//                    }
//                }
//                if (!isToWeb) {
//                    binding.video.setPayisv(true);
//                    binding.video.getPay().setVisibility(VISIBLE);
//                    binding.rootView.setBackground(null);
//                    binding.video.getToWebLay().setVisibility(GONE);
//                    isToWeb = false;
//                }

//                break;
            case 4:
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    isJieXi = true;
                    binding.video.getVideoPlayer().stop();
                    binding.video.getVideoPlayer().reset();
                    binding.video.stop();
                    binding.video.getVideoPlayer().setDataSource(mContext, Uri.parse(jsonObject.getString("url")), map);
                    binding.video.getVideoPlayer().prepareAsync();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
//            case 5:
//                try {
//                    vip = new JSONObject(jsonString).getString("vip");
////                    binding.shareLayout.nnn.setText("该影片需要分享后才可看\n分享后可得" + vip + "天VIP");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case 6:
//                shareMode = new JsonUtil<ShareMode>().json2Bean(jsonString, ShareMode.class.getName());
//
//                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            binding.video.stop();
            binding.video.getVideoPlayer().release();
            binding = null;
        } catch (Exception ignored) {
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (binding.video.getVideoPlayer().isPlaying()) {
                binding.video.getVideoPlayer().pause();
                binding.video.getPay().setImageResource(R.drawable.ic_pay);
            }
        } catch (Exception ignored) {
        }


    }

    //    private Bitmap bitmap;
    private List<VideoPlayadressMode.DataBean> data = new ArrayList<>();

    private void initListView() {
        titles.addAll(lists.get(0));
        urls.addAll(lists.get(1));
        binding.top.rootView.setVisibility(VISIBLE);
        binding.top.rootViewTwo.setVisibility(VISIBLE);
        adapter = new PAdapter<>(titles, R.layout.jishu_list_item, (bb, position) -> {
            JishuListItemBinding jishu = (JishuListItemBinding) bb;
            jishu.title.setText(titles.get(position));
            if (nock) {
                if (position == 0)
                    setButtonBlue(jishu.title);
                else
                    setHuiBlue(jishu.title);

            } else {
                if (position == p)
                    setButtonBlue(jishu.title);
                else
                    setHuiBlue(jishu.title);
            }
            /*
             * 切换集数
             */
            jishu.title.setOnClickListener(v -> {

                isJieXi = false;
                if (p == position) {
                    return;
                }
                adapter.notifyDataSetChanged();
                p = position;
                nock = false;
                try {
                    payuri = lists.get(1).get(position);
                    binding.video.ChangeUri();
                    binding.video.getVideoPlayer().setDataSource(mContext, Uri.parse(payuri), map);
                    for (int i = 0; i < data.size(); i++) {
                        if (payuri.contains(data.get(i).getHttp())) {
                            binding.video.getPay().setVisibility(GONE);
                            binding.video.getToWebLay().setVisibility(VISIBLE);
                            binding.rootView.setBackgroundResource(R.drawable.video_bg);
                            if (urls.get(0).contains("http") && !urls.get(0).contains("https")) {
                                urls.get(0).replace("http", "https");
                            }
                            intent.putExtra(KEY.TITLE, VideoSourceString.get(position))
                                    .putExtra(KEY.URL, payuri)
                                    .putExtra(KEY.TYPE, "1");
                            binding.video.setPayisv(false);
                            isToWeb = true;
                            break;
                        } else {
                            isToWeb = false;
                        }
//                        LogUtils.e(lists.get(1).get(0) + "____" + data.get(i).getHttp());
                    }
                    if (!isToWeb) {
                        binding.video.setPayisv(true);
                        binding.video.getPay().setVisibility(VISIBLE);
                        binding.rootView.setBackground(null);
                        binding.video.getToWebLay().setVisibility(GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.show("error_code589");
                }
                ToastUtils.show(titles.get(position));
            });

        });
        binding.top.recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        if (!getIntent().hasExtra(KEY.IS_DIAN_YING)) {
            binding.top.recyclerView.setAdapter(adapter);
            binding.top.j.setVisibility(VISIBLE);
        }
    }

    private void setButtonBlue(TextView textView) {
        textView.setBackgroundResource(R.drawable.bg_blue_yuan_6dp);
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
    }

    private void setHuiBlue(TextView textView) {
        textView.setBackgroundResource(R.drawable.bg_hui_yuan);
        textView.setTextColor(ContextCompat.getColor(mContext, R.color.txt_black));
    }

    @Override
    public void error(String e, int uriType) {
        switch (uriType) {
            case 4:
                ToastUtils.show("资源不存在，请切换资源");
                break;
        }
    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }

    @Override
    public void onBackPressed() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制为竖屏
        } else {
            super.onBackPressed();
        }
    }
}

