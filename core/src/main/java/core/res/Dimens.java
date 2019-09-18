package core.res;

import global.App;

public class Dimens {

    public static float getDimen(int dimenId) {
        return App.APP.getResources().getDimension(dimenId);
    }

}