package cn.com.codingtu.func4a.core.processor.model;

import java.util.List;

import cn.com.codingtu.func4a.core.processor.BaseProcessor;
import cn.com.codingtu.func4j.CountFunc;
import cn.com.codingtu.func4j.StringFunc;

public class LauncherCM extends ClassModel {

    public final int launcherLinesIndex;

    public LauncherCM() {
        super(BaseProcessor.PACKAGE_CORE, "ActLuncher");

        //import
        importLinesIndex = createLines();
        addLines(importLinesIndex, "\r\n");
        addImport("android.app.Activity");
        addImport("android.content.Intent");
        addImport(BaseProcessor.PACKAGE_CORE + ".json.JsonFunc");
        addImport(BaseProcessor.PACKAGE_CORE + ".funcs.ActFunc");

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

        String className = StringFunc.getSimpleName(act);
        String staticName = StringFunc.getStaticName(className);
        String methodName = StringFunc.getMethodName(className);

        addImport(act);

        addLines(launcherLinesIndex, "\r\n");
        addLines(launcherLinesIndex, "  public static final void " + methodName + "(Activity act");

        for (int i = 0; i < CountFunc.count(paramClasses); i++) {
            String paramClassName = paramClasses.get(i);
            addImport(paramClassName);
            addLines(launcherLinesIndex, ", " + StringFunc.getSimpleName(paramClassName) + " " + paramNames[i]);
        }

        addLines(launcherLinesIndex, ") {\r\n");
        addLines(launcherLinesIndex, "        Intent intent = new Intent(act, " + className + ".class);\r\n");

        //
        for (int i = 0; i < CountFunc.count(paramClasses); i++) {
            String paramName = paramNames[i];
            addLines(launcherLinesIndex, "        if (" + paramName + " != null)\r\n");
            addLines(launcherLinesIndex, "            intent.putExtra(Pass." + paramName.toUpperCase() + ", JsonFunc.toJson(" + paramName + "));\r\n");
        }

        addLines(launcherLinesIndex, "        ActFunc.startActivityForResult(act, intent, Code4Request." + staticName + ");\r\n");
        addLines(launcherLinesIndex, "  }\r\n");
    }

}
