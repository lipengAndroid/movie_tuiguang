package com.judian.watch.videos.View.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.LogUtils;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Main.LoginActivity;
import com.judian.watch.videos.databinding.PayWebLayoutThreeBinding;

import org.json.JSONObject;

import java.util.HashMap;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.judian.watch.videos.Http.UrlUtils.URI.h5uri_f;
import static com.judian.watch.videos.Http.UrlUtils.URI.h5uri_h;
import static com.judian.watch.videos.Http.UrlUtils.URI.jiexim3u8;

/**
 * Created by 李鹏 2017/11/29 0029.
 */

public class PAYWebActivityForWeb extends AppCompatActivity implements View.OnClickListener, MyHttpCallBack {
    private Context mContext;
    public PayWebLayoutThreeBinding binding;
    private HashMap<String, String> map = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        binding = DataBindingUtil.setContentView(this, R.layout.pay_web_layout_three);
        map.put("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Mobile Safari/537.36");

        if (!MyApplication.getInstance().isLogin()) {
            ToastUtils.show("请先登录");
            startActivity(new Intent(mContext, LoginActivity.class));
            finish();
            return;
        }
        new OkHttpUtils(4, PAYWebActivityForWeb.this)
                .SetApiUrl(jiexim3u8)
                .SetKey("url")
                .SetValue(getIntent().getStringExtra(KEY.URL))
                .POST();
//        startActivity(new Intent(mContext, TitleWebActivity.class)
//                .putExtra(KEY.TITLE, "111")
//                .putExtra(KEY.URL, h5uri_h+""+h5uri_f));
        initView();


    }


    private void initView() {
        binding.video.setActivity(this);
        binding.video.getPay().setOnClickListener(this);
        binding.video.getBack().setOnClickListener(this);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        binding.video.setPayisv(true);
        binding.video.getPay().setVisibility(GONE);
        binding.video.getProgressBar2().setVisibility(VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                binding.video.getVideoPlayer().release();
                finish();
                break;
            case R.id.pay:
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
                break;
            case R.id.back:
                try {
                    binding.video.getVideoPlayer().release();
                } catch (Exception e) {
                    LogUtils.e(e.getMessage());
                }
                finish();
                break;
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 4:
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    binding.video.getVideoPlayer().stop();
                    binding.video.getVideoPlayer().reset();
                    binding.video.stop();
                    binding.video.getVideoPlayer().setDataSource(mContext, Uri.parse(jsonObject.getString("url")), map);
                    binding.video.getVideoPlayer().prepareAsync();
                } catch (Exception e) {
                    ToastUtils.showCENTER("播放地址获取失败code：0");
                    e.printStackTrace();
                }
                break;

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


    @Override
    public void error(String e, int uriType) {
        switch (uriType) {
            case 4:
                ToastUtils.showCENTER("播放地址获取失败code：1");
                break;
        }
    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {
    }
}

