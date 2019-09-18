package cn.com.codingtu.func4a.core.processor.funcs;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;

import cn.com.codingtu.func4a.core.processor.annotation.net.NetBack;
import cn.com.codingtu.func4a.core.processor.annotation.onactivityresult.OnResult4Activity;
import cn.com.codingtu.func4j.CountFunc;
import cn.com.codingtu.func4j.ls.Ls;
import cn.com.codingtu.func4j.ls.each.Each;

public class ClassFunc {


    public static String getNetBackValue(NetBack netBack) {
        return getAnnotationClass(new AnnotationClassGetter() {
            @Override
            public Object get() {
                return netBack.value();
            }
        });
    }

    public static String getOnResult4ActivityValue(OnResult4Activity onResult4Activity) {
        return getAnnotationClass(new AnnotationClassGetter() {
            @Override
            public Object get() {
                return onResult4Activity.value();
            }
        });
    }

    //////////////////////////////

    public static String getAnnotationClass(AnnotationClassGetter getter) {
        return getAnnotationClass(getter, null);
    }

    public static String getAnnotationClass(AnnotationClassGetter getter, Class defaultClass) {
        String back = null;
        try {
            back = (String) getter.get();
        } catch (MirroredTypeException mte) {
            back = mte.getTypeMirror().toString();
        }
        if (defaultClass != null && defaultClass.getName().equals(back)) {
            back = null;
        }
        return back;
    }


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
