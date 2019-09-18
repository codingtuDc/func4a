package global;

import core.json.jsonholder.JsonHolder;
import core.net.RetrofitManager;

public class Configs extends CoreConfigs {
    @Override
    protected void onGlobalException(Thread t, Throwable e) {

    }

    @Override
    public String defaultLogTag() {
        return null;
    }

    @Override
    public boolean isLog() {
        return false;
    }

    @Override
    public JsonHolder createJsonHolder() {
        return null;
    }

    @Override
    public String getBaseUrl() {
        return null;
    }

    @Override
    public RetrofitManager.OkHttpClientCreater getDefaultOkHttpClientCreater() {
        return null;
    }

    @Override
    public RetrofitManager.RetrofitCreater getDefaultRetrofitCreater() {
        return null;
    }

    @Override
    public int getDefaultIcon() {
        return 0;
    }

    @Override
    public String getPicDirName() {
        return null;
    }
}
