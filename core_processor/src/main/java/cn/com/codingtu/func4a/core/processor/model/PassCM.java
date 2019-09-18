package cn.com.codingtu.func4a.core.processor.model;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import cn.com.codingtu.func4a.core.processor.BaseProcessor;
import cn.com.codingtu.func4a.core.processor.annotation.onactivityresult.OnResult4Activity;
import cn.com.codingtu.func4j.CountFunc;
import cn.com.codingtu.func4j.StringFunc;

public class PassCM extends ClassModel {

    public final int fieldLinesIndex;
    public final int methodLinesIndex;

    private List<String> passes = new ArrayList<String>();

    public PassCM() {
        super(BaseProcessor.PACKAGE_CORE, "Pass");

        //import
        importLinesIndex = createLines();
        addLines(importLinesIndex, "\r\n");
        addLines(importLinesIndex, "import android.content.Intent;\r\n");
        addLines(importLinesIndex, "import " + BaseProcessor.PACKAGE_CORE + ".json.JsonFunc;\r\n");

        //class
        int classLinesIndex = createLines();
        addLines(classLinesIndex, "\r\n");
        addLines(classLinesIndex, "public class " + name + " {\r\n");

        //field
        fieldLinesIndex = createLines();

        //method
        methodLinesIndex = createLines();

        //end
        createEndLines();
    }


    public void addOnResult(ExecutableElement ee) {
        OnResult4Activity onResult4Activity = ee.getAnnotation(OnResult4Activity.class);
        if (onResult4Activity.isDeal()) {
            List<? extends VariableElement> parameters = ee.getParameters();

            int count = CountFunc.count(parameters);

            ArrayList<String> paramClasses = new ArrayList<String>();
            String[] paramNames = new String[count];

            for (int i = 0; i < count; i++) {
                VariableElement ve = parameters.get(i);
                paramClasses.add(ve.asType().toString());
                paramNames[i] = ve.getSimpleName().toString();
            }
            addPass(paramClasses, paramNames);
        }
    }


    public void addPass(List<String> paramClasses, String[] paramNames) {
        for (int i = 0; i < CountFunc.count(paramClasses); i++) {
            String paramName = paramNames[i];
            if (!passes.contains(paramName)) {
                passes.add(paramName);
                addField(paramName);
                addMethod(paramClasses.get(i), paramName);
            }
        }
    }

    public void addField(String paramName) {
        addLines(fieldLinesIndex, "\r\n");
        addLines(fieldLinesIndex, "  public static final String " + paramName.toUpperCase() + " = \"" + paramName + "\";\r\n");
    }

    public void addMethod(String paramClass, String paramName) {
        addImport(paramClass);
        String simpleName = StringFunc.getSimpleName(paramClass);
        addLines(methodLinesIndex, "\r\n");
        addLines(methodLinesIndex, "  public static final " + simpleName + " " + paramName + "(Intent data) {\r\n");
        if ("int".equals(simpleName)) {
            addLines(methodLinesIndex, "        return data.getIntExtra(" + paramName.toUpperCase() + ", -404);\r\n");
        } else if ("String".equals(simpleName)) {
            addLines(methodLinesIndex, "        return data.getStringExtra(" + paramName.toUpperCase() + ");\r\n");
        } else {
            addLines(methodLinesIndex, "    return JsonFunc.toBean(" + simpleName + ".class, data.getStringExtra(" + paramName.toUpperCase() + "));\r\n");
        }
        addLines(methodLinesIndex, "  }\r\n");
        addLines(methodLinesIndex, "\r\n");
    }

}
