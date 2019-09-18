package cn.com.codingtu.func4a.api;

import cn.com.codingtu.func4a.core.processor.annotation.net.Api;
import cn.com.codingtu.func4a.core.processor.annotation.net.BaseUrl;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

@Api
@BaseUrl("https://bookgood.wismoly.com/app/api/v1/")
public interface VivoService {
    @GET("/rec/newapps")
    public Flowable<Result<ResponseBody>> newApps(@Query("param") String param,
                                                  @Query("jvq") String jvq);
}
