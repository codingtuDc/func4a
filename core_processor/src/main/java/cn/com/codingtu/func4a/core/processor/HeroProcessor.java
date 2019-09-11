package cn.com.codingtu.func4a.core.processor;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

import cn.com.codingtu.func4a.core.processor.annotation.view.FindView;
import cn.com.codingtu.func4a.core.processor.funcs.ClassFunc;
import cn.com.codingtu.func4a.core.processor.model.ClassModel;
import cn.com.codingtu.func4j.CountFunc;
import cn.com.codingtu.func4j.ls.Ls;
import cn.com.codingtu.func4j.ls.each.Each;
import cn.com.codingtu.func4j.ls.each.MapEach;

@AutoService(Processor.class)
public class HeroProcessor extends BaseProcessor {

    public static final String PREX = "Hero";

    private Map<String, ClassModel> cms = new HashMap<>();

    @Override
    protected Class[] getSupportTypes() {
        return new Class[]{
                FindView.class
        };
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(FindView.class);

        Ls.ls(elements, new Each<Element>() {
            @Override
            public boolean each(int position, Element element) {
                if (element instanceof VariableElement) {
                    dealFindViewElement((VariableElement) element);
                }
                return false;
            }
        });

        Ls.ls(cms, new MapEach<String, ClassModel>() {
            @Override
            public boolean each(int position, String s, ClassModel cm) {
                createClass(cm);
                return false;
            }
        });

        cms.clear();

        return false;
    }

    private void createClass(ClassModel cm) {
        try {

            JavaFileObject jfo = processingEnv.getFiler().createSourceFile(cm.fullName);
            Writer writer = jfo.openWriter();

            StringBuffer sb = new StringBuffer();

            sb.append("package " + cm.packages + ";\r\n");
            sb.append("\r\n");
            sb.append("import android.app.Activity;\r\n");
            sb.append("import android.content.Intent;\r\n");
            sb.append("import android.support.v4.app.Fragment;\r\n");
            sb.append("import android.view.View;\r\n");
            sb.append("\r\n");
            sb.append("import cn.com.codingtu.func4a.core.hero.Hero;\r\n");
            sb.append("import okhttp3.ResponseBody;\r\n");
            sb.append("import retrofit2.adapter.rxjava2.Result;\r\n");
            sb.append("\r\n");
            sb.append("public class " + cm.name + " implements Hero {\r\n");
            sb.append("\r\n");
            sb.append("    private " + cm.oriName + " binder;\r\n");
            sb.append("\r\n");
            sb.append("    public " + cm.name + "(" + cm.oriName + " binder, View view) {\r\n");
            sb.append("        this.binder = binder;\r\n");


            Ls.ls(cm.constructorLines, new Each<String>() {
                @Override
                public boolean each(int position, String s) {
                    sb.append(s);
                    return false;
                }
            });


            sb.append("    }\r\n");
            sb.append("\r\n");
            sb.append("    @Override\r\n");
            sb.append("    public void onClick(View v) {\r\n");
            sb.append("\r\n");
            sb.append("    }\r\n");
            sb.append("\r\n");
            sb.append("    @Override\r\n");
            sb.append("    public void onActivityResult(int requestCode, int resultCode, Intent data) {\r\n");
            sb.append("\r\n");
            sb.append("    }\r\n");
            sb.append("\r\n");
            sb.append("    @Override\r\n");
            sb.append("    public void accept(String code, Result<ResponseBody> result) {\r\n");
            sb.append("\r\n");
            sb.append("    }\r\n");
            sb.append("\r\n");
            sb.append("    @Override\r\n");
            sb.append("    public Activity getAct() {\r\n");
            sb.append("        Object binder = this.binder;\r\n");
            sb.append("        if (binder instanceof Activity) {\r\n");
            sb.append("            return (Activity) binder;\r\n");
            sb.append("        } else if (binder instanceof Fragment) {\r\n");
            sb.append("            return ((Fragment) binder).getActivity();\r\n");
            sb.append("        }\r\n");
            sb.append("        return null;\r\n");
            sb.append("    }\r\n");
            sb.append("\r\n");
            sb.append("    @Override\r\n");
            sb.append("    public void onPermissionsBack(int requestCode, String[] permissions, int[] grantResults) {\r\n");
            sb.append("\r\n");
            sb.append("    }\r\n");
            sb.append("}\r\n");

            writer.write(sb.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log(e.getMessage());

        }
    }

    private void dealFindViewElement(VariableElement ve) {
        ClassModel cm = getClassModel(ve);
        //String type = ve.asType().toString();
        String vname = ve.getSimpleName().toString();
        int id = ve.getAnnotation(FindView.class).value();
        cm.addConstructorLine("        this.binder." + vname + " = view.findViewById(" + id + ");\r\n");
    }

    private ClassModel getClassModel(VariableElement ve) {
        TypeElement te = (TypeElement) ve.getEnclosingElement();
        String fullName = te.getQualifiedName().toString() + "_" + PREX;
        ClassModel cm = cms.get(fullName);
        if (cm == null) {
            cm = new ClassModel(mElementUtils, te, PREX);
            cms.put(fullName, cm);
        }
        return cm;
    }


}
