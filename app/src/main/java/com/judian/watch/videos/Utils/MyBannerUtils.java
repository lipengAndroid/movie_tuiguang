package com.judian.watch.videos.Utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.judian.watch.videos.DiyView.MyBannerView;
import com.judian.watch.videos.Mode.BannerInfo;
import com.judian.watch.videos.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


/*
 * Created by Administrator on 2017/10/16 0016.
 */

public class MyBannerUtils {
    private List<View> mViewList = new ArrayList<>();

    public void show(Context context, List<BannerInfo> list, MyBannerView view) {
        setValue(context, list);//最上面轮播图
        view.setValue(mViewList, true, false);
    }


    private void setValue(final Context context, final List<BannerInfo> list) {

        ImageView img;
        AtomicReference<TextView> textView = new AtomicReference<>();
        View viewPagerItem;
        for (int i = 0; i < list.size(); i++) {
            viewPagerItem = View.inflate(context, R.layout.viewpaper_item, null);
            img = viewPagerItem.findViewById(R.id.title_image);
            textView.set(viewPagerItem.findViewById(R.id.title));
            textView.get().setText(list.get(i).getTitle());
            textView.get().setText(list.get(i).getTitle());
            ImgLoadUtils.init(context)
                    .Uri(list.get(i).getPic_url())
                    .Show(img);

//
//                img.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (ck) {
//                            Intent intent = new Intent(context, ShareWebActivity.class);
//                            intent.putExtra(URL, list.get(finalI).getLink_value());
//                            context.startActivity(intent);
//                        }
//
//                    }
//                });
            mViewList.add(viewPagerItem);
        }

    }
}
