package core.funcs;

import func4j.MathFunc;

public class SizeFunc {

    public static int size(int designW, int designSize) {
        return MathFunc.adjust(designW, designSize, MobileFunc.getScreenWidth());
    }

    public static Size create(int designW) {
        return new Size(designW);
    }

    public static class Size {
        private int designWidth;

        private Size(int designWidth) {
            this.designWidth = designWidth;
        }

        public int size(int designSize) {
            return SizeFunc.size(designWidth, designSize);
        }
    }

}