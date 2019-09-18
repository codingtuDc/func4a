package cn.com.codingtu.func4a;

import cn.com.codingtu.func4a.core.net.NetBackI;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.Result;

public class BeanBack implements NetBackI {
    @Override
    public void accept(String code, Result<ResponseBody> result) {
        back("", new User());
    }

    //此方法名不可更改，但是参数可根据自己的需求来返回
    public void back(String messsage, User user) {
    }
}
