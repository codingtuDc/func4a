package cn.com.codingtu.func4a.api;

import cn.com.codingtu.func4a.User;
import core.processor.annotation.net.Api;
import core.processor.annotation.net.BaseUrl;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Path;

@Api
@BaseUrl("https://bookgood.wismoly.com/app/api/v1/")
public interface SelfService {

    @GET("albums/{id}")
    public Flowable<Result<ResponseBody>> selfDetailOne(@Path("id") String id, User user);
    @GET("albums/{id}")
    public Flowable<Result<ResponseBody>> selfDetailTwo(@Path("id") String id);
    @GET("albums/{id}")
    public Flowable<Result<ResponseBody>> selfDetailThree(@Path("id") String id, int age);

}
