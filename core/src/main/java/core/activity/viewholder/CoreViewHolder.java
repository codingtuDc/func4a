package core.activity.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import core.funcs.InflateFunc;
import core.hero.HeroFunc;

public class CoreViewHolder extends RecyclerView.ViewHolder {

    public CoreViewHolder(int layoutId, ViewGroup viewGroup) {
        super(InflateFunc.inflate(layoutId, viewGroup));
        HeroFunc.bind(this, itemView);
    }

}