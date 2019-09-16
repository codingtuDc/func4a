package cn.com.codingtu.func4a.core.processor.funcs;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;

import cn.com.codingtu.func4j.CountFunc;
import cn.com.codingtu.func4j.ls.Ls;
import cn.com.codingtu.func4j.ls.each.Each;

public class ClassFunc {


    public static List<String> getAnnotationClasses(AnnotationClassGetter getter) {
        try {
            getter.get();
        } catch (MirroredTypesException mte) {
            List<? extends TypeMirror> typeMirrors = mte.getTypeMirrors();
            ArrayList<String> classNames = new ArrayList<String>();
            for (int i = 0; i < CountFunc.count(typeMirrors); i++) {
                classNames.add(typeMirrors.get(i).toString());
            }
            return classNames;
        }
        return null;
    }

    public static interface AnnotationClassGetter {
        Object get();
    }
}
