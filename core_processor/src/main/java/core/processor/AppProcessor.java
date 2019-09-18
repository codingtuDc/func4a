package core.processor;

import com.google.auto.service.AutoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import cn.com.codingtu.func4j.ls.Ls;
import cn.com.codingtu.func4j.ls.each.Each;
import cn.com.codingtu.func4j.ls.each.MapEach;
import core.processor.annotation.activity.Launcher;
import core.processor.annotation.net.Api;
import core.processor.annotation.net.NetBack;
import core.processor.annotation.onactivityresult.OnResult4Activity;
import core.processor.annotation.onclick.ClickTag;
import core.processor.annotation.onclick.ClickView;
import core.processor.annotation.permission.PermissionCheck;
import core.processor.annotation.view.FindView;
import core.processor.funcs.ClassFunc;
import core.processor.model.Code4RequestCM;
import core.processor.model.HeroCM;
import core.processor.model.LauncherCM;
import core.processor.model.NetCM;
import core.processor.model.PassCM;
import core.processor.model.PermissionCM;

import static core.processor.StaticData.PREX_HERO;

@AutoService(Processor.class)
public class AppProcessor extends BaseProcessor {

    private Map<String, HeroCM> heros = new HashMap<String, HeroCM>();
    private LauncherCM launcherCM;
    private PassCM passCM;
    private Code4RequestCM code4RequestCM;
    private PermissionCM permissionCM;
    private NetCM netCM;


    @Override
    protected Class[] getSupportTypes() {
        return new Class[]{
                FindView.class,
                ClickView.class,
                ClickTag.class,
                PermissionCheck.class,
                Launcher.class,
                OnResult4Activity.class,
                Api.class,
                NetBack.class
        };
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        launcherCM = new LauncherCM();
        passCM = new PassCM();
        code4RequestCM = new Code4RequestCM();
        permissionCM = new PermissionCM();
        netCM = new NetCM();
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
                    dealPermissionCheck(position, (ExecutableElement) element);
                }
                return false;
            }
        });
        //OnResult4Activity
        ls(roundEnvironment, OnResult4Activity.class, new Each<Element>() {
            @Override
            public boolean each(int position, Element element) {
                if (element instanceof ExecutableElement) {
                    dealOnResult4Activity(position, (ExecutableElement) element);
                }
                return false;
            }
        });
        //Net
        ls(roundEnvironment, Api.class, new Each<Element>() {
            @Override
            public boolean each(int position, Element element) {
                if (element instanceof TypeElement) {
                    dealApi((TypeElement) element);
                }
                return false;
            }
        });
        //NetBack
        ls(roundEnvironment, NetBack.class, new Each<Element>() {
            @Override
            public boolean each(int position, Element element) {
                if (element instanceof ExecutableElement) {
                    dealNetBack((ExecutableElement) element);
                }
                return false;
            }
        });
        //Launcher
        ls(roundEnvironment, Launcher.class, new Each<Element>() {
            @Override
            public boolean each(int position, Element element) {
                if (element instanceof TypeElement) {
                    dealLauncher(((TypeElement) element));
                }
                return false;
            }

        });

        //create
        Ls.ls(heros, new MapEach<String, HeroCM>() {
            @Override
            public boolean each(int position, String s, HeroCM cm) {
                createClass(cm);
                return false;
            }
        });
        createClass(launcherCM);
        createClass(passCM);
        createClass(code4RequestCM);
        createClass(permissionCM);
        createClass(netCM);

        //clear
        heros.clear();
        launcherCM = null;
        passCM = null;
        code4RequestCM = null;
        permissionCM = null;
        netCM = null;

        return true;
    }


    private void dealFindViewElement(VariableElement ve) {
        getHeroCM(ve).addConstructer(ve);
    }

    private void dealClickViewMethodElement(ExecutableElement ee) {
        getHeroCM(ee).addClick(ee);
    }


    private void dealPermissionCheck(int position, ExecutableElement ee) {
        getHeroCM(ee).addPermission(ee);
        permissionCM.addPermission(position, ee);
    }

    private void dealOnResult4Activity(int position, ExecutableElement ee) {
        getHeroCM(ee).addResult(ee);
        passCM.addOnResult(ee);
    }

    private void dealApi(TypeElement te) {
        netCM.addNet(te);
    }

    private void dealNetBack(ExecutableElement ee) {
        getHeroCM(ee).addAccept(ee);
    }

    private void dealLauncher(TypeElement te) {
        Launcher launcher = te.getAnnotation(Launcher.class);
        List<String> paramClasses = ClassFunc.getAnnotationClasses(new ClassFunc.AnnotationClassGetter() {
            @Override
            public Object get() {
                return launcher.paramClasses();
            }
        });
        passCM.addPass(paramClasses, launcher.paramNames());
        launcherCM.addLauncher(te.getQualifiedName().toString(), paramClasses, launcher.paramNames());
        code4RequestCM.addCode(te.getSimpleName().toString());
    }


    /*******************************************
     *
     * 获取HeroCM的方法
     *
     *******************************************/
    private HeroCM getHeroCM(VariableElement ve) {
        return getHeroCM((TypeElement) ve.getEnclosingElement());
    }

    private HeroCM getHeroCM(ExecutableElement ee) {
        return getHeroCM((TypeElement) ee.getEnclosingElement());
    }

    private HeroCM getHeroCM(TypeElement te) {
        String fullName = te.getQualifiedName().toString() + "_" + PREX_HERO;
        HeroCM hero = heros.get(fullName);
        if (hero == null) {
            hero = new HeroCM(mElementUtils, te, PREX_HERO);
            heros.put(fullName, hero);
        }
        return hero;
    }

}
