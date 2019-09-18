package core.bean;

import core.json.JsonFunc;

public class CoreBean {

    @Override
    public String toString() {
        return JsonFunc.toJson(this);
    }
}
