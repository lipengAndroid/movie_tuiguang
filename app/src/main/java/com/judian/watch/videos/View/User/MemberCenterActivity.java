package com.judian.watch.videos.View.User;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.cuihai.library.StatusBarUtils;
import com.github.florent37.fiftyshadesof.FiftyShadesOf;
import com.judian.watch.videos.DiyView.BaseActivity;
import com.judian.watch.videos.DiyView.RV.PAdapter;
import com.judian.watch.videos.Http.OkHttpUtils;
import com.judian.watch.videos.Interface.EditTextDialogInterface;
import com.judian.watch.videos.Interface.MyHttpCallBack;
import com.judian.watch.videos.Interface.getUserInterFace;
import com.judian.watch.videos.Mode.UserInfoMode;
import com.judian.watch.videos.Mode.vipZxMode;
import com.judian.watch.videos.R;
import com.judian.watch.videos.Utils.GetUserInfo;
import com.judian.watch.videos.Utils.JsonUtil;
import com.judian.watch.videos.Utils.MyApplication;
import com.judian.watch.videos.Utils.ToastUtils;
import com.judian.watch.videos.Utils.TxtUtils;
import com.judian.watch.videos.View.Dialog.EdDialogUtils;
import com.judian.watch.videos.View.Dialog.MyProgressDialog;
import com.judian.watch.videos.databinding.CouponListItemBinding;
import com.judian.watch.videos.databinding.MemberLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import static com.judian.watch.videos.Http.UrlUtils.URI.usevoucher_two;
import static com.judian.watch.videos.Http.UrlUtils.URI.vipcneterinfo;

/**
 * Created by 李鹏 2017/11/30 0030.
 */

public class MemberCenterActivity extends BaseActivity implements MyHttpCallBack, View.OnClickListener {
    private MemberLayoutBinding binding;
    private PAdapter adapter;

    private FiftyShadesOf fiftyShadesOf;
    private Boolean vipisCK = true;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.member_layout);
        binding.title.title.setText("领取VIP");
        binding.title.imgBack.setOnClickListener(this);
        StatusBarUtils.transparencyBar(this);
        fiftyShadesOf = FiftyShadesOf.with(mContext);
        fiftyShadesOf.on(binding.initView).start();

        getData();

        adapter = new PAdapter<>(listBeans, R.layout.coupon_list_item, (b, position) -> {
            CouponListItemBinding bb = (CouponListItemBinding) b;
            bb.title.setText(listBeans.get(position).getTitle());
            bb.details.setText(listBeans.get(position).getContent());
            bb.get.setText("使用");
            bb.get.setVisibility(View.VISIBLE);
            if (listBeans.get(position).getDiv() == 1) {
                bb.rootView.setBackgroundResource(R.drawable.youhuihb);
                bb.get.setBackgroundResource(R.drawable.bg_ywell_fillet);
                bb.get.setOnClickListener(v -> {
                    String title;
                    if (listBeans.get(position).getTag() == 0) {
                        title = "邀请码";
                    } else {
                        title = "兑换码";
                    }
                    new EdDialogUtils()
                            .init(mContext, title, new EditTextDialogInterface() {
                                @Override
                                public void ED(String order) {
                                    new OkHttpUtils(415, MemberCenterActivity.this)
                                            .SetApiUrl(usevoucher_two)
                                            .SetKey("unionid", "code", "sign", "tag")
                                            .SetValue(MyApplication.getInstance().getUser().getData().getUnionid(),
                                                    order,
                                                    TxtUtils.Md5("yingyinapp" + MyApplication.getInstance().getUser().getData().getUnionid() + ""),
                                                    listBeans.get(position).getTag() + "")
                                            .POST();
                                }

                                @Override
                                public void cancel() {

                                }
                            });

                });
            } else {
                bb.rootView.setBackgroundResource(R.drawable.youhuiw);
                bb.get.setBackgroundResource(R.drawable.bg_hui_yuan);
            }
        });
        binding.recView.setLayoutManager(new LinearLayoutManager(mContext));

        binding.recView.setAdapter(adapter);

    }

    private List<vipZxMode.DataBean> listBeans = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    public void ok(String jsonString, int httpTY) {
        switch (httpTY) {
            case 414:
                vipZxMode vipzxMode = new JsonUtil<vipZxMode>()
                        .json2Bean(jsonString, vipZxMode.class.getName());
                listBeans.clear();
                listBeans.addAll(vipzxMode.getData());
                adapter.notifyDataSetChanged();
                binding.vipEndTime.setText(vipzxMode.getViptime() + "天");
                fiftyShadesOf.stop();
                binding.initView.setVisibility(View.GONE);
                break;
            case 98:
                shuaxin();
                break;
            case 852:
                shuaxin();
                break;
            case 415:
                getData();
                ToastUtils.show("兑换成功");
                break;

        }
    }

    private void shuaxin() {
        getData();
        new GetUserInfo().Get(MyApplication.getInstance().getUser().getData()
                .getUnionid(), new getUserInterFace() {
            @Override
            public void ok(UserInfoMode userInfoMode) {
                vipisCK = true;
            }

            @Override
            public void error(String e) {

            }
        });
    }

    private void getData() {
        new OkHttpUtils(414, this)
                .SetApiUrl(vipcneterinfo)
                .SetKey("unionid")
                .SetValue(MyApplication.getInstance().getUser().getData().getUnionid())
                .POST();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void error(String e, int uriType) {
        switch (uriType) {
            case 415:
                getData();
                break;
        }
        ToastUtils.show(e);
        MyProgressDialog.getInstance().cancel();
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
}
