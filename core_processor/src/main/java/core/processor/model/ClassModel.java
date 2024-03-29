package core.processor.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class ClassModel {

    public String fullName;
    public String name;
    public String packages;
    public String oriName;
    public List<List<String>> lines;
    public Map<Integer, List<String>> subLinesMap;
    public int importLinesIndex;

    public ClassModel(String packageName, String name) {
        this.packages = packageName;
        this.oriName = name;
        this.name = name;
        this.fullName = this.packages + "." + this.name;
        addPackageLines();
    }

    public ClassModel(Elements elementUtils, TypeElement te, String prex) {
        this.packages = elementUtils.getPackageOf(te).getQualifiedName().toString();
        this.oriName = te.getSimpleName().toString();
        this.name = oriName + "_" + prex;
        this.fullName = this.packages + "." + this.name;
        addPackageLines();
    }

    private void addPackageLines() {
        addLines(createLines(), "package " + packages + ";\r\n");
    }

    public int createLines() {
        if (lines == null) {
            lines = new ArrayList<List<String>>();
        }
        lines.add(new ArrayList<String>());
        return lines.size() - 1;
    }

    public void addLines(int linesIndex, String line) {
        this.lines.get(linesIndex).add(line);
    }

    public void addLines(int linesIndex, int listIndex, String line) {
        this.lines.get(linesIndex).add(listIndex, line);
    }

    public void addLinesIfNotExist(int index, String line) {
        List<String> subLines = this.lines.get(index);
        if (!subLines.contains(line)) {
            subLines.add(line);
        }
    }

    public String getLines() {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            List<String> subLines = lines.get(i);
            for (int j = 0; j < subLines.size(); j++) {
                sb.append(subLines.get(j));
            }
        }

        return sb.toString();
    }

    public void addSubLines(int index, String line) {
        if (subLinesMap == null)
            subLinesMap = new HashMap<Integer, List<String>>();

        List<String> lines = subLinesMap.get(index);
        if (lines == null) {
            lines = new ArrayList<String>();
            subLinesMap.put(index, lines);
        }

        lines.add(line);

    }

    public List<String> getSubLines(int index) {
        if (subLinesMap != null) {
            return subLinesMap.get(index);
        }
        return null;
    }

    private Class[] exceptClasses = new Class[]{
            int.class,
            long.class,
            double.class,
            float.class,
            boolean.class,
            String.class
    };

    public void addImport(String importStr) {

        for (int i = 0; i < exceptClasses.length; i++) {
            if (exceptClasses[i].getName().equals(importStr)) {
                return;
            }
        }

        addLinesIfNotExist(this.importLinesIndex, "import " + importStr + ";\r\n");
    }

    public void createEndLines() {
        int endLinesIndex = createLines();
        addLines(endLinesIndex, "\r\n");
        addLines(endLinesIndex, "}\r\n");
    }
}
