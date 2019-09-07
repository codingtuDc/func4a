package cn.com.codingtu.func4a.core.processor.funcs;

import javax.lang.model.element.TypeElement;

public class ClassFunc {


    public static String getFullName(TypeElement te) {
        return te.getQualifiedName().toString();
    }
}
