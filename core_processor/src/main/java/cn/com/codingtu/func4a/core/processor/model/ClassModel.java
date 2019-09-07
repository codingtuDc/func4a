package cn.com.codingtu.func4a.core.processor.model;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class ClassModel {

    public String fullName;
    public String name;
    public String packages;
    public String oriName;
    public List<String> constructorLines;

    public ClassModel(Elements elementUtils, TypeElement te, String prex) {
        this.packages = elementUtils.getPackageOf(te).getQualifiedName().toString();
        this.oriName = te.getSimpleName().toString();
        this.name = oriName + "_" + prex;
        this.fullName = this.packages + "." + this.name;
    }

    public void addConstructorLine(String line) {
        if (constructorLines == null)
            constructorLines = new ArrayList<String>();
        constructorLines.add(line);
    }


}
