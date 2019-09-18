package core.net;

import java.lang.reflect.Constructor;

import core.log.Logs;
import global.CoreConfigs;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;

public class NetUtils extends RetrofitManager {

    public static NetBackI bindNet(Object binder) {
        try {
            String helperName = binder.getClass().getName() + "_Helper";
            Constructor<NetBackI> constructor = (Constructor<NetBackI>) Class.forName(helperName)
                    .getConstructor(
                            binder.getClass());
            return constructor.newInstance(binder);
        } catch (Exception e) {
            Logs.w(e);
        }
        return null;
    }

    public static API api(CreateApi createApi, String code) {
        return api(createApi, code, CoreConfigs.configs().getBaseUrl());
    }

    public static API api(CreateApi createApi, String code, String baseUrl) {
        return new APISub(createApi, code, baseUrl);
    }

    public static interface CreateApi {
        public Flowable<Result<ResponseBody>> create(Retrofit retrofit);
    }
}
