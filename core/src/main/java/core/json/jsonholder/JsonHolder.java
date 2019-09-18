package core.json.jsonholder;

import java.util.List;

public interface JsonHolder {
    <T> T toBean(Class<T> tClass, String json);

    String toJson(Object obj);

    <T> List<T> toBeanList(Class<T> tClass, String json);
}
