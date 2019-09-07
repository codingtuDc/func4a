package cn.com.codingtu.func4a.global;

import android.app.Application;

import cn.com.codingtu.func4a.core.log.Logs;

/*************************************************
 *
 * 基础的application，创建工程时要继承此app
 *
 *************************************************/

public abstract class App extends Application implements Thread.UncaughtExceptionHandler {

    //保存自己的实例
    public static App APP;

    @Override
    public void onCreate() {
        super.onCreate();
        APP = this;
        //添加全局的异常捕获
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logs.w(e);
        CoreConfigs.configs().onGlobalException(t, e);
    }

    public abstract CoreConfigs createConfigs();

}
