package cn.com.codingtu.func4a.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import cn.com.codingtu.func4a.core.funcs.ActFunc;
import cn.com.codingtu.func4a.core.funcs.AdjustFunc;
import cn.com.codingtu.func4a.core.funcs.StatusBarFunc;
import cn.com.codingtu.func4a.core.funcs.ViewFunc;
import cn.com.codingtu.func4a.core.hero.Hero;
import cn.com.codingtu.func4a.core.hero.HeroFunc;
import cn.com.codingtu.func4a.core.hero.OnActivityBack;
import cn.com.codingtu.func4a.core.permission.PermissionHelper;
import cn.com.codingtu.func4j.CountFunc;
import cn.com.codingtu.func4j.ls.Ls;

public class CoreActivity extends AppCompatActivity implements ActivityFunc {

    protected Hero hero;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AdjustFunc.setCustomDensity(getThis());
        initStatusBar();
    }

    protected void initStatusBar() {
        StatusBarFunc.translucentAndLight(getThis());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        final View rootView = ViewFunc.getRootView(getThis());
        rootView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        onViewInitComplete();
                        rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });
        hero = HeroFunc.bind(getThis());
        if (hero != null) {
            addPermissionHelper(hero);
            addOnActivityBack(hero);
        }
    }

    protected void onViewInitComplete() {
    }

    protected Activity getThis() {
        return this;
    }

    /********************************
     *
     * 对finish方法做扩展
     *
     ********************************/
    @Override
    public void finish() {
        beforeFinish();
        super.finish();
        setFinishAnimation();
        afterFinish();
    }

    //finish之后调用
    protected void afterFinish() {
    }

    //设置finish动画
    protected void setFinishAnimation() {
        ActFunc.actRightOut(getThis());
    }

    //finish之前调用
    protected void beforeFinish() {
    }

    /**************************************************
     *
     *
     *
     **************************************************/

    private List<PermissionHelper> permissionHelpers;

    public void addPermissionHelper(PermissionHelper helper) {
        if (permissionHelpers == null)
            permissionHelpers = new ArrayList<PermissionHelper>();
        permissionHelpers.add(helper);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Ls.ls(permissionHelpers, (position, helper) -> {
            helper.onPermissionsBack(requestCode, permissions, grantResults);
            return false;
        });
    }

    private List<OnActivityBack> onActivityBacks;

    public void addOnActivityBack(OnActivityBack back) {
        if (onActivityBacks == null)
            onActivityBacks = new ArrayList<OnActivityBack>();
        onActivityBacks.add(back);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Ls.ls(onActivityBacks, (position, back) -> {
            back.onActivityResult(requestCode, resultCode, data);
            return false;
        });
    }

    /**************************************************
     *
     * onkeydown
     *
     **************************************************/

    private List<WhenKeyDown> keyDowns;

    public void addToKeyDowns(WhenKeyDown keyDown) {
        if (keyDowns == null) {
            keyDowns = new ArrayList<WhenKeyDown>();
        }
        keyDowns.add(keyDown);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean b = false;

        for (int i = 0; i < CountFunc.count(keyDowns); i++) {
            if (keyDowns.get(i).onKeyDown(keyCode, event)) {
                b = true;
            }
        }

        return b ? b : super.onKeyDown(keyCode, event);
    }

}