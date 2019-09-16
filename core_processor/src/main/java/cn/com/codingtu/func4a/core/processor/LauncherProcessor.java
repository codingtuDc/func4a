package cn.com.codingtu.func4a.core.processor;

import com.google.auto.service.AutoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import cn.com.codingtu.func4a.core.processor.annotation.activity.Launcher;
import cn.com.codingtu.func4a.core.processor.funcs.ClassFunc;
import cn.com.codingtu.func4a.core.processor.model.ClassModel;
import cn.com.codingtu.func4j.CountFunc;
import cn.com.codingtu.func4j.StringFunc;
import cn.com.codingtu.func4j.ls.each.Each;

@AutoService(Processor.class)
public class LauncherProcessor extends BaseProcessor {
    private ClassModel launcherCm;
    private int launcherLinesIndex;
    private ClassModel passCm;
    private int passLinesIndex;
    private ClassModel code4RequestCm;
    private int code4RequestLinesIndex;
    private int code4Request = 0;
    private List<String> passes = new ArrayList<String>();
    private int passMethodLinesIndex;

    @Override
    protected Class[] getSupportTypes() {
        return new Class[]{
                Launcher.class
        };
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        ls(roundEnvironment, Launcher.class, new Each<Element>() {
            @Override
            public boolean each(int position, Element element) {

                if (element instanceof TypeElement) {
                    dealLauncher(((TypeElement) element));
                }

                return false;
            }

        });

        if (launcherCm != null)
            createClass(launcherCm, null);
        if (passCm != null)
            createClass(passCm, null);
        if (code4RequestCm != null)
            createClass(code4RequestCm, null);

        launcherCm = null;
        passCm = null;
        code4RequestCm = null;
        return true;
    }

    private void dealLauncher(TypeElement te) {
        createClassModel();

        String fullName = te.getQualifiedName().toString();
        launcherCm.addImport(fullName);

        String simpleName = te.getSimpleName().toString();
        String staticName = StringFunc.getStaticName(simpleName);
        String methodName = StringFunc.getMethodName(simpleName);

        Launcher launcher = te.getAnnotation(Launcher.class);
        List<String> paramClasses = ClassFunc.getAnnotationClasses(new ClassFunc.AnnotationClassGetter() {
            @Override
            public Object get() {
                return launcher.paramClasses();
            }
        });

        String[] paramNames = launcher.paramNames();

        launcherCm.addLines(launcherLinesIndex, "\r\n");
        launcherCm.addLines(launcherLinesIndex, "  public static final void " + methodName + "(Activity act");

        ArrayList<String> putExtras = new ArrayList<String>();
        for (int i = 0; i < CountFunc.count(paramClasses); i++) {
            String className = paramClasses.get(i);
            String paramName = paramNames[i];
            launcherCm.addImport(className);
            String clazz = className.substring(className.lastIndexOf(".") + 1);
            launcherCm.addLines(launcherLinesIndex, ", " + clazz + " " + paramName);


            if (!passes.contains(paramName)) {
                passes.add(paramName);

                passCm.addImport(className);

                passCm.addLines(passLinesIndex, "\r\n");
                passCm.addLines(passLinesIndex, "  public static final String " + paramName.toUpperCase() + " = \"" + paramName + "\";\r\n");

                passCm.addLines(passMethodLinesIndex, "\r\n");
                passCm.addLines(passMethodLinesIndex, "  public static final " + clazz + " " + paramName + "(Intent data) {\r\n");
                passCm.addLines(passMethodLinesIndex, "    return JsonFunc.toBean(" + clazz + ".class, data.getStringExtra(" + paramName.toUpperCase() + "));\r\n");
                passCm.addLines(passMethodLinesIndex, "  }\r\n");

            }

            putExtras.add("        if ("+paramName+" != null)\r\n");
            putExtras.add("            intent.putExtra(Pass." + paramName.toUpperCase() + ", JsonFunc.toJson(" + paramName + "));\r\n");
        }

        launcherCm.addLines(launcherLinesIndex, ") {\r\n");
        launcherCm.addLines(launcherLinesIndex, "        Intent intent = new Intent(act, " + simpleName + ".class);\r\n");

        for (int i = 0; i < CountFunc.count(putExtras); i++) {
            launcherCm.addLines(launcherLinesIndex, putExtras.get(i));
        }

        launcherCm.addLines(launcherLinesIndex, "        ActFunc.startActivityForResult(act, intent, Code4Request." + staticName + ");\r\n");
        launcherCm.addLines(launcherLinesIndex, "  }\r\n");

        //////
        code4RequestCm.addLines(code4RequestLinesIndex, "\r\n");
        code4RequestCm.addLines(code4RequestLinesIndex, "  public static final int " + staticName + " = " + code4Request++ + ";\r\n");

    }

    private void createClassModel() {
        if (launcherCm == null) {
            launcherCm = new ClassModel(PACKAGE_CORE, "ActLuncher");

            //import
            launcherCm.importLinesIndex = launcherCm.createLines();
            launcherCm.addLines(launcherCm.importLinesIndex, "\r\n");
            launcherCm.addLines(launcherCm.importLinesIndex, "import android.app.Activity;\r\n");
            launcherCm.addLines(launcherCm.importLinesIndex, "import android.content.Intent;\r\n");
            launcherCm.addLines(launcherCm.importLinesIndex, "import " + PACKAGE_CORE + ".json.JsonFunc;\r\n");
            launcherCm.addLines(launcherCm.importLinesIndex, "import " + PACKAGE_CORE + ".funcs.ActFunc;\r\n");

            //class
            int classLinesIndex = launcherCm.createLines();
            launcherCm.addLines(classLinesIndex, "\r\n");
            launcherCm.addLines(classLinesIndex, "public class " + launcherCm.name + "{\r\n");

            //launcher
            launcherLinesIndex = launcherCm.createLines();

            //end
            launcherCm.createEndLines();
        }

        if (passCm == null) {
            passCm = new ClassModel(PACKAGE_CORE, "Pass");

            //import
            passCm.importLinesIndex = passCm.createLines();
            passCm.addLines(passCm.importLinesIndex, "\r\n");
            passCm.addLines(passCm.importLinesIndex, "import android.content.Intent;\r\n");
            passCm.addLines(passCm.importLinesIndex, "import " + PACKAGE_CORE + ".json.JsonFunc;\r\n");

            //class
            int classLinesIndex = passCm.createLines();
            passCm.addLines(classLinesIndex, "\r\n");
            passCm.addLines(classLinesIndex, "public class Pass {\r\n");

            //pass
            passLinesIndex = passCm.createLines();

            //passMethod
            passMethodLinesIndex = passCm.createLines();

            //end
            passCm.createEndLines();
        }

        if (code4RequestCm == null) {
            code4RequestCm = new ClassModel(PACKAGE_CORE, "Code4Request");

            //class
            int classLinesIndex = code4RequestCm.createLines();
            code4RequestCm.addLines(classLinesIndex, "\r\n");
            code4RequestCm.addLines(classLinesIndex, "public class Code4Request {\r\n");

            //code4Request
            code4RequestLinesIndex = code4RequestCm.createLines();

            //end
            code4RequestCm.createEndLines();

        }
    }

}
