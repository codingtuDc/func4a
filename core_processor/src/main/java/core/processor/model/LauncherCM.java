package core.processor.model;

import java.util.List;

import func4j.CountFunc;
import func4j.StringFunc;

import static core.processor.StaticData.CLASS_NAME_ACT_FUNC;
import static core.processor.StaticData.CLASS_NAME_ACT_LUNCHER;
import static core.processor.StaticData.CLASS_NAME_CODE_4_REQUEST;
import static core.processor.StaticData.CLASS_NAME_JSON_FUNC;
import static core.processor.StaticData.CLASS_PATH_ACT_FUNC;
import static core.processor.StaticData.CLASS_PATH_JSON_FUNC;
import static core.processor.StaticData.PACKAGE_CORE;

public class LauncherCM extends ClassModel {

    public final int launcherLinesIndex;

    public LauncherCM() {
        super(PACKAGE_CORE, CLASS_NAME_ACT_LUNCHER);

        //import
        importLinesIndex = createLines();
        addLines(importLinesIndex, "\r\n");
        addImport("android.app.Activity");
        addImport("android.content.Intent");
        addImport(CLASS_PATH_JSON_FUNC);
        addImport(CLASS_PATH_ACT_FUNC);

        //class
        int classLinesIndex = createLines();
        addLines(classLinesIndex, "\r\n");
        addLines(classLinesIndex, "public class " + name + "{\r\n");

        //launcher
        launcherLinesIndex = createLines();

        //end
        createEndLines();
    }

    public void addLauncher(String act, List<String> paramClasses, String[] paramNames) {

        String className = StringFunc.getClassName(act);
        String staticName = StringFunc.getStaticName(className);
        String methodName = StringFunc.getMethodName(className);

        addImport(act);

        addLines(launcherLinesIndex, "\r\n");
        addLines(launcherLinesIndex, "  public static final void " + methodName + "(Activity act");

        for (int i = 0; i < CountFunc.count(paramClasses); i++) {
            String paramClassName = paramClasses.get(i);
            addImport(paramClassName);
            addLines(launcherLinesIndex, ", " + StringFunc.getClassName(paramClassName) + " " + paramNames[i]);
        }

        addLines(launcherLinesIndex, ") {\r\n");
        addLines(launcherLinesIndex, "        Intent intent = new Intent(act, " + className + ".class);\r\n");

        //
        for (int i = 0; i < CountFunc.count(paramClasses); i++) {
            String paramName = paramNames[i];
            addLines(launcherLinesIndex, "        if (" + paramName + " != null)\r\n");
            addLines(launcherLinesIndex, "            intent.putExtra(Pass." + paramName.toUpperCase() + ", " + CLASS_NAME_JSON_FUNC + ".toJson(" + paramName + "));\r\n");
        }

        addLines(launcherLinesIndex, "        " + CLASS_NAME_ACT_FUNC + ".startActivityForResult(act, intent, " + CLASS_NAME_CODE_4_REQUEST + "." + staticName + ");\r\n");
        addLines(launcherLinesIndex, "  }\r\n");
    }

}
