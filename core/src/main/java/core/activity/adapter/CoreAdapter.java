package core.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;

import core.R;
import core.activity.viewholder.CoreViewHolder;
import core.log.Logs;
import func4j.ClassFunc;

public abstract class CoreAdapter<VH extends CoreViewHolder> extends RecyclerView.Adapter<VH> {

    private View.OnClickListener onClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    protected CoreViewHolder createViewHolder(int p, ViewGroup viewGroup) {
        try {
            Constructor<CoreViewHolder> constructor = ((Class) ClassFunc
                    .getParameterizedType(this, p)).getConstructor(ViewGroup.class);
            return constructor.newInstance(viewGroup);
        } catch (Exception e) {
            Logs.w(e);
            return null;
        }
    }


    /***************************************
     *
     *  判断最后一项
     *
     ***************************************/

    protected boolean isLastPosition(int position) {
        return position == (getItemCount() - 1);
    }


    /**************************************************
     *
     * onclick
     *
     **************************************************/
    protected void setOnClick(View view) {
        if (view != null && onClickListener != null)
            view.setOnClickListener(onClickListener);
    }


    protected void setOnClick(View view, int position) {
        if (view != null && onClickListener != null) {
            view.setTag(R.id.tag_position, position);
            view.setOnClickListener(onClickListener);
        }
    }

    protected void setOnClick(View view, Object obj) {
        if (view != null && onClickListener != null && obj != null) {
            view.setTag(R.id.tag_obj, obj);
            view.setOnClickListener(onClickListener);
        }
    }

    protected void setOnClick(View view, int position, Object obj) {
        if (view != null && onClickListener != null && obj != null) {
            view.setTag(R.id.tag_position, position);
            view.setTag(R.id.tag_obj, obj);
            view.setOnClickListener(onClickListener);
        }
    }


}
