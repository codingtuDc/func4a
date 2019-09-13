package cn.com.codingtu.func4a.core.processor.funcs;

import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;

import cn.com.codingtu.func4j.ls.Ls;
import cn.com.codingtu.func4j.ls.each.Each;

public class ClassFunc {


    public static void getAnnotationClasses(AnnotationClassGetter getter, Each<String> each) {
        try {
            getter.get();
        } catch (MirroredTypesException mte) {
            List<? extends TypeMirror> typeMirrors = mte.getTypeMirrors();

            Ls.ls(typeMirrors, (position, typeMirror) -> {
                each.each(position, typeMirror.toString());
                return false;
            });
        }
    }

    public static interface AnnotationClassGetter {
        Object get();
    }
}
