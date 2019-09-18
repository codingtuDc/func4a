package core.net;

import java.util.HashMap;
import java.util.Map;

import global.CoreConfigs;
import func4j.StringFunc;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitManager {

    private static Map<String, Retrofit> retrofits;
    private static String DEFAULT_GROUP = "default_group";

    public static Retrofit getRetrofit(String group, String baseUrl,
                                       RetrofitCreater retrofitCreater, OkHttpClientCreater okHttpClientCreater) {
        if (StringFunc.isBlank(group)) {
            group = DEFAULT_GROUP;
        }
        String retrofitName = retrofitName(group, baseUrl);
        Retrofit retrofit = null;
        if (retrofits == null) {
            retrofits = new HashMap<String, Retrofit>();
        } else {
            retrofit = retrofits.get(retrofitName);
        }
        if (retrofit == null) {

            if (okHttpClientCreater == null) {
                okHttpClientCreater = CoreConfigs.configs().getDefaultOkHttpClientCreater();
            }

            OkHttpClient okHttpClient = null;
            if (okHttpClientCreater != null) {
                okHttpClient = okHttpClientCreater.create();
            } else {
                okHttpClient = createOkHttpClient();
            }

            if (retrofitCreater == null) {
                retrofitCreater = CoreConfigs.configs().getDefaultRetrofitCreater();
            }

            if (retrofitCreater == null) {
                retrofit = createRetrofit(okHttpClient, baseUrl);
            } else {
                retrofit = retrofitCreater.create(okHttpClient, baseUrl);
            }
            retrofits.put(retrofitName, retrofit);
        }
        return retrofit;
    }

    private static OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new BaseInterceptor(CoreConfigs.configs().isLog())).build();
    }

    private static Retrofit createRetrofit(OkHttpClient okHttpClient, String baseUrl) {
        return new Retrofit.Builder().client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl).build();
    }

    private static String retrofitName(String group, String baseUrl) {
        return group + "::" + baseUrl;
    }

    public static interface RetrofitCreater {
        public Retrofit create(OkHttpClient okHttpClient, String baseUrl);
    }

    public static interface OkHttpClientCreater {
        public OkHttpClient create();
    }


}