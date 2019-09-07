package cn.com.codingtu.func4j;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ClassFunc {

    public static Type getParameterizedType(Class clazz, int index) {
        return ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[index];
    }

    public static Type getParameterizedType(Object obj, int index) {
        return getParameterizedType(obj.getClass(), index);
    }

    public static <T> T getPrivateField(Object obj, String fieldName,
                                        Class<T> tClass) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(obj);
    }

}
