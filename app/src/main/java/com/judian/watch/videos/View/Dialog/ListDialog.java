package com.judian.watch.videos.View.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.judian.watch.videos.DiyView.RV.PAdapter;
import com.judian.watch.videos.Interface.BindViewInterface;
import com.judian.watch.videos.Interface.ListItemListener;
import com.judian.watch.videos.R;
import com.judian.watch.videos.databinding.DiyListItemItemBinding;

import java.util.List;


/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class ListDialog {
    //    iqiyi 爱奇艺
//    sohu  搜狐
//    letv  乐视网
//    qq    腾讯视频
//    ykyun 优酷云
//    mgtv  芒果TV
//    tudou 土豆网
//    pptv  PPTV
//    youku 优酷网
//
//    other 云资源
    public void show(Context context, List<String> titles, final ListItemListener listItemListener) {
        Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialig_list_layout);
        RecyclerView recyclerView = dialog.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL));
        recyclerView.setAdapter(new PAdapter<>(titles, R.layout.diy_list_item_item, new BindViewInterface() {
            @Override
            public void bandView(ViewDataBinding binding, int position) {
                DiyListItemItemBinding bb = (DiyListItemItemBinding) binding;

                bb.tvGoodsName.setText(titles.get(position));
                bb.tvGoodsName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listItemListener.Item(position);
                        dialog.dismiss();
                    }
                });
            }
        }));

        dialog.show();
    }


}