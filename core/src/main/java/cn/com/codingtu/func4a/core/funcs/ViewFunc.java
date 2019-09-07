package cn.com.codingtu.func4a.core.funcs;

import android.app.Activity;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.codingtu.func4a.core.log.Logs;
import cn.com.codingtu.func4a.global.App;
import cn.com.codingtu.func4j.ClassFunc;
import cn.com.codingtu.func4j.StringFunc;

public class ViewFunc {

    public static final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    public static final int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;

    /****************************************************************
     *
     * 可见与不可见
     *
     ****************************************************************/

    //设置view状态
    public static void visible(View view) {
        if (view != null) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void gone(View view) {
        if (view != null) {
            view.setVisibility(View.GONE);
        }
    }

    public static void invisible(View view) {
        if (view != null) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    //判断view当前状态，visible，gone或者invisible
    public static boolean isVisible(View view) {
        return checkStatus(view, View.VISIBLE);
    }

    public static boolean isGone(View view) {
        return checkStatus(view, View.GONE);
    }

    public static boolean isInvisible(View view) {
        return checkStatus(view, View.INVISIBLE);
    }

    public static boolean checkStatus(View view, int checkStatus) {
        if (view != null) {
            return view.getVisibility() == checkStatus;
        }
        return false;
    }

    //切换view状态
    public static void visibleOrGone(View view, boolean isVisible) {
        if (view != null) {
            view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    public static void visibleOrInvisible(View view, boolean isVisible) {
        if (view != null) {
            view.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        }
    }

    /****************************************************************
     *
     * 设置Textview
     *
     ****************************************************************/

    public static void setTextSize(View tv, int size) {
        if (tv != null && tv instanceof TextView) {
            ((TextView) tv).setTextSize(TypedValue.COMPLEX_UNIT_PX, size);
        }
    }

    public static void setText(View tv, Object text) {
        if (tv != null && tv instanceof TextView) {
            ((TextView) tv).setText(StringFunc.toString(text));
        }
    }

    public static void setText(View tv, Object text, String defaultStr) {
        String str = StringFunc.toString(text);
        if (StringFunc.isBlank(str)) {
            str = defaultStr;
        }
        setText(tv, str);
    }

    public static void setEditTextAndSelection(View view, Object obj) {
        if (view != null && view instanceof EditText) {
            String text = StringFunc.toString(obj);
            ((EditText) view).setText(text);
            ((EditText) view).setSelection(text.length());
        }
    }

    public static void setEditTextAndSelection(View view, Object obj, int position) {
        if (view != null && view instanceof EditText) {
            String text = StringFunc.toString(obj);
            ((EditText) view).setText(text);
            if (position > text.length()) {
                position = text.length();
            }
            ((EditText) view).setSelection(position);
        }
    }

    /****************************************************************
     *
     * 输入法的收起
     *
     ****************************************************************/

    public static void inputHidden(EditText et) {
        if (et != null) {
            InputMethodManager imm = SystemFunc.getInputMethodManager();
            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        }
    }

    public static void inputShow(EditText et) {
        if (et != null) {
            InputMethodManager imm = SystemFunc.getInputMethodManager();
            imm.showSoftInput(et, InputMethodManager.SHOW_FORCED);
        }
    }

    /****************************************************************
     *
     * RecyclerView设置
     *
     ****************************************************************/

    public static void setRecyclerViewLinearVertical(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(App.APP, LinearLayoutManager.VERTICAL, false));
    }

    public static void setRecyclerViewLinearHorizontal(RecyclerView rv) {
        rv.setLayoutManager(
                new LinearLayoutManager(App.APP, LinearLayoutManager.HORIZONTAL, false));
    }

    /****************************************************************
     *
     * view宽高
     *
     ****************************************************************/
    public static void setW(View view, int width) {
        if (view == null)
            return;
        view.getLayoutParams().width = width;
    }

    public static void setH(View view, int height) {
        if (view == null)
            return;
        view.getLayoutParams().height = height;
    }

    public static void setWH(View view, int width, int height) {
        if (view == null)
            return;
        view.getLayoutParams().width = width;
        view.getLayoutParams().height = height;
    }

    /****************************************************************
     *
     * 获得edittext的设置的最大字符数
     *
     ****************************************************************/

    public static int getEditTextMaxLength(EditText codeEt) {
        InputFilter[] filters = codeEt.getFilters();
        for (int i = 0; i < (filters == null ? 0 : filters.length); i++) {
            InputFilter filter = filters[i];
            if (filter instanceof InputFilter.LengthFilter) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    return ((InputFilter.LengthFilter) filter).getMax();
                } else {
                    try {
                        return ClassFunc.getPrivateField(filter, "mMax", int.class);
                    } catch (Exception e) {
                        Logs.w(e);
                    }
                }
            }
        }
        return -1;
    }

    /****************************************************************
     *
     * 获得rootView
     *
     ****************************************************************/

    public static View getRootView(Activity act) {
        return ((ViewGroup) act.getWindow().getDecorView().findViewById(android.R.id.content))
                .getChildAt(0);
    }

    public static ViewGroup getRootViewGroup(Activity act) {
        return (ViewGroup) getRootView(act);
    }

    /****************************************************************
     *
     * removeView
     *
     ****************************************************************/
    public static void removeView(View view) {
        ((ViewGroup) view.getParent()).removeView(view);
    }

    /****************************************************************
     *
     * measureView
     *
     ****************************************************************/
    public static void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

}
