package cn.com.codingtu.func4a.core.processor.model;

import cn.com.codingtu.func4a.core.processor.BaseProcessor;
import cn.com.codingtu.func4j.StringFunc;

public class Code4RequestCM extends ClassModel {

    public final int codeLinesIndex;

    public int index = 0;

    public Code4RequestCM() {
        super(BaseProcessor.PACKAGE_CORE, "Code4Request");

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
