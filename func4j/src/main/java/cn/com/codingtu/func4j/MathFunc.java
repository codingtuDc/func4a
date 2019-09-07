package cn.com.codingtu.func4j;

public class MathFunc {
    public static int adjust(int rateBefore, int rateAfter, int before) {
        return before * rateAfter / rateBefore;
    }

    public static int row(int count, int perRow) {
        return (count / perRow) + (count % perRow == 0 ? 0 : 1);
    }
}
