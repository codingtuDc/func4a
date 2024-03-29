package core.processor.model;

import javax.lang.model.element.ExecutableElement;

import core.processor.BaseProcessor;
import core.processor.annotation.permission.PermissionCheck;
import func4j.CountFunc;
import func4j.StringFunc;

import static core.processor.StaticData.CLASS_NAME_PERMISSIONS;
import static core.processor.StaticData.PACKAGE_PERMISSION;

public class PermissionCM extends ClassModel {

    public final int checkLinesIndex;
    public final int cacheLinesIndex;
    public final int methodLinesIndex;
    private String checkIdName;
    private String methodName;

    public PermissionCM() {
        super(PACKAGE_PERMISSION, CLASS_NAME_PERMISSIONS);


        //class
        int classLinesIndex = createLines();
        addLines(classLinesIndex, "\r\n");
        addLines(classLinesIndex, "public class " + name + " {\r\n");
        addLines(classLinesIndex, "\r\n");

        //check
        checkLinesIndex = createLines();

        //cache
        cacheLinesIndex = createLines();

        //method
        methodLinesIndex = createLines();

        //end
        createEndLines();
    }

    public void addPermission(int position, ExecutableElement ee) {
        PermissionCheck permissionCheck = ee.getAnnotation(PermissionCheck.class);
        methodName = ee.getSimpleName().toString();
        checkIdName = StringFunc.getStaticName(methodName);

        addCheck(position);
        if (!permissionCheck.isForce()) {
            addCache();
        }
        addMethod(permissionCheck);
    }

    public void addCheck(int position) {
        addLines(checkLinesIndex, "  public static final int " + checkIdName + " = " + position + ";\r\n");
    }

    public void addCache() {
        addLines(cacheLinesIndex, "  public static final String CACHE_" + checkIdName + " = \"permission_" + checkIdName.toLowerCase() + "\";\r\n");
    }

    public void addMethod(PermissionCheck permissionCheck) {
        addLines(methodLinesIndex, "\r\n");
        addLines(methodLinesIndex, "  public static void " + methodName + "(PermissionHelper helper) {\r\n");
        addLines(methodLinesIndex, "    PermissionFunc.check(helper");

        if (!permissionCheck.isForce()) {
            addLines(methodLinesIndex, ", CACHE_" + checkIdName);
        }

        addLines(methodLinesIndex, ", " + checkIdName + ",\r\n");
        addLines(methodLinesIndex, "       new String[]{");

        int count = CountFunc.count(permissionCheck.value());

        for (int i = 0; i < count; i++) {
            if (i == count - 1) {
                addLines(methodLinesIndex, "\"" + permissionCheck.value()[i] + "\"");
            } else {
                addLines(methodLinesIndex, "\"" + permissionCheck.value()[i] + "\", ");
            }
        }

        addLines(methodLinesIndex, "});\r\n");
        addLines(methodLinesIndex, "  }\r\n");
    }

}
