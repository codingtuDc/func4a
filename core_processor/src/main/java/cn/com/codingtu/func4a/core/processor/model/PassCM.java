package cn.com.codingtu.func4a.core.processor.model;

import java.util.ArrayList;
import java.util.List;

import cn.com.codingtu.func4a.core.processor.BaseProcessor;
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
        addLines(methodLinesIndex, "    return JsonFunc.toBean(" + simpleName + ".class, data.getStringExtra(" + paramName.toUpperCase() + "));\r\n");
        addLines(methodLinesIndex, "  }\r\n");
        addLines(methodLinesIndex, "\r\n");
    }
}
