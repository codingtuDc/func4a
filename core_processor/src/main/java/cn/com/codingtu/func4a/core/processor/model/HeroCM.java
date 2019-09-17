package cn.com.codingtu.func4a.core.processor.model;

import java.util.List;
import java.util.Map;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

import cn.com.codingtu.func4a.core.processor.BaseProcessor;
import cn.com.codingtu.func4a.core.processor.annotation.onactivityresult.OnResult4Activity;
import cn.com.codingtu.func4a.core.processor.annotation.onclick.ClickTag;
import cn.com.codingtu.func4a.core.processor.annotation.onclick.ClickView;
import cn.com.codingtu.func4a.core.processor.annotation.view.FindView;
import cn.com.codingtu.func4a.core.processor.funcs.ClassFunc;
import cn.com.codingtu.func4a.core.processor.funcs.IdFunc;
import cn.com.codingtu.func4j.CountFunc;
import cn.com.codingtu.func4j.StringFunc;

public class HeroCM extends ClassModel {

    public final int constructerLinesIndex;
    public final int clickLinesIndex;
    public final int resultLinesIndex;
    public final int acceptLinesIndex;
    public final int permissionLinesIndex;
    public final int constructerEndLinesIndex;
    public final int clickEndLinesIndex;
    private boolean isInitOnClick;

    public HeroCM(Elements elementUtils, TypeElement te, String prex) {
        super(elementUtils, te, prex);

        //import
        importLinesIndex = createLines();
        addLines(importLinesIndex, "\r\n");
        addLines(importLinesIndex, "import android.app.Activity;\r\n");
        addLines(importLinesIndex, "import android.content.Intent;\r\n");
        addLines(importLinesIndex, "import android.support.v4.app.Fragment;\r\n");
        addLines(importLinesIndex, "import android.view.View;\r\n");
        addLines(importLinesIndex, "import " + BaseProcessor.PACKAGE_CORE + ".hero.Hero;\r\n");
        addLines(importLinesIndex, "import okhttp3.ResponseBody;\r\n");
        addLines(importLinesIndex, "import retrofit2.adapter.rxjava2.Result;\r\n");

        //class
        int classLinesIndex = createLines();
        addLines(classLinesIndex, "\r\n");
        addLines(classLinesIndex, "public class " + name + " implements Hero {\r\n");
        addLines(classLinesIndex, "\r\n");
        addLines(classLinesIndex, "    private " + oriName + " binder;\r\n");
        addLines(classLinesIndex, "\r\n");
        addLines(classLinesIndex, "    public " + name + "(" + oriName + " binder, View view) {\r\n");
        addLines(classLinesIndex, "        this.binder = binder;\r\n");

        //constructer
        constructerLinesIndex = createLines();

        //constructerEnd
        constructerEndLinesIndex = createLines();
        addLines(constructerEndLinesIndex, "    }\r\n");
        addLines(constructerEndLinesIndex, "\r\n");
        addLines(constructerEndLinesIndex, "    @Override\r\n");
        addLines(constructerEndLinesIndex, "    public void onClick(View v) {\r\n");

        //click
        clickLinesIndex = createLines();

        //clickEnd
        clickEndLinesIndex = createLines();
        addLines(clickEndLinesIndex, "    }\r\n");
        addLines(clickEndLinesIndex, "\r\n");
        addLines(clickEndLinesIndex, "    @Override\r\n");
        addLines(clickEndLinesIndex, "    public void onActivityResult(int requestCode, int resultCode, Intent data) {\r\n");


        //result
        resultLinesIndex = createLines();

        //resultEnd
        int resultEndLinesIndex = createLines();
        addLines(resultEndLinesIndex, "    }\r\n");
        addLines(resultEndLinesIndex, "\r\n");
        addLines(resultEndLinesIndex, "    @Override\r\n");
        addLines(resultEndLinesIndex, "    public void accept(String code, Result<ResponseBody> result) {\r\n");


        //accept
        acceptLinesIndex = createLines();

        //acceptEnd
        int acceptEndLinesIndex = createLines();
        addLines(acceptEndLinesIndex, "    }\r\n");
        addLines(acceptEndLinesIndex, "\r\n");
        addLines(acceptEndLinesIndex, "    @Override\r\n");
        addLines(acceptEndLinesIndex, "    public Activity getAct() {\r\n");
        addLines(acceptEndLinesIndex, "        Object binder = this.binder;\r\n");
        addLines(acceptEndLinesIndex, "        if (binder instanceof Activity) {\r\n");
        addLines(acceptEndLinesIndex, "            return (Activity) binder;\r\n");
        addLines(acceptEndLinesIndex, "        } else if (binder instanceof Fragment) {\r\n");
        addLines(acceptEndLinesIndex, "            return ((Fragment) binder).getActivity();\r\n");
        addLines(acceptEndLinesIndex, "        }\r\n");
        addLines(acceptEndLinesIndex, "        return null;\r\n");
        addLines(acceptEndLinesIndex, "    }\r\n");
        addLines(acceptEndLinesIndex, "\r\n");
        addLines(acceptEndLinesIndex, "    @Override\r\n");
        addLines(acceptEndLinesIndex, "    public void onPermissionsBack(int requestCode, String[] permissions, int[] grantResults) {\r\n");


        //permission
        permissionLinesIndex = createLines();

        //permissionEnd
        int permissionEndLinesIndex = createLines();
        addLines(permissionEndLinesIndex, "\r\n");
        addLines(permissionEndLinesIndex, "    }\r\n");
        addLines(permissionEndLinesIndex, "\r\n");

        //end
        createEndLines();
    }

    public void addConstructer(VariableElement ve) {

        FindView findView = ve.getAnnotation(FindView.class);
        IdFunc.Id id = IdFunc.elementToId(this, ve, FindView.class, findView.value());
        String vname = ve.getSimpleName().toString();

        addLines(constructerLinesIndex, "        this.binder." + vname + " = view.findViewById(" + id.code + ");\r\n");
    }

    public void addConstructer(IdFunc.Id id) {
        addLines(constructerLinesIndex, "        view.findViewById(" + id.code + ").setOnClickListener(this);\r\n");
    }


    public void initOnClick() {
        if (!isInitOnClick) {
            isInitOnClick = true;

            addLines(constructerEndLinesIndex, "\r\n");
            addLines(constructerEndLinesIndex, "        switch (v.getId()) {\r\n");

            addLines(clickEndLinesIndex, 0, "        }\r\n");

        }
    }

    public void addClick(ExecutableElement ee) {

        initOnClick();
        int[] ids = ee.getAnnotation(ClickView.class).value();
        String mName = ee.getSimpleName().toString();
        Map<Integer, IdFunc.Id> idMap = IdFunc.elementToIds(this, ee, ClickView.class, ids);


        for (IdFunc.Id id : idMap.values()) {
            addLines(clickLinesIndex, "            case " + id.code + ":\r\n");
            addConstructer(id);
        }
        addLines(clickLinesIndex, "                this.binder." + mName + "(");

        List<? extends VariableElement> parameters = ee.getParameters();

        int count = CountFunc.count(parameters);

        for (int i = 0; i < count; i++) {
            VariableElement ve = parameters.get(i);
            ClickTag clickTag = ve.getAnnotation(ClickTag.class);
            if (clickTag != null) {
                IdFunc.Id id = IdFunc.elementToId(this, ve, ClickTag.class, clickTag.value());
                addLines(clickLinesIndex, "(" + ve.asType().toString() + ") v.getTag(" + id.code + ")");
            } else {
                String type = ve.asType().toString();
                if ("android.view.View".equals(type)) {
                    addLines(clickLinesIndex, "v");
                }
            }

            if (i < count - 1) {
                addLines(clickLinesIndex, ", ");
            }
        }

        addLines(clickLinesIndex, ");\r\n");
        addLines(clickLinesIndex, "                break;\r\n");

    }

    public void addClick(Map<Integer, IdFunc.Id> idMap, String mName, List<? extends VariableElement> parameters) {
        for (IdFunc.Id id : idMap.values()) {
            addLines(clickLinesIndex, "            case " + id.code + ":\r\n");
            addConstructer(id);
        }
        addLines(clickLinesIndex, "                this.binder." + mName + "(");

        int count = CountFunc.count(parameters);

        for (int i = 0; i < count; i++) {
            VariableElement ve = parameters.get(i);
            ClickTag clickTag = ve.getAnnotation(ClickTag.class);
            if (clickTag != null) {
                IdFunc.Id id = IdFunc.elementToId(this, ve, ClickTag.class, clickTag.value());
                addLines(clickLinesIndex, "(" + ve.asType().toString() + ") v.getTag(" + id.code + ")");
            } else {
                String type = ve.asType().toString();
                if ("android.view.View".equals(type)) {
                    addLines(clickLinesIndex, "v");
                }
            }

            if (i < count - 1) {
                addLines(clickLinesIndex, ", ");
            }
        }

        addLines(clickLinesIndex, ");\r\n");
        addLines(clickLinesIndex, "                break;\r\n");

    }


    public void addResult(ExecutableElement ee) {
        OnResult4Activity onResult = ee.getAnnotation(OnResult4Activity.class);
        boolean isDeal = onResult.isDeal();

        List<String> annotationClasses = ClassFunc.getAnnotationClasses(new ClassFunc.AnnotationClassGetter() {
            @Override
            public Object get() {
                return onResult.value();
            }
        });

        addLines(resultLinesIndex, "        if (requestCode == Code4Request.TWO_ACTIVITY) {\r\n");
        addLines(resultLinesIndex, "            if (resultCode == Activity.RESULT_OK) {\r\n");
        addLines(resultLinesIndex, "                this.binder.aaa(Pass.user(data));\r\n");
        addLines(resultLinesIndex, "            }\r\n");
        addLines(resultLinesIndex, "        }\r\n");
    }

    public void addAccept() {
        addLines(acceptLinesIndex, "\r\n");
    }


    public void addPermission(ExecutableElement ee) {
        addImport(BaseProcessor.PACKAGE_PERMISSION + ".Permissions");

        String methodName = ee.getSimpleName().toString();
        String checkIdName = StringFunc.getStaticName(methodName);

        addLines(permissionLinesIndex, "        if (requestCode == Permissions." + checkIdName + ") {\r\n");
        addLines(permissionLinesIndex, "            this.binder." + methodName + "(");

        List<? extends VariableElement> ps = ee.getParameters();
        int count = CountFunc.count(ps);
        for (int i = 0; i < count; i++) {
            VariableElement ve = ps.get(i);

            String typeStr = ve.asType().toString();

            if ("boolean".equals(typeStr)) {
                addImport(BaseProcessor.PACKAGE_PERMISSION + ".PermissionFunc");
                addLines(permissionLinesIndex, "PermissionFunc.allow(grantResults)");
            } else if ("java.lang.String[]".equals(typeStr)) {
                addLines(permissionLinesIndex, "permissions");
            } else if ("int[]".equals(typeStr)) {
                addLines(permissionLinesIndex, "grantResults");
            }

            if (i < count - 1) {
                addLines(permissionLinesIndex, ", ");
            }

        }

        addLines(permissionLinesIndex, ");\r\n");
        addLines(permissionLinesIndex, "        }\r\n");
        addLines(permissionLinesIndex, "\r\n");
    }

}
