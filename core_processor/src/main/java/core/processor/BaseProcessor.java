package core.processor;

import com.sun.source.util.Trees;

import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import cn.com.codingtu.func4j.ls.Ls;
import cn.com.codingtu.func4j.ls.each.Each;
import core.processor.funcs.IdFunc;
import core.processor.model.ClassModel;

public abstract class BaseProcessor extends AbstractProcessor {

    protected Messager mMessager;
    protected Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mMessager = processingEnv.getMessager();
        mElementUtils = processingEnv.getElementUtils();
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


    protected void ls(RoundEnvironment roundEnvironment, Class clazz, Each<Element> each) {
        Ls.ls(roundEnvironment.getElementsAnnotatedWith(clazz), each);
    }

    protected void log(String msg) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, msg);
    }

    protected void createClass(ClassModel cm) {
        if (cm == null)
            return;

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

    public static interface DealClassModel {
        public void deal(ClassModel cm);
    }


    protected abstract Class[] getSupportTypes();
}
