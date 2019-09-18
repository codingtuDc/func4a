package cn.com.codingtu.func4a.core.processor.model;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import cn.com.codingtu.func4a.core.processor.BaseProcessor;
import cn.com.codingtu.func4a.core.processor.annotation.net.BaseUrl;
import cn.com.codingtu.func4j.CountFunc;
import cn.com.codingtu.func4j.StringFunc;

public class NetCM extends ClassModel {

    public final int baseUrlLinesIndex;
    public final int methodNameLinesIndex;
    public final int methodLinesIndex;
    private List<String> baseUrls = new ArrayList<String>();
    private List<String> methodNames = new ArrayList<String>();

    public NetCM() {
        super(BaseProcessor.PACKAGE_CORE + ".net", "Net");

        //import
        importLinesIndex = createLines();
        addLines(importLinesIndex, "\r\n");
        addLines(importLinesIndex, "import io.reactivex.Flowable;\r\n");
        addLines(importLinesIndex, "import okhttp3.ResponseBody;\r\n");
        addLines(importLinesIndex, "import retrofit2.Retrofit;\r\n");
        addLines(importLinesIndex, "import retrofit2.adapter.rxjava2.Result;\r\n");

        //class
        int classLinesIndex = createLines();
        addLines(classLinesIndex, "\r\n");
        addLines(classLinesIndex, "public final class " + name + " {\r\n");

        //baseUrl
        baseUrlLinesIndex = createLines();

        //methodName
        methodNameLinesIndex = createLines();

        //method
        methodLinesIndex = createLines();

        //end
        createEndLines();
    }

    public void addNet(TypeElement te) {
        BaseUrl baseUrl = te.getAnnotation(BaseUrl.class);

        addBaseUrl(baseUrl);

        List<? extends Element> es = te.getEnclosedElements();
        for (int i = 0; i < CountFunc.count(es); i++) {
            Element e = es.get(i);
            if (e instanceof ExecutableElement) {
                ExecutableElement ee = (ExecutableElement) e;
                addMethodName(ee);
                addMethod(te, baseUrl, ee);
            }
        }
    }


    public void addBaseUrl(BaseUrl baseUrl) {
        if (baseUrl == null)
            return;

        if (!baseUrls.contains(baseUrl.value())) {
            baseUrls.add(baseUrl.value());
            addLines(baseUrlLinesIndex, "\r\n");
            addLines(baseUrlLinesIndex, "  private static final String BASE_URL_" + (baseUrls.size() - 1) + " = \"" + baseUrl.value() + "\";\r\n");
        }

    }

    public void addMethodName(ExecutableElement ee) {
        String simpleName = ee.getSimpleName().toString();
        String staticName = StringFunc.getStaticName(simpleName);

        if (!methodNames.contains(staticName)) {
            methodNames.add(staticName);
            addLines(methodNameLinesIndex, "\r\n");
            addLines(methodNameLinesIndex, "  public static final String " + staticName + " = \"" + simpleName + "Back\";\r\n");
        }

    }

    public void addMethod(TypeElement te, BaseUrl baseUrl, ExecutableElement ee) {
        String apiClassName = te.asType().toString();
        String apiSimpleName = StringFunc.getSimpleName(apiClassName);

        addImport(apiClassName);

        String simpleName = ee.getSimpleName().toString();
        String staticName = StringFunc.getStaticName(simpleName);


        addLines(methodLinesIndex, "\r\n");
        addLines(methodLinesIndex, "  public static API " + simpleName + "(");

        List<? extends VariableElement> parameters = ee.getParameters();

        int count = CountFunc.count(parameters);
        StringBuilder sbPs = new StringBuilder();
        StringBuilder sbVs = new StringBuilder();
        for (int i = 0; i < count; i++) {
            VariableElement ve = parameters.get(i);
            String className = ve.asType().toString();
            String vClassName = StringFunc.getSimpleName(className);
            String vName = ve.getSimpleName().toString();

            addImport(className);
            if (i == 0) {
                sbPs.append("final " + vClassName + " " + vName);
                sbVs.append(vName);
            } else {
                sbPs.append(", final " + vClassName + " " + vName);
                sbVs.append(", " + vName);
            }
        }

        addLines(methodLinesIndex, sbPs.toString());

        addLines(methodLinesIndex, ") {\r\n");
        addLines(methodLinesIndex, "    return NetUtils.api(new NetUtils.CreateApi() {\r\n");
        addLines(methodLinesIndex, "      @Override\r\n");
        addLines(methodLinesIndex, "      public Flowable<Result<ResponseBody>> create(Retrofit retrofit) {\r\n");
        addLines(methodLinesIndex, "        return retrofit.create(" + apiSimpleName + ".class)." + simpleName + "(");

        addLines(methodLinesIndex, sbVs.toString());

        addLines(methodLinesIndex, ");\r\n");
        addLines(methodLinesIndex, "      }\r\n");
        addLines(methodLinesIndex, "    }, " + staticName);
        if (baseUrl != null) {
            addLines(methodLinesIndex, ", BASE_URL_" + baseUrls.indexOf(baseUrl.value()));
        }
        addLines(methodLinesIndex, ");\r\n");
        addLines(methodLinesIndex, "  }\r\n");

    }

}
