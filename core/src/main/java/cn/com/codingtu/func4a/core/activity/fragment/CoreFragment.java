package cn.com.codingtu.func4a.core.activity.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.codingtu.func4a.core.funcs.InflateFunc;

public abstract class CoreFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = InflateFunc.inflate(inflater, getLayoutId(), container);
        onCreatedView(view);
        return view;
    }

    protected abstract int getLayoutId();

    protected void onCreatedView(View view) {

    }

}