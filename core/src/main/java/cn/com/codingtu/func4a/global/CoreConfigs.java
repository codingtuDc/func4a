package cn.com.codingtu.func4a.global;

import cn.com.codingtu.func4a.core.json.jsonholder.JsonHolder;
import cn.com.codingtu.func4a.core.net.RetrofitManager;

public abstract class CoreConfigs {

    public static CoreConfigs CONFIGS;

    public static CoreConfigs configs() {
        if (CONFIGS == null) {
            CONFIGS = App.APP.createConfigs();
        }
        return CONFIGS;
    }


    //处理全局异常
    protected abstract void onGlobalException(Thread t, Throwable e);

    public abstract String defaultLogTag();

    public abstract boolean isLog();

    public abstract JsonHolder createJsonHolder();

    public abstract String getBaseUrl();

    public abstract RetrofitManager.OkHttpClientCreater getDefaultOkHttpClientCreater();

    public abstract RetrofitManager.RetrofitCreater getDefaultRetrofitCreater();

    public abstract int getDefaultIcon();

    public abstract String getPicDirName();
}