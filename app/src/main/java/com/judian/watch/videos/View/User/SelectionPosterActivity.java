package com.judian.watch.videos.View.User;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cuihai.library.StatusBarUtils;
import com.judian.watch.videos.DiyView.AlphaPageTransformer;
import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.PosterMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.FileUtils;
import com.judian.watch.videos.Utils.ImgLoadUtils;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Dialog.DialogUtils;
import com.judian.watch.videos.databinding.SelectionPosterActivityBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.judian.watch.videos.Http.UrlUtils.URI.getpostervalue;

/**
 * Created by 李鹏 2017/12/11 0011.
 */

public class SelectionPosterActivity extends BaseActivity implements MyHttpCallBack, View.OnClickListener,
        ViewPager.OnPageChangeListener {
    private SelectionPosterActivityBinding binding;
    private List<View> views = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.selection_poster_activity);
        binding.title.imgBack.setOnClickListener(this);
        binding.ok.setOnClickListener(this);
        binding.title.title.setText(R.string.xqtghb);
        StatusBarUtils.transparencyBar(this);


        new OkHttpUtils(91, this)
                .SetApiUrl(getpostervalue)
                .SetKey("unionid")
                .SetValue(MyApplication.getInstance().getUser().getData().getUnionid())
                .POST();


    }

    private PosterMode mode;

    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 91:
                mode = new JsonUtil<PosterMode>()
                        .json2Bean(jsonString, PosterMode.class.getName());
                for (int i = 0; i < mode.getList().size(); i++) {
                    View ss = View.inflate(mContext, R.layout.selection_poster_activity_item_two, null);
                    TextView detailss = ss.findViewById(R.id.tv_details);
                    ImageView aa = ss.findViewById(R.id.img);
                    ImageView qrimg = ss.findViewById(R.id.rqimg);
                    TextView details = ss.findViewById(R.id.wx);
                    detailss.setText(mode.getList().get(i).getContent());
                    ImgLoadUtils.init(mContext)
                            .Uri(mode.getList().get(i).getPosterurl())
                            .Show(aa);
                    ImgLoadUtils.init(mContext)
                            .Uri(mode.getQrcode())
                            .Show(qrimg);
                    details.setText(mode.getWxaccount());
                    views.add(ss);
                }
                //设置page间距
                binding.viewPager.setPageMargin(20);
                binding.viewPager.setOffscreenPageLimit(3);

                binding.viewPager.setPageTransformer(false, new AlphaPageTransformer());

                //设置子view

                binding.viewPager.setAdapter(new MyAdapter());
                binding.viewPager.setCurrentItem(1);

                binding.viewPager.addOnPageChangeListener(this);

                Glide.with(mContext)
                        .load(mode.getList().get(binding.viewPager.getCurrentItem()).getPosterurl())
                        .into(binding.item.img);
                Glide.with(mContext)
                        .load(mode.getQrcode())
                        .into(binding.item.rqimg);
                binding.item.wx.setText(mode.getWxaccount());
                break;
            case 31:
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String url = jsonObject.getString("path");
                    new OkHttpUtils(999, this)
                            .SetApiUrl(url)
                            .Download();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 999:
                FileUtils.saveImageToSystem(mContext, jsonString);
                ToastUtils.show("海报以保存到相册~");
                break;

        }
    }

    @Override
    public void error(String e, int uriType) {

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
            case R.id.ok:
                ToastUtils.show(getString(R.string.scz));
                if (views.size() != 0) {
                    new Thread(() -> {
                        try {
                            Thread.sleep(2000);
                            handler.sendEmptyMessage(9);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } else {
                    ToastUtils.show(getString(R.string.hbzzgldlsqshzs));
                }

                break;
        }
    }

    private Bitmap bitmaps;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 9:
                    int w = binding.item.rootView.getWidth();
                    int h = binding.item.rootView.getHeight();
                    Bitmap bitmap;
                    bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    binding.item.rootView.draw(canvas);
                    bitmaps = bitmap;

                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED &&
                            ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)
                                    == PackageManager.PERMISSION_DENIED) {
                        ActivityCompat.requestPermissions(SelectionPosterActivity.this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 994);
                    } else {
                        FileUtils.saveImageToGallery(mContext, bitmap);
                        ToastUtils.show(getString(R.string.hbybczxc));
                    }

                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isok = true;
        if (requestCode == 994) {
            for (int i : grantResults) {
                if (i != PackageManager.PERMISSION_GRANTED) {
                    isok = false;
                }
            }
            if (isok) {
                FileUtils.saveImageToGallery(mContext, bitmaps);
                ToastUtils.show(getString(R.string.hbybczxc));
            } else {
                new DialogUtils().init(mContext)
                        .setTitle(getString(R.string.byyqxhbjwfbczxc))
                        .setOne(getString(R.string.ty), ButtType -> ActivityCompat.requestPermissions(SelectionPosterActivity.this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 994)).setTwo(getString(R.string.bty), ButtType -> new DialogUtils().init(mContext).setTitle(getString(R.string.hbybczrjml))
                        .setOne(getString(R.string.qd), ButtType1 -> new DialogUtils().init(mContext)
                                .setTitle(getString(R.string.tpdz) + FileUtils.saveImageToApp(bitmaps))
                                .setOne(getString(R.string.gb), ButtType11 -> {

                                })).isTouchcCancel(true));
            }
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:     //暂停状态
                break;
            case ViewPager.SCROLL_STATE_SETTLING:   //滑动结束
                Glide.with(mContext)
                        .load(mode.getList().get(binding.viewPager.getCurrentItem()).getPosterurl())
                        .into(binding.item.img);
                Glide.with(mContext)
                        .load(mode.getQrcode())
                        .into(binding.item.rqimg);

                binding.item.wx.setText(mode.getWxaccount());
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:   //拖动中
                break;
        }
    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(views.get(position));
        }
    }
}
