package cn.com.codingtu.func4a.core.processor;

import com.sun.source.util.Trees;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import cn.com.codingtu.func4a.core.processor.funcs.IdFunc;

import static java.util.Objects.requireNonNull;

public abstract class BaseProcessor extends AbstractProcessor {

    protected Messager mMessager;
    protected Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();
        mElementUtils = processingEnv.getElementUtils();
        log("init");
        IdFunc.trees = Trees.instance(processingEnv);
        IdFunc.rScanner = new IdFunc.RScanner();
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
