package cn.com.codingtu.func4a.core.processor;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

public abstract class BaseProcessor extends AbstractProcessor {

    protected Messager mMessager;
    protected Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();
        mElementUtils = processingEnv.getElementUtils();
        log("init");
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        Class[] classes = getSupportTypes();
        int size = classes != null ? classes.length : 0;
        for (int i = 0; i < size; i++) {
            supportTypes.add(classes[i].getCanonicalName());
        }
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    protected void log(String msg) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, msg);
    }

    protected abstract Class[] getSupportTypes();
}
