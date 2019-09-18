package core.processor.model;

import func4j.StringFunc;

import static core.processor.StaticData.CLASS_NAME_CODE_4_REQUEST;
import static core.processor.StaticData.PACKAGE_CORE;

public class Code4RequestCM extends ClassModel {

    public final int codeLinesIndex;

    public int index = 0;

    public Code4RequestCM() {
        super(PACKAGE_CORE, CLASS_NAME_CODE_4_REQUEST);

        //class
        int classLinesIndex = createLines();
        addLines(classLinesIndex, "\r\n");
        addLines(classLinesIndex, "public class " + name + " {\r\n");

        //code
        codeLinesIndex = createLines();

        //end
        createEndLines();
    }

    public void addCode(String name) {
        addLines(codeLinesIndex, "\r\n");
        addLines(codeLinesIndex, "  public static final int " + StringFunc.getStaticName(StringFunc.getClassName(name)) + " = " + index++ + ";\r\n");
    }

}
