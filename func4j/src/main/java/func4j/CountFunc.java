package func4j;

import java.util.Collection;
import java.util.Map;

public class CountFunc {

    /**************************************************
     *
     * count
     *
     **************************************************/
    public static int count(Collection cs) {
        return cs == null ? 0 : cs.size();
    }

    public static int count(Map map) {
        return map == null ? 0 : map.size();
    }

    public static int count(Object... objs) {
        return objs == null ? 0 : objs.length;
    }

    /**************************************************
     *
     * isNull
     *
     **************************************************/
    public static boolean isNull(Collection cs) {
        return count(cs) <= 0;
    }

    public static boolean isNull(Map map) {
        return count(map) <= 0;
    }

    public static boolean isNull(Object... objs) {
        return count(objs) <= 0;
    }

}
