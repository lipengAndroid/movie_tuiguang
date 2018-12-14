package com.judian.watch.videos.View.User;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.cuihai.library.StatusBarUtils;
import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.Http.ImgChoiceUtils;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Mode.ShareMode;
import com.judian.watch.videos.Mode.WxNumAndQrImgMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.FileUtils;
import com.judian.watch.videos.Utils.ImgLoadUtils;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.Utils.WxShareUtils;
import com.judian.watch.videos.View.Dialog.DialogUtils;
import com.judian.watch.videos.databinding.AddCodeActivityBinding;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static com.judian.watch.videos.Http.UrlUtils.URI.backinformation;
import static com.judian.watch.videos.Http.UrlUtils.URI.createvoucher;
import static com.judian.watch.videos.Http.UrlUtils.URI.savewxaccount;
import static com.judian.watch.videos.Http.UrlUtils.URI.searchproxyinfo;

/**
 * Created by 李鹏 2017/12/11 0011.
 */

public class AddCodeActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack {
    private AddCodeActivityBinding binding;
    private ImgChoiceUtils utils;
    private boolean codeiSoK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.add_code_activity);

        StatusBarUtils.transparencyBar(this);
        binding.title.title.setText(R.string.scdhm);
        binding.title.imgBack.setOnClickListener(this);
        binding.copy.setOnClickListener(this);
        binding.layUpdateQrImg.setOnClickListener(this);
        binding.shenchenCode.setOnClickListener(this);
        binding.tvSubmit.setOnClickListener(this);
        binding.tvShareAppUri.setOnClickListener(this);
        utils = ImgChoiceUtils.isNew(this);

        new OkHttpUtils(998, this)
                .SetApiUrl(searchproxyinfo)
                .SetKey("unionid")
                .SetValue(MyApplication.getInstance().getUser().getData().getUnionid())
                .POST();

    }

    private String imgUrl;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.lay_update_qr_img:
                utils.show(imgUrls -> {
                    imgUrl = imgUrls;
                    ImgLoadUtils.init(mContext)
                            .Uri(imgUrl)
                            .Show(binding.imgQr);
                });
                break;
            case R.id.tv_submit:
                if (TextUtils.isEmpty(binding.edWxNum.getText() + "")) {
                    ToastUtils.show(getString(R.string.qtxwxh));
                    return;
                }
                if (TextUtils.isEmpty(imgUrl)) {
                    ToastUtils.show(getString(R.string.qscrwm));
                    return;
                }
                new OkHttpUtils(21, this)
                        .SetApiUrl(savewxaccount)
                        .SetKey("unionid", "wxaccount")
                        .SetValue(MyApplication.getInstance().getUser().getData().getUnionid(), binding.edWxNum.getText() + "")
                        .POST();

                startActivity(new Intent(mContext, SelectionPosterActivity.class));
                break;
            case R.id.shenchen_code:
                if (!codeiSoK) {
                    new OkHttpUtils(99, this)
                            .SetApiUrl(createvoucher)
                            .SetKey("unionid")
                            .SetValue(MyApplication.getInstance().getUser().getData().getUnionid())
                            .POST();
                    binding.copy.setText(R.string.hz);
                    binding.copy.setBackgroundResource(R.drawable.bg_green_fillet);
                } else {
                    ToastUtils.show(getString(R.string.dhmyscqwcfsc));
                }
                break;
            case R.id.copy:
                if (!TextUtils.isEmpty(binding.myCode.getText().toString())) {

                    ClipboardManager mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData mClipData = ClipData.newPlainText("Simple test", binding.myCode.getText() + "");
                    assert mClipboardManager != null;
                    mClipboardManager.setPrimaryClip(mClipData);
                    binding.copy.setText(R.string.yhz);
                    codeiSoK = false;
                    binding.copy.setBackgroundResource(R.drawable.bg_hui_yuan);
                } else {
                    ToastUtils.show(getString(R.string.qxscdhm));
                }
                break;
            case R.id.tv_share_app_uri:
                new OkHttpUtils(1000, this)
                        .SetApiUrl(backinformation)
                        .SetKey("type", "unionid")
                        .SetValue("2", MyApplication.getInstance().getUser().getData().getUnionid())
                        .POST();

                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        utils.onPermissions(requestCode, grantResults);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 99:
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    String code = jsonObject.getString("code");
                    binding.myCode.setText(code);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                codeiSoK = true;

                break;
            case 998:
                WxNumAndQrImgMode mode = new JsonUtil<WxNumAndQrImgMode>()
                        .json2Bean(jsonString, WxNumAndQrImgMode.class.getName());
                imgUrl = mode.getQrcode();
                binding.tvShareAppUri.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(mode.getQrcode())) {
                    ImgLoadUtils.init(mContext)
                            .Uri(mode.getQrcode())
                            .Show(binding.imgQr);
                }
                if (!TextUtils.isEmpty(mode.getWxaccount())) {
                    binding.edWxNum.setText(mode.getWxaccount() + "");
                }

                break;
            case 1000:
                shareMode = new JsonUtil<ShareMode>().json2Bean(jsonString, ShareMode.class.getName());
                new Thread(() -> {
                    try {
                        try {
//                            bitmap = FileUtils.drawable2Bitmap(Glide.with(MyApplication.getInstance())
//                                    .load(shareMode.getImageurl())
//                                    .asBitmap() //必须
//                                    .centerCrop()
//                                    .get());
                            bitmap = Glide.with(mContext)
                                    .load(shareMode.getImageurl())
                                    .asBitmap() //必须
                                    .centerCrop()
                                    .into(80, 80)
                                    .get();
                            while (true) {
                                if (bitmap != null) {
                                    handler.sendEmptyMessage(99);
                                    break;
                                }
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();

                break;
        }
    }

    private ShareMode shareMode;
    private Bitmap bitmap;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 99:
                    new DialogUtils().init(mContext)
                            .setTitle(getString(R.string.fxd))
                            .setOne(getString(R.string.wxhy), ButtType -> {
                                MyApplication.getInstance().setWxLogin(false);
                                WxShareUtils.sendWxWeb(mContext, bitmap, shareMode.getUrl(),
                                        shareMode.getTitle(), shareMode.getContent(),
                                        SendMessageToWX.Req.WXSceneSession);
                            }).setTwo(getString(R.string.pyq), ButtType -> {
                        MyApplication.getInstance().setWxLogin(false);
                        WxShareUtils.sendWxWeb(mContext, bitmap, shareMode.getUrl(),
                                shareMode.getTitle(), shareMode.getContent(),
                                SendMessageToWX.Req.WXSceneTimeline);
                    });
                    break;
            }
        }
    };

    @Override
    public void error(String e, int uriType) {
        if (uriType != 21) {
            ToastUtils.show(e);
        }

    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        utils.onActivityResult(requestCode, resultCode, data);
    }
}
