package com.judian.watch.videos.View.User;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Http.UrlUtils;
import com.judian.watch.videos.Interface.FragmentToActivityCallBack;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Interface.getUserInterFace;
import com.judian.watch.videos.Mode.KEY;
import com.judian.watch.videos.Mode.UserInfoMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.GetUserInfo;
import com.judian.watch.videos.Utils.ImgLoadUtils;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.PreferencesUtil;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.View.Dialog.ClearDialog;
import com.judian.watch.videos.View.Dialog.DialogUtils;
import com.judian.watch.videos.View.Dialog.ExchangeDialog;
import com.judian.watch.videos.View.Home.ShareWebActivity;
import com.judian.watch.videos.View.Main.LoginActivity;
import com.judian.watch.videos.databinding.UserFragmentBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.judian.watch.videos.Http.UrlUtils.URI.problem;
import static com.judian.watch.videos.Http.UrlUtils.URI.share_app;
import static com.judian.watch.videos.Http.UrlUtils.URI.usevoucher;
import static com.judian.watch.videos.Mode.KEY.USER_INFO;
import static com.judian.watch.videos.R.string.fxyj;

/**
 * Created by 李鹏 2017/11/28 0028.
 */

public class UserFragment extends Fragment implements View.OnClickListener, MyHttpCallBack {
    private UserFragmentBinding binding;

    private int[] imgs = new int[]{
            R.drawable.phone,
            R.drawable.gywm,
            R.drawable.qkhc,
            R.drawable.jcgx,
            R.drawable.cjwt,
            R.drawable.jyfk,
            R.drawable.scdgj};
    private int[] txts = new int[]{
            R.string.bdsj,
            R.string.gywm,
            R.string.qkhc,
            R.string.jxgx,
            R.string.cjwt,
            R.string.jyfk,
            R.string.scdhm};
    private Context mContext;

    private FragmentToActivityCallBack mainActivity;


    @Override
    public void onResume() {
        super.onResume();
        if (binding != null) {
            initViews();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.user_fragment, container, false);
            mContext = getActivity();
            mainActivity = (FragmentToActivityCallBack) getActivity();

            binding.layOne.linearLayout.setOnClickListener(this);
            binding.layOne.linearLayout.setTag(1);
            binding.layOne.imgIcon.setImageResource(imgs[1]);
            binding.layOne.txt.setText(txts[1]);

            binding.layTwo.linearLayout.setOnClickListener(this);
            binding.layTwo.linearLayout.setTag(2);
            binding.layTwo.imgIcon.setImageResource(imgs[2]);
            binding.layTwo.txt.setText(txts[2]);

            binding.layThree.linearLayout.setOnClickListener(this);
            binding.layThree.linearLayout.setTag(3);
            binding.layThree.imgIcon.setImageResource(imgs[3]);
            binding.layThree.txt.setText(txts[3]);

            binding.layFour.linearLayout.setOnClickListener(this);
            binding.layFour.linearLayout.setTag(4);
            binding.layFour.imgIcon.setImageResource(imgs[4]);
            binding.layFour.imgIcon.setPadding(2, 2, 2, 2);
            binding.layFour.txt.setText(txts[4]);

            binding.layFiv.linearLayout.setOnClickListener(this);
            binding.layFiv.linearLayout.setTag(5);
            binding.layFiv.imgIcon.setImageResource(imgs[5]);
            binding.layFiv.txt.setText(txts[5]);

//            binding.laySix.linearLayout.setOnClickListener(this);
//            binding.laySix.linearLayout.setTag(6);
//            binding.laySix.imgIcon.setImageResource(imgs[6]);
//            binding.laySix.txt.setText(txts[6]);

            binding.loginOut.setOnClickListener(this);

            binding.coupon.setOnClickListener(this);
            binding.vip.setOnClickListener(this);
            binding.share.setOnClickListener(this);
            binding.constraintLayout.setOnClickListener(this);
            initViews();

            binding.smal.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    refreshlayout.finishRefresh(1000);
                    new OkHttpUtils(1, UserFragment.this)
                            .SetApiUrl(UrlUtils.URI.getmyself)
                            .SetKey("unionid")
                            .SetValue(MyApplication.getInstance().getUser().getData().getUnionid())
                            .POST();
                }
            });
        }

        return binding.getRoot();
    }

    private void initViews() {

        if (MyApplication.getInstance().isLogin()) {
            if (!TextUtils.isEmpty(PreferencesUtil.getString(KEY.IS_ED_FIRST_CODE))
                    && PreferencesUtil.getString(KEY.IS_ED_FIRST_CODE).equals("0")) {
                return;
            }
            binding.loginOut.setVisibility(View.VISIBLE);


            Glide.with(mContext)
                    .load(MyApplication.getInstance()
                            .getUser().getData().getImageurl())
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .thumbnail(0.9f)
                    .transform(new ImgLoadUtils.GlideRoundTransform(mContext, 120))
                    .crossFade()
                    .into(binding.imgUserHoder);


            binding.userName.setText(MyApplication.getInstance().getUser().getData().getNickname());

            String a = MyApplication.getInstance().getUser().getData().getVip() + " 23:59:59";
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = format.parse(a);
                Date date2 = new Date();
                if (date2.getTime() > date.getTime()) {
                    binding.vipEndTime.setText("VIP: 已过期");
                } else {
                    binding.vipEndTime.setText("VIP: " + MyApplication.getInstance().getUser().getData().getVip() + "日到期");
                }
            } catch (ParseException ignored) {
            }
            binding.id.setText("邀请码: " + MyApplication.getInstance().getUser().getData().getId());

//            if (MyApplication.getInstance().getUser().getData().getPstatus() == 1) {
//                binding.laySix.layList.setVisibility(View.VISIBLE);
//            } else {
//                binding.laySix.layList.setVisibility(View.GONE);
//            }
        } else {
            binding.loginOut.setVisibility(View.GONE);
            binding.vipEndTime.setText("点击登录");
            binding.id.setText("");
            binding.loginOut.setVisibility(View.GONE);
            binding.userName.setText("");
            binding.imgUserHoder.setImageResource(R.drawable.user_default);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.constraintLayout:
                if (!MyApplication.getInstance().isLogin()) {
                    startActivityForResult(new Intent(mContext, LoginActivity.class), 123);
                }
                break;
            case R.id.vip:
                MystartActivity(new Intent(mContext, MemberCenterActivity.class));
                break;
            case R.id.share:
                MystartActivity(new Intent(mContext, ShareWebActivity.class)
                        .putExtra(KEY.TITLE, "分享有奖")
                        .putExtra(KEY.URL, share_app)
                        .putExtra(KEY.TYPE, "1"));
                break;
            case R.id.coupon:
//                if (MyApplication.getInstance().isLogin()) {
//                    ExchangeDialog.show(mContext, str -> new OkHttpUtils(5155, UserFragment.this)
//                            .SetApiUrl(usevoucher)
//                            .SetKey("unionid", "code")
//                            .SetValue(MyApplication.getInstance().getUser().getData().getUnionid(),
//                                    str)
//                            .POST());
//                } else {
//                    LoginDialog();
//                }

                MystartActivity(new Intent(mContext, MemberCenterActivity.class));
                break;
            case R.id.login_out:
                PreferencesUtil.clearKey(USER_INFO);
                MyApplication.getInstance().setUser(null);
                MyApplication.getInstance().setLogin(false);
                ToastUtils.showCENTER("已注销登录");
                startActivityForResult(new Intent(mContext, LoginActivity.class), 123);
                break;
        }


        if (v.getTag() == null) {
            return;
        }
        switch ((int) v.getTag()) {

            case 1:
                MystartActivity(new Intent(mContext, AbutActivity.class));
                break;
            case 2:
                new ClearDialog().show(mContext);
                break;
            case 3:
                MyApplication.getInstance().setUserUp(true);
                mainActivity.callBack();
                break;
            case 4:
                MystartActivity(new Intent(mContext, ShareWebActivity.class)
                        .putExtra(KEY.TITLE, "常见问题")
                        .putExtra(KEY.URL, problem));
                break;
            case 5:
                MystartActivity(new Intent(mContext, FeedBackActivity.class));
                break;
            case 6:
                MystartActivity(new Intent(mContext, AddCodeActivity.class));
                break;

        }

    }

    private void MystartActivity(Intent intent) {

        if (MyApplication.getInstance().isLogin()) {
            startActivity(intent);
        } else {
            LoginDialog();
        }
    }

    private void LoginDialog() {

        new DialogUtils().init(mContext).setTitle("您尚未登录，是否现在登录?")
                .setOne("登录", ButtType -> startActivityForResult(new Intent(mContext, LoginActivity.class), 123)).setTwo("取消", null);
    }


    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 5155:

                new GetUserInfo().Get(MyApplication.getInstance().getUser().getData().getUnionid()
                        , new getUserInterFace() {
                            @Override
                            public void ok(UserInfoMode userInfoMode) {
                                initViews();
                                ToastUtils.showCENTER("激活成功");
                            }

                            @Override
                            public void error(String e) {

                            }
                        });
                break;
            case 1:
                UserInfoMode userInfoMode = new JsonUtil<UserInfoMode>()
                        .json2Bean(jsonString, UserInfoMode.class.getName());
                PreferencesUtil.putString("uid", userInfoMode.getData().getUnionid());
                MyApplication.getInstance().setUser(userInfoMode);
                PreferencesUtil.putString(KEY.USER_INFO, jsonString);
                initViews();
                ToastUtils.show("以获取最新用户信息");
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.showCENTER(e);
    }

    @Override
    public void downloadUpProgress(long Percentile, int httpTY) {

    }
}
