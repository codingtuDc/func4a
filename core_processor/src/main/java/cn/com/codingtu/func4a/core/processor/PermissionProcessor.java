package cn.com.codingtu.func4a.core.processor;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

import cn.com.codingtu.func4a.core.processor.annotation.permission.PermissionCheck;
import cn.com.codingtu.func4a.core.processor.model.ClassModel;
import cn.com.codingtu.func4j.CountFunc;
import cn.com.codingtu.func4j.StringFunc;
import cn.com.codingtu.func4j.ls.each.Each;

@AutoService(Processor.class)
public class PermissionProcessor extends BaseProcessor {
    private ClassModel cm;
    private int fieldLinesIndex;
    private int checkLinesIndex;

    @Override
    protected Class[] getSupportTypes() {
        return new Class[]{
                PermissionCheck.class
        };
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        ls(roundEnvironment, PermissionCheck.class, new Each<Element>() {
            @Override
            public boolean each(int position, Element element) {
                if (element instanceof ExecutableElement) {
                    dealPermissionCheck(position, (ExecutableElement) element);
                }
                return false;
            }
        });

        if (cm != null) {
            createClass(cm);
        }

        cm = null;

        return true;
    }

    private void dealPermissionCheck(int position, ExecutableElement ee) {

        ClassModel cm = getClassModel();

        PermissionCheck permissionCheck = ee.getAnnotation(PermissionCheck.class);

        String methodName = ee.getSimpleName().toString();
        String checkIdName = StringFunc.getStaticName(methodName);

        cm.addLines(fieldLinesIndex, "  public static final int " + checkIdName + " = " + position + ";\r\n");

        if (!permissionCheck.isForce()) {
            cm.addLines(fieldLinesIndex, "  public static final String CACHE_" + checkIdName + " = \"permission_" + checkIdName.toLowerCase() + "\";\r\n");
        }


        cm.addLines(checkLinesIndex, "\r\n");
        cm.addLines(checkLinesIndex, "  public static void " + methodName + "(PermissionHelper helper) {\r\n");
        cm.addLines(checkLinesIndex, "    PermissionFunc.check(helper, ");

        if (!permissionCheck.isForce()) {
            cm.addLines(checkLinesIndex, "CACHE_" + checkIdName + ", ");
        }

        cm.addLines(checkLinesIndex, checkIdName + ",\r\n");
        cm.addLines(checkLinesIndex, "       new String[]{");

        String[] value = permissionCheck.value();

        for (int i = 0; i < CountFunc.count(value); i++) {
            cm.addLines(checkLinesIndex, "\"" + value[i] + "\"");
        }

        cm.addLines(checkLinesIndex, "});\r\n");
        cm.addLines(checkLinesIndex, "  }\r\n");

    }

    private ClassModel getClassModel() {

        if (cm == null) {
            cm = new ClassModel(PACKAGE_PERMISSION, "Permissions");

            int packageLinesIndex = cm.createLines();
            cm.addLines(packageLinesIndex, "package " + cm.packages + ";\r\n");
            int classLinesIndex = cm.createLines();
            cm.addLines(classLinesIndex, "\r\n");
            cm.addLines(classLinesIndex, "public class " + cm.name + " {\r\n");
            fieldLinesIndex = cm.createLines();
            cm.addLines(fieldLinesIndex, "\r\n");

            checkLinesIndex = cm.createLines();

            int endLinesIndex = cm.createLines();
            cm.addLines(endLinesIndex, "}\r\n");
        }

        return cm;
    }

    private void createClass(ClassModel cm) {
        try {
            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(cm.fullName);
            Writer writer = jfo.openWriter();
            writer.write(cm.getLines());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log(e.getMessage());

        }
    }
}
