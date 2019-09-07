package cn.com.codingtu.func4a.core.net;

import android.os.Looper;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.Result;

public class APISub implements API {
    private NetUtils.CreateApi createApi;
    /**************************************************
     *
     * 回调的方法名字,默认的为api的方法名，可以自己设置名字
     *
     **************************************************/
    private String m;
    private String group;
    private String baseUrl;
    private RetrofitManager.RetrofitCreater retrofitCreater;
    private RetrofitManager.OkHttpClientCreater okHttpClientCreater;

    public APISub(NetUtils.CreateApi createApi, String m, String baseUrl) {
        this.createApi = createApi;
        this.baseUrl = baseUrl;
        this.m = m;
    }

    /**************************************************
     *
     * 自定义配置方法
     *
     **************************************************/

    @Override
    public API m(String m) {
        this.m = m;
        return this;
    }

    @Override
    public API group(String group) {
        this.group = group;
        return this;
    }

    @Override
    public API retrofit(RetrofitManager.RetrofitCreater retrofitCreater) {
        this.retrofitCreater = retrofitCreater;
        return this;
    }

    @Override
    public API okHttp(RetrofitManager.OkHttpClientCreater okHttpClientCreater) {
        this.okHttpClientCreater = okHttpClientCreater;
        return this;
    }

    /**************************************************
     *
     * 请求方法
     *
     **************************************************/

    @Override
    public void main(NetBackI helper) {
        main(helper, false);
    }

    @Override
    public void main(NetBackI helper, boolean isForceNewThread) {
        run(helper, AndroidSchedulers.mainThread(), isForceNewThread);
    }

    @Override
    public void io(NetBackI helper) {
        io(helper, false);
    }

    @Override
    public void io(NetBackI helper, boolean isForceNewThread) {
        run(helper, Schedulers.trampoline(), isForceNewThread);
    }

    private void run(final NetBackI helper, Scheduler resultScheduler, boolean isForceNewThread) {

        Scheduler work = null;
        if (isForceNewThread || Looper.getMainLooper().getThread() == Thread.currentThread()) {
            work = Schedulers.io();
        } else {
            work = Schedulers.trampoline();
        }

        Retrofit retrofit = NetUtils
                .getRetrofit(group, baseUrl, retrofitCreater, okHttpClientCreater);

        createApi.create(retrofit).subscribeOn(work).observeOn(resultScheduler)
                .subscribe(new Consumer<Result<ResponseBody>>() {
                    @Override
                    public void accept(Result<ResponseBody> result) throws Exception {
                        if (helper != null)
                            helper.accept(m, result);
                    }
                });

    }

}
