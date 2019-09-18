package cn.com.codingtu.func4a.api;

import core.processor.annotation.net.Api;
import core.processor.annotation.net.BaseUrl;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Query;

@Api
@BaseUrl("https://main.appstore.vivo.com.cn")
public interface VivoService {
    @GET("/rec/newapps")
    public Flowable<Result<ResponseBody>> newApps(@Query("param") String param,
                                                  @Query("jvq") String jvq);
}
