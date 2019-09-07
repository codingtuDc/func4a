package cn.com.codingtu.func4a.core.activity.adapter.more;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.codingtu.func4a.core.activity.adapter.CoreAdapter;
import cn.com.codingtu.func4a.core.activity.viewholder.CoreViewHolder;
import cn.com.codingtu.func4a.core.activity.viewholder.MoreViewHolder;
import cn.com.codingtu.func4j.CountFunc;

public abstract class CoreMoreAdapter<VH extends CoreViewHolder, T>
        extends CoreAdapter<CoreViewHolder> {

    protected boolean hasMore = true;
    protected int page = startPage();
    protected OnLoadMore onLoadMore;
    protected int TYPE_MORE = -100;
    protected List<T> ts;

    public void setOnLoadMore(OnLoadMore onLoadMore) {
        this.onLoadMore = onLoadMore;
    }

    @Override
    public final int getItemViewType(int position) {
        if (hasMore && isLastPosition(position)) {
            return TYPE_MORE;
        }
        return getItemViewTypeInCoreMore(position);
    }

    @NonNull
    @Override
    public final CoreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_MORE) {
            return onCreateMoreViewHolder(viewGroup);
        }
        return onCreateViewHolderInCoreMore(viewGroup, viewType);
    }

    @Override
    public final void onBindViewHolder(@NonNull CoreViewHolder holder, int position) {
        if (hasMore && isLastPosition(position)) {
            onBindMoreViewHolder(holder, position);
        } else {
            onBindViewHolderInCoreMore(holder, position);
        }
    }

    private void loadMore() {
        if (onLoadMore != null)
            onLoadMore.onLoadMore(page);
    }

    public void updateItem(List<T> ts) {
        updateItem(ts, CountFunc.count(ts) > 0);
    }

    public void updateItem(List<T> ts, boolean hasMore) {
        this.hasMore = hasMore;
        if (this.ts == null)
            this.ts = new ArrayList<T>();
        if (page == startPage()) {
            this.ts.clear();
        }
        if (CountFunc.count(ts) > 0)
            this.ts.addAll(ts);

        notifyDataSetChanged();
        page++;
    }

    @Override
    public int getItemCount() {
        return CountFunc.count(ts) + (hasMore ? 1 : 0);
    }

    public static interface OnLoadMore {
        public void onLoadMore(int page);
    }

    private int startPage() {
        return 1;
    }

    public void init(boolean isRefresh) {
        page = startPage();
        hasMore = true;
        if (isRefresh) {
            this.onLoadMore.onLoadMore(page);
        } else {
            if (ts != null)
                ts.clear();
            notifyDataSetChanged();
        }

    }

    /**************************************************
     *
     * 可以覆写的方法,但一般不用覆写
     *
     **************************************************/

    protected int getItemViewTypeInCoreMore(int position) {
        return 0;
    }

    protected CoreViewHolder onCreateViewHolderInCoreMore(ViewGroup viewGroup, int viewType) {
        return createViewHolder(0, viewGroup);
    }

    protected CoreViewHolder onCreateMoreViewHolder(ViewGroup viewGroup) {
        return new MoreViewHolder(viewGroup);
    }


    protected void onBindMoreViewHolder(CoreViewHolder holder, int position) {
        loadMore();
    }


    protected void onBindViewHolderInCoreMore(CoreViewHolder vh, int position) {
        onBindItemViewHolder((VH) vh, position);
    }


    /**************************************************
     *
     * 覆写，处理每一项
     *
     **************************************************/
    protected abstract void onBindItemViewHolder(VH vh, int position);


}
