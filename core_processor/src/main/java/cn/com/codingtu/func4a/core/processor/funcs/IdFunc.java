package cn.com.codingtu.func4a.core.processor.funcs;

import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeScanner;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import cn.com.codingtu.func4a.core.processor.model.ClassModel;
import cn.com.codingtu.func4j.StringFunc;

import static java.util.Objects.requireNonNull;

public class IdFunc {

    public static Trees trees;
    public static RScanner rScanner;


    public static Id elementToId(ClassModel cm, Element element, Class<? extends Annotation> annotation, int value) {
        Id id = elementToId(element, annotation, value);
        addImport(cm, id);
        return id;
    }

    public static Map<Integer, Id> elementToIds(ClassModel cm, Element element, Class<? extends Annotation> annotation,
                                                int[] values) {
        Map<Integer, Id> idMap = elementToIds(element, annotation, values);
        if (idMap != null) {
            for (int id : idMap.keySet()) {
                addImport(cm, idMap.get(id));

            }
        }
        return idMap;
    }

    private static void addImport(ClassModel cm, Id id) {
        if (id != null && StringFunc.isNotBlank(id.idPackage)) {
            cm.addLinesIfNotExist(cm.importLinesIndex, "import " + id.idPackage + ".R;\r\n");
        }
    }

    private static Id elementToId(Element element, Class<? extends Annotation> annotation, int value) {
        JCTree tree = (JCTree) trees.getTree(element, getMirror(element, annotation));
        if (tree != null) { // tree can be null if the references are compiled types and not source
            rScanner.reset();
            tree.accept(rScanner);
            if (!rScanner.resourceIds.isEmpty()) {
                return rScanner.resourceIds.values().iterator().next();
            }
        }
        return new Id(value);
    }

    private static Map<Integer, Id> elementToIds(Element element, Class<? extends Annotation> annotation,
                                                 int[] values) {
        Map<Integer, Id> resourceIds = new LinkedHashMap<>();
        JCTree tree = (JCTree) trees.getTree(element, getMirror(element, annotation));
        if (tree != null) { // tree can be null if the references are compiled types and not source
            rScanner.reset();
            tree.accept(rScanner);
            resourceIds = rScanner.resourceIds;
        }

        // Every value looked up should have an Id
        for (int value : values) {
            resourceIds.putIfAbsent(value, new Id(value));
        }
        return resourceIds;
    }


    private static AnnotationMirror getMirror(Element element,
                                              Class<? extends Annotation> annotation) {
        for (AnnotationMirror annotationMirror : element.getAnnotationMirrors()) {
            if (annotationMirror.getAnnotationType().toString().equals(annotation.getCanonicalName())) {
                return annotationMirror;
            }
        }
        return null;
    }

    public static class Id {
        public String idPackage;
        public String code;

        public Id(int value) {
            this(value, null);
        }

        public Id(int value, @Nullable Symbol rSymbol) {
            if (rSymbol != null) {
                this.idPackage = rSymbol.packge().getQualifiedName().toString();
                this.code = "R." + rSymbol.enclClass().name.toString() + "." + rSymbol.name.toString();
            } else {
                this.code = value + "";
            }
        }


    }

    public static class RScanner extends TreeScanner {
        Map<Integer, Id> resourceIds = new LinkedHashMap<>();

        @Override
        public void visitSelect(JCTree.JCFieldAccess jcFieldAccess) {
            Symbol symbol = jcFieldAccess.sym;
            if (symbol.getEnclosingElement() != null
                    && symbol.getEnclosingElement().getEnclosingElement() != null
                    && symbol.getEnclosingElement().getEnclosingElement().enclClass() != null) {
                try {
                    int value = (Integer) requireNonNull(((Symbol.VarSymbol) symbol).getConstantValue());
                    resourceIds.put(value, new Id(value, symbol));
                } catch (Exception ignored) {
                }
            }
        }

        @Override
        public void visitLiteral(JCTree.JCLiteral jcLiteral) {
            try {
                int value = (Integer) jcLiteral.value;
                resourceIds.put(value, new Id(value));
            } catch (Exception ignored) {
            }
        }

        void reset() {
            resourceIds.clear();
        }
    }

}
