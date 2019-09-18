package core.net;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava2.Result;

public interface NetBackI {
    void accept(String code, Result<ResponseBody> result);
}

