package com.judian.watch.videos.DiyView.RV;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.judian.watch.videos.Interface.BindViewInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李鹏 2017/11/25 0025.
 */

public class PAdapter<T> extends RecyclerView.Adapter<PAdapter.ListHolder> {
    private int layoutId;
    private BindViewInterface bindViewInterface;

    private static final int TYPE_HEADER = 0;  //说明是带有Header的
    private static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    private static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的


    //获取从Activity中传递过来每个item的数据集合
    private List<T> mDatas = new ArrayList<>();
    //HeaderView, FooterView
    private View mHeaderView = null;
    private View mFooterView = null;


    public PAdapter(List<T> mData, int layoutId, BindViewInterface bindViewInterface) {
        this.mDatas = mData;
        this.layoutId = layoutId;
        this.bindViewInterface = bindViewInterface;
    }

    public PAdapter(int mData, int layoutId, BindViewInterface bindViewInterface) {
        for (int i = 0; i < mData; i++) {
            mDatas.add((T) "");
        }
        this.layoutId = layoutId;
        this.bindViewInterface = bindViewInterface;
    }

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    *
     */
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null) {
            return TYPE_NORMAL;
        }
        if (mHeaderView != null) {
            if (position == 0) {
                //第一个item应该加载Header
                return TYPE_HEADER;
            }
        }
        if (mFooterView != null) {
            if (position == getItemCount() - 1) {
                //最后一个,应该加载Footer
                return TYPE_FOOTER;
            }
        }

        return TYPE_NORMAL;
    }

    //创建View，如果是HeaderView或者是FooterView，直接在Holder中返回
    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            ViewGroup p = (ViewGroup) mHeaderView.getParent();
            if (p != null) {
                p.removeView(mHeaderView);
            }
            return new ListHolder(mHeaderView);
        }
        if (mFooterView != null && viewType == TYPE_FOOTER) {
            return new ListHolder(mFooterView);
        }
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                layoutId,
                parent,
                false);
        ListHolder listHolder = new ListHolder(binding.getRoot());
        listHolder.setBinding(binding);
        return listHolder;
    }


    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了
    @Override
    public void onBindViewHolder(ListHolder holder, int position) {

        if (getItemViewType(position) == TYPE_FOOTER) {
            return;
        }
        if (getItemViewType(0) != TYPE_HEADER) {
            bindViewInterface.bandView(holder.getBinding(), position);
        } else if (position != 0) {
            bindViewInterface.bandView(holder.getBinding(), position - 1);
        }

        /*else if (getItemViewType(position) == TYPE_HEADER) {
            return;
        } else {
            return;
        }*/
    }

    //在这里面加载ListView中的每个item的布局
    class ListHolder<T> extends RecyclerView.ViewHolder {
        ViewDataBinding viewDataBinding;

        ListHolder(View itemView) {
            super(itemView);
            //如果是headerview或者是footerview,直接返回


        }

        public ViewDataBinding getBinding() {
            return viewDataBinding;
        }

        public void setBinding(ViewDataBinding viewDataBinding) {
            this.viewDataBinding = viewDataBinding;
        }
    }

    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        if (mHeaderView == null && mFooterView == null) {
            return mDatas.size();
        } else if (mHeaderView == null) {
            return mDatas.size() + 1;
        } else if (mFooterView == null) {
            return mDatas.size() + 1;
        } else {
            return mDatas.size() + 2;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER || getItemViewType(position) == TYPE_FOOTER
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(PAdapter.ListHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

}

