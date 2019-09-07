package cn.com.codingtu.func4a.core.bean;

import cn.com.codingtu.func4a.core.json.JsonFunc;

public class CoreBean {

    @Override
    public String toString() {
        return JsonFunc.toJson(this);
    }
}
