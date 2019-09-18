package func4j;

public class StringFunc {

    /***************************************
     *
     * 判断字符串是否为空。包括null和"","   "等
     *
     ***************************************/
    public static boolean isBlank(String text) {
        return text == null || text.trim().length() <= 0;
    }

    /***************************************
     *
     * 判断字符串是否有值，不包括"","  "等
     *
     ***************************************/
    public static boolean isNotBlank(String text) {
        return !isBlank(text);
    }

    /***************************************
     *
     * 格式化数字。比如12，格式化成4位的0012
     *
     * @param number 需要格式化的数字
     * @param digits 位数
     * @return 格式化的数字
     *
     ***************************************/
    public static String formatNumber(int number, int digits) {
        StringBuilder sb = new StringBuilder();
        sb.append(number);
        int rest = digits - sb.length();
        if (rest > 0) {
            for (int i = 0; i < rest; i++) {
                sb.insert(0, "0");
            }
        }
        return sb.toString();
    }

    /***************************************
     *
     * object转换成string
     *
     ***************************************/
    public static String toString(Object obj) {
        if (obj == null) {
            return "";
        } else if (obj instanceof String) {
            return (String) obj;
        } else {
            return String.valueOf(obj);
        }
    }

    public static String getStaticName(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 97 && c <= 122) {
                sb.append((char) (c - 32));
            } else if (c >= 65 && c <= 90 && i != 0) {
                sb.append("_" + c);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getMethodName(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    public static String getClassName(String classPath) {
        return classPath.substring(classPath.lastIndexOf(".") + 1);
    }
}
