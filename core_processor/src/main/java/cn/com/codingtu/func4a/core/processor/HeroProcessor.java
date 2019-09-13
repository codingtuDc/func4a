package cn.com.codingtu.func4a.core.processor;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.JavaFileObject;

import cn.com.codingtu.func4a.core.processor.annotation.onactivityresult.OnResult4Activity;
import cn.com.codingtu.func4a.core.processor.annotation.onclick.ClickTag;
import cn.com.codingtu.func4a.core.processor.annotation.onclick.ClickView;
import cn.com.codingtu.func4a.core.processor.annotation.permission.PermissionCheck;
import cn.com.codingtu.func4a.core.processor.annotation.view.FindView;
import cn.com.codingtu.func4a.core.processor.funcs.IdFunc;
import cn.com.codingtu.func4a.core.processor.model.ClassModel;
import cn.com.codingtu.func4j.CountFunc;
import cn.com.codingtu.func4j.StringFunc;
import cn.com.codingtu.func4j.ls.Ls;
import cn.com.codingtu.func4j.ls.each.Each;
import cn.com.codingtu.func4j.ls.each.MapEach;

@AutoService(Processor.class)
public class HeroProcessor extends BaseProcessor {

    public static final String PREX = "Hero";

    private Map<String, ClassModel> cms = new HashMap<>();
    private int fieldLI;
    private int constructorLI;
    private int onClickLinesIndex;
    private int onActivityResultLI;
    private int acceptLI;
    private int onPermissionsBackLI;

    @Override
    protected Class[] getSupportTypes() {
        return new Class[]{
                FindView.class,
                ClickView.class,
                ClickTag.class,
                PermissionCheck.class
        };
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        //FindView
        ls(roundEnvironment, FindView.class, new Each<Element>() {
            @Override
            public boolean each(int position, Element element) {
                if (element instanceof VariableElement) {
                    dealFindViewElement((VariableElement) element);
                }
                return false;
            }
        });

        //ClickView
        ls(roundEnvironment, ClickView.class, new Each<Element>() {
            @Override
            public boolean each(int position, Element element) {
                if (element instanceof ExecutableElement) {
                    dealClickViewMethodElement((ExecutableElement) element);
                }
                return false;
            }
        });

        //PermissionCheck
        ls(roundEnvironment, PermissionCheck.class, new Each<Element>() {
            @Override
            public boolean each(int position, Element element) {
                if (element instanceof ExecutableElement) {
                    dealPermissionCheck((ExecutableElement) element);
                }
                return false;
            }
        });

        Ls.ls(cms, new MapEach<String, ClassModel>() {
            @Override
            public boolean each(int position, String s, ClassModel cm) {
                createClass(cm, new DealClassModel() {
                    @Override
                    public void deal(ClassModel cm) {

                        List<String> onClickSubLines = cm.getSubLines(onClickLinesIndex);
                        int count = CountFunc.count(onClickSubLines);
                        if (count > 0) {
                            cm.addLines(onClickLinesIndex, "        switch (v.getId()) {\r\n");
                            for (int i = 0; i < count; i++) {
                                cm.addLines(onClickLinesIndex, onClickSubLines.get(i));
                            }
                            cm.addLines(onClickLinesIndex, "        }\r\n");
                        }


                    }
                });
                return false;
            }
        });

        cms.clear();

        return false;
    }


    private void dealPermissionCheck(ExecutableElement ee) {

        ClassModel cm = getClassModel((TypeElement) ee.getEnclosingElement());

        cm.addImport(PACKAGE_PERMISSION + ".Permissions");

        String methodName = ee.getSimpleName().toString();
        String checkIdName = StringFunc.getStaticName(methodName);

        cm.addLines(onPermissionsBackLI, "        if (requestCode == Permissions." + checkIdName + ") {\r\n");
        cm.addLines(onPermissionsBackLI, "            this.binder." + methodName + "(");

        List<? extends VariableElement> ps = ee.getParameters();
        int count = CountFunc.count(ps);
        for (int i = 0; i < count; i++) {
            VariableElement ve = ps.get(i);

            String typeStr = ve.asType().toString();

            if ("boolean".equals(typeStr)) {
                cm.addImport(PACKAGE_PERMISSION + ".PermissionFunc");
                cm.addLines(onPermissionsBackLI, "PermissionFunc.allow(grantResults)");
            } else if ("java.lang.String[]".equals(typeStr)) {
                cm.addLines(onPermissionsBackLI, "permissions");
            } else if ("int[]".equals(typeStr)) {
                cm.addLines(onPermissionsBackLI, "grantResults");
            }

            if (i < count - 1) {
                cm.addLines(onPermissionsBackLI, ", ");
            }

        }

        cm.addLines(onPermissionsBackLI, ");\r\n");
        cm.addLines(onPermissionsBackLI, "        }\r\n");
        cm.addLines(onPermissionsBackLI, "\r\n");

    }

    private void dealClickViewMethodElement(ExecutableElement ee) {
        ClassModel cm = getClassModel((TypeElement) ee.getEnclosingElement());

        int[] ids = ee.getAnnotation(ClickView.class).value();

        String mName = ee.getSimpleName().toString();

        List<? extends VariableElement> parameters = ee.getParameters();

        Map<Integer, IdFunc.Id> idMap = IdFunc.elementToIds(cm, ee, ClickView.class, ids);

        for (int i = 0; i < ids.length; i++) {
            IdFunc.Id id = idMap.get(ids[i]);
            cm.addSubLines(onClickLinesIndex, "            case " + id.code + ":\r\n");
            cm.addLines(constructorLI, "        view.findViewById(" + id.code + ").setOnClickListener(this);\r\n");
        }
        cm.addSubLines(onClickLinesIndex, "                this.binder." + mName + "(");

        int count = CountFunc.count(parameters);

        for (int i = 0; i < count; i++) {
            VariableElement ve = parameters.get(i);
            ClickTag clickTag = ve.getAnnotation(ClickTag.class);
            if (clickTag != null) {
                IdFunc.Id id = IdFunc.elementToId(cm, ve, ClickTag.class, clickTag.value());
                cm.addSubLines(onClickLinesIndex, "(" + ve.asType().toString() + ") v.getTag(" + id.code + ")");
            } else {
                String type = ve.asType().toString();
                if ("android.view.View".equals(type)) {
                    cm.addSubLines(onClickLinesIndex, "v");
                }
            }

            if (i < count - 1) {
                cm.addSubLines(onClickLinesIndex, ", ");
            }
        }

        cm.addSubLines(onClickLinesIndex, ");\r\n");
        cm.addSubLines(onClickLinesIndex, "                break;\r\n");

    }

    private void dealFindViewElement(VariableElement ve) {
        ClassModel cm = getClassModel((TypeElement) ve.getEnclosingElement());
        //String type = ve.asType().toString();
        String vname = ve.getSimpleName().toString();

        FindView findView = ve.getAnnotation(FindView.class);
        IdFunc.Id id = IdFunc.elementToId(cm, ve, FindView.class, findView.value());

        cm.addLines(constructorLI, "        this.binder." + vname + " = view.findViewById(" + id.code + ");\r\n");
    }

    private ClassModel getClassModel(TypeElement te) {
        String fullName = te.getQualifiedName().toString() + "_" + PREX;
        ClassModel cm = cms.get(fullName);
        if (cm == null) {
            cm = new ClassModel(mElementUtils, te, PREX);

            cm.importLinesIndex = cm.createLines();
            cm.addLines(cm.importLinesIndex, "\r\n");
            cm.addLines(cm.importLinesIndex, "import android.app.Activity;\r\n");
            cm.addLines(cm.importLinesIndex, "import android.content.Intent;\r\n");
            cm.addLines(cm.importLinesIndex, "import android.support.v4.app.Fragment;\r\n");
            cm.addLines(cm.importLinesIndex, "import android.view.View;\r\n");
            cm.addLines(cm.importLinesIndex, "import " + PACKAGE_CORE + ".hero.Hero;\r\n");
            cm.addLines(cm.importLinesIndex, "import okhttp3.ResponseBody;\r\n");
            cm.addLines(cm.importLinesIndex, "import retrofit2.adapter.rxjava2.Result;\r\n");

            int classLinesIndex = cm.createLines();
            cm.addLines(classLinesIndex, "\r\n");
            cm.addLines(classLinesIndex, "public class " + cm.name + " implements Hero {\r\n");

            fieldLI = cm.createLines();
            cm.addLines(fieldLI, "\r\n");
            cm.addLines(fieldLI, "    private " + cm.oriName + " binder;\r\n");

            constructorLI = cm.createLines();
            cm.addLines(constructorLI, "\r\n");
            cm.addLines(constructorLI, "    public " + cm.name + "(" + cm.oriName + " binder, View view) {\r\n");
            cm.addLines(constructorLI, "        this.binder = binder;\r\n");


            onClickLinesIndex = cm.createLines();
            cm.addLines(onClickLinesIndex, "    }\r\n");
            cm.addLines(onClickLinesIndex, "\r\n");
            cm.addLines(onClickLinesIndex, "    @Override\r\n");
            cm.addLines(onClickLinesIndex, "    public void onClick(View v) {\r\n");
            cm.addLines(onClickLinesIndex, "\r\n");


            onActivityResultLI = cm.createLines();
            cm.addLines(onActivityResultLI, "    }\r\n");
            cm.addLines(onActivityResultLI, "\r\n");
            cm.addLines(onActivityResultLI, "    @Override\r\n");
            cm.addLines(onActivityResultLI, "    public void onActivityResult(int requestCode, int resultCode, Intent data) {\r\n");
            cm.addLines(onActivityResultLI, "\r\n");


            acceptLI = cm.createLines();
            cm.addLines(acceptLI, "    }\r\n");
            cm.addLines(acceptLI, "\r\n");
            cm.addLines(acceptLI, "    @Override\r\n");
            cm.addLines(acceptLI, "    public void accept(String code, Result<ResponseBody> result) {\r\n");
            cm.addLines(acceptLI, "\r\n");


            int getActLI = cm.createLines();
            cm.addLines(getActLI, "    }\r\n");
            cm.addLines(getActLI, "\r\n");
            cm.addLines(getActLI, "    @Override\r\n");
            cm.addLines(getActLI, "    public Activity getAct() {\r\n");
            cm.addLines(getActLI, "        Object binder = this.binder;\r\n");
            cm.addLines(getActLI, "        if (binder instanceof Activity) {\r\n");
            cm.addLines(getActLI, "            return (Activity) binder;\r\n");
            cm.addLines(getActLI, "        } else if (binder instanceof Fragment) {\r\n");
            cm.addLines(getActLI, "            return ((Fragment) binder).getActivity();\r\n");
            cm.addLines(getActLI, "        }\r\n");
            cm.addLines(getActLI, "        return null;\r\n");
            cm.addLines(getActLI, "    }\r\n");

            onPermissionsBackLI = cm.createLines();
            cm.addLines(onPermissionsBackLI, "\r\n");
            cm.addLines(onPermissionsBackLI, "    @Override\r\n");
            cm.addLines(onPermissionsBackLI, "    public void onPermissionsBack(int requestCode, String[] permissions, int[] grantResults) {\r\n");
            cm.addLines(onPermissionsBackLI, "\r\n");

            int onPermissionsBackEndLI = cm.createLines();
            cm.addLines(onPermissionsBackEndLI, "    }\r\n");

            //end
            cm.createEndLines();

            cms.put(fullName, cm);
        }
        return cm;
    }

}
