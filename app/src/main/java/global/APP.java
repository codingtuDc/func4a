package global;

import cn.com.codingtu.func4a.global.App;
import cn.com.codingtu.func4a.global.CoreConfigs;

public class APP extends App {
    @Override
    public CoreConfigs createConfigs() {
        return new Configs();
    }
}
