package core.json;

import java.util.List;

import core.json.jsonholder.FastJsonHolder;
import core.json.jsonholder.JsonHolder;
import global.CoreConfigs;

/**************************************************
 *
 * json工具类
 *
 **************************************************/
public class JsonFunc {
    private static JsonHolder JSON;

    private static JsonHolder getJSON() {
        if (JSON == null) {
            JSON = CoreConfigs.configs().createJsonHolder();
            if (JSON == null)
                JSON = new FastJsonHolder();
        }
        return JSON;
    }

    public static <T> T toBean(Class<T> tClass, String json) {
        return getJSON().toBean(tClass, json);
    }

    public static String toJson(Object obj) {
        return getJSON().toJson(obj);
    }

    public static <T> List<T> toBeanList(Class<T> tClass, String json) {
        return getJSON().toBeanList(tClass, json);
    }
}