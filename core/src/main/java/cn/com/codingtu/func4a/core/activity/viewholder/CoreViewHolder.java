package cn.com.codingtu.func4a.core.activity.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import cn.com.codingtu.func4a.core.funcs.InflateFunc;

public class CoreViewHolder extends RecyclerView.ViewHolder {

    public CoreViewHolder(int layoutId, ViewGroup viewGroup) {
        super(InflateFunc.inflate(layoutId, viewGroup));
    }

}