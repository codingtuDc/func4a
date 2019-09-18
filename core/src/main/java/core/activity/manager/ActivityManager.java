package core.activity.manager;

import android.app.Activity;

import java.util.HashMap;
import java.util.Set;

public class ActivityManager {

    private HashMap<String, Activity> acts;

    private ActivityManager() {
        acts = new HashMap<String, Activity>();
    }

    private static final class SingleInstance {
        public static final ActivityManager INSTANCE = new ActivityManager();
    }

    public static ActivityManager getInstance() {
        return SingleInstance.INSTANCE;
    }

    public void add(Activity act) {
        if (acts == null)
            acts = new HashMap<String, Activity>();
        acts.put(act.toString(), act);
    }

    public void remove(Activity act) {
        if (acts != null) {
            acts.remove(act.toString());
        }
    }

    public void finishAll() {
        if (acts != null) {
            Set<String> keys = acts.keySet();
            for (String key : keys) {
                acts.get(key).finish();
            }
            acts.clear();
        }
    }

    public Activity find(String act) {
        if (acts != null) {
            return acts.get(act);
        }
        return null;
    }

}