package cn.com.codingtu.func4a.core.json.jsonholder;

import com.alibaba.fastjson.JSON;

import java.util.List;

import cn.com.codingtu.func4a.core.log.Logs;

public class FastJsonHolder implements JsonHolder {
    @Override
    public <T> T toBean(Class<T> tClass, String json) {
        try {
            return JSON.parseObject(json, tClass);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    @Override
    public <T> List<T> toBeanList(Class<T> tClass, String json) {
        try {
            return JSON.parseArray(json, tClass);
        } catch (Exception e) {
            Logs.w(e);
            return null;
        }
    }
}