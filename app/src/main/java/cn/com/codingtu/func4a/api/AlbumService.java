package cn.com.codingtu.func4a.api;

import core.processor.annotation.net.Api;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Path;

@Api
public interface AlbumService {

    @GET("albums/{id}")
    public Flowable<Result<ResponseBody>> getAlbumDetail(@Path("id") String id);

}
