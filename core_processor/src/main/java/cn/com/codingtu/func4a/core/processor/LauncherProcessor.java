package cn.com.codingtu.func4a.core.processor;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import cn.com.codingtu.func4a.core.processor.annotation.activity.Launcher;
import cn.com.codingtu.func4a.core.processor.funcs.ClassFunc;
import cn.com.codingtu.func4a.core.processor.model.ClassModel;
import cn.com.codingtu.func4j.CountFunc;
import cn.com.codingtu.func4j.ls.each.Each;

@AutoService(Processor.class)
public class LauncherProcessor extends BaseProcessor {
    private ClassModel launcherCm;
    private int launcherLinesIndex;

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

        launcherCm = null;
        return true;
    }

    private void dealLauncher(TypeElement te) {
        createClassModel();

        String fullName = te.getQualifiedName().toString();

        launcherCm.addImport(fullName);

        launcherCm.addLines(launcherLinesIndex, "\r\n");
        launcherCm.addLines(launcherLinesIndex, "  public static final void cookBookActivity(Activity act");

        Launcher launcher = te.getAnnotation(Launcher.class);

        ClassFunc.getAnnotationClasses(new ClassFunc.AnnotationClassGetter() {
            @Override
            public Object get() {
                return launcher.paramClasses();
            }
        }, new Each<String>() {
            @Override
            public boolean each(int position, String s) {

                log(s);

                return false;
            }
        });


//        Class[] classes = launcher.paramClasses();
//        String[] strings = launcher.paramNames();

//        for (int i = 0; i < CountFunc.count(classes); i++) {
//
//            log(classes[i].toString());
//
////            launcherCm.addLines(launcherLinesIndex, " , " + classes[i] + " " + strings[i]);
//        }

        launcherCm.addLines(launcherLinesIndex, ") {\r\n");
        launcherCm.addLines(launcherLinesIndex, "        Intent intent = new Intent(act, CookBookActivity.class);\r\n");
        launcherCm.addLines(launcherLinesIndex, "        intent.putExtra(Pass.COOKBOOK, JsonFunc.toJson(cookbook));\r\n");
        launcherCm.addLines(launcherLinesIndex, "        ActFunc.startActivityForResult(act, intent, Code4Request.COOK_BOOK_ACTIVITY);\r\n");
        launcherCm.addLines(launcherLinesIndex, "  }\r\n");


//        launcherCm.addLines(launcherLinesIndex, "\r\n");
//        launcherCm.addLines(launcherLinesIndex, "  public static final void addCategoryActivity(Activity act) {\r\n");
//        launcherCm.addLines(launcherLinesIndex, "        Intent intent = new Intent(act, AddCategoryActivity.class);\r\n");
//        launcherCm.addLines(launcherLinesIndex, "        ActFunc.startActivityForResult(act, intent, Code4Request.ADD_CATEGORY_ACTIVITY);\r\n");
//        launcherCm.addLines(launcherLinesIndex, "  }\r\n");
//
//
//        launcherCm.addLines(launcherLinesIndex, "\r\n");
//        launcherCm.addLines(launcherLinesIndex, "  public static final void addCookBookActivity(Activity act, CookBookCategory category) {\r\n");
//        launcherCm.addLines(launcherLinesIndex, "        Intent intent = new Intent(act, AddCookBookActivity.class);\r\n");
//        launcherCm.addLines(launcherLinesIndex, "        intent.putExtra(Pass.CATEGORY, JsonFunc.toJson(category));\r\n");
//        launcherCm.addLines(launcherLinesIndex, "        ActFunc.startActivityForResult(act, intent, Code4Request.ADD_COOK_BOOK_ACTIVITY);\r\n");
//        launcherCm.addLines(launcherLinesIndex, "  }\r\n");
//
//
//        launcherCm.addLines(launcherLinesIndex, "\r\n");
//        launcherCm.addLines(launcherLinesIndex, "  public static final void cookBookActivity(Activity act, CookBook cookbook) {\r\n");
//        launcherCm.addLines(launcherLinesIndex, "        Intent intent = new Intent(act, CookBookActivity.class);\r\n");
//        launcherCm.addLines(launcherLinesIndex, "        intent.putExtra(Pass.COOKBOOK, JsonFunc.toJson(cookbook));\r\n");
//        launcherCm.addLines(launcherLinesIndex, "        ActFunc.startActivityForResult(act, intent, Code4Request.COOK_BOOK_ACTIVITY);\r\n");
//        launcherCm.addLines(launcherLinesIndex, "  }\r\n");
//
//
//        launcherCm.addLines(launcherLinesIndex, "\r\n");
//        launcherCm.addLines(launcherLinesIndex, "  public static final void cookBookCategoriesActivity(Activity act) {\r\n");
//        launcherCm.addLines(launcherLinesIndex, "        Intent intent = new Intent(act, CookBookCategoriesActivity.class);\r\n");
//        launcherCm.addLines(launcherLinesIndex, "        ActFunc.startActivityForResult(act, intent, Code4Request.COOK_BOOK_CATEGORIES_ACTIVITY);\r\n");
//        launcherCm.addLines(launcherLinesIndex, "  }\r\n");
//
//
//        launcherCm.addLines(launcherLinesIndex, "\r\n");
//        launcherCm.addLines(launcherLinesIndex, "  public static final void cookBooksActivity(Activity act, CookBookCategory category) {\r\n");
//        launcherCm.addLines(launcherLinesIndex, "        Intent intent = new Intent(act, CookBooksActivity.class);\r\n");
//        launcherCm.addLines(launcherLinesIndex, "        intent.putExtra(Pass.CATEGORY, JsonFunc.toJson(category));\r\n");
//        launcherCm.addLines(launcherLinesIndex, "        ActFunc.startActivityForResult(act, intent, Code4Request.COOK_BOOKS_ACTIVITY);\r\n");
//        launcherCm.addLines(launcherLinesIndex, "  }\r\n");
//
//
//        launcherCm.addLines(launcherLinesIndex, "\r\n");
//        launcherCm.addLines(launcherLinesIndex, "  public static final void editCookBookActivity(Activity act, CookBook cookbook) {\r\n");
//        launcherCm.addLines(launcherLinesIndex, "        Intent intent = new Intent(act, EditCookBookActivity.class);\r\n");
//        launcherCm.addLines(launcherLinesIndex, "        intent.putExtra(Pass.COOKBOOK, JsonFunc.toJson(cookbook));\r\n");
//        launcherCm.addLines(launcherLinesIndex, "        ActFunc.startActivityForResult(act, intent, Code4Request.EDIT_COOK_BOOK_ACTIVITY);\r\n");
//        launcherCm.addLines(launcherLinesIndex, "  }\r\n");


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
    }

}
