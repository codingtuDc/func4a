package cn.com.codingtu.func4a.core.net;

public interface API {
    API m(String m);

    API group(String group);

    API retrofit(RetrofitManager.RetrofitCreater retrofitCreater);

    API okHttp(RetrofitManager.OkHttpClientCreater okHttpClientCreater);

    void main(NetBackI helper);

    void main(NetBackI helper, boolean isForceNewThread);

    void io(NetBackI helper);

    void io(NetBackI helper, boolean isForceNewThread);
}
