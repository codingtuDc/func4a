package core.log;

import android.util.Log;

import java.util.Collection;
import java.util.Iterator;

import global.CoreConfigs;
import func4j.CountFunc;
import func4j.StringFunc;

public class Logs {

    //封装了log方法，可以打印多种类型

    public static final int LEVEL_INFO = 0;
    public static final int LEVEL_WARNING = 1;
    public static final int LEVEL_ERROR = 2;

    public static void line(Object... msgs) {
        StringBuilder sb = new StringBuilder();
        sb.append(" \n");
        sb.append(
                "┌───────────────────────────────────────────────────────────────────────────────────────\n");

        for (int i = 0; i < CountFunc.count(msgs); i++) {
            sb.append("│ " + msgs[i] + "\n");
        }
        sb.append(
                "└───────────────────────────────────────────────────────────────────────────────────────\n");
        i(sb.toString());
    }

    public static void i(Object msg) {
        log(LEVEL_INFO, CoreConfigs.configs().defaultLogTag() + "_I", msg);
    }

    public static void e(Object msg) {
        log(LEVEL_ERROR, CoreConfigs.configs().defaultLogTag() + "_E", msg);
    }

    public static void w(Object msg) {
        log(LEVEL_WARNING, CoreConfigs.configs().defaultLogTag() + "_W", msg);
    }

    public static void i(String tag, Object msg) {
        log(LEVEL_INFO, tag, msg);
    }

    public static void e(String tag, Object msg) {
        log(LEVEL_ERROR, tag, msg);
    }

    public static void w(String tag, Object msg) {
        log(LEVEL_WARNING, tag, msg);
    }

    private static void log(int level, String tag, Object msg) {
        if (CoreConfigs.configs().isLog() && StringFunc.isNotBlank(tag)) {
            if (msg == null) {
                logNull(level, tag);
            } else if (msg instanceof String) {
                logString(level, tag, (String) msg);
            } else if (msg instanceof Throwable) {
                logThrowable(level, tag, (Throwable) msg);
            } else if (msg instanceof Collection) {
                logCollection(level, tag, (Collection) msg);
            } else if (msg.getClass().isArray()) {
                logArray(level, tag, (Object[]) msg);
            } else {
                logOther(level, tag, msg);
            }
        }
    }

    private static void logOther(int level, String tag, Object msg) {
        baseLog(level, tag, msg.toString());
    }

    private static void logNull(int level, String tag) {
        baseLog(level, tag, "this is null");
    }

    private static void logString(int level, String tag, String msg) {
        baseLog(level, tag, msg);
    }

    private static void logThrowable(int level, String tag, Throwable t) {

        StringBuilder sb = new StringBuilder();
        sb.append(
                "  \n┌──Throwable────────────────────────────────────────────────────────────────────────────\n");
        sb.append("│ " + t.getClass().getName() + ": " + t.getMessage() + "\n");
        StackTraceElement[] stackTrace = t.getStackTrace();
        for (StackTraceElement ste : stackTrace) {
            sb.append("│\tat " + ste + "\n");
        }
        Throwable cause = t.getCause();
        if (cause != null) {
            sb.append("│ Caused by: " + cause.getMessage() + "\n");
            StackTraceElement[] stackTrace1 = cause.getStackTrace();
            for (StackTraceElement ste : stackTrace1) {
                sb.append("│\tat " + ste + "\n");
            }
        }
        sb.append(
                "└───────────────────────────────────────────────────────────────────────────────────────\n");
        baseLog(level, tag, sb.toString());
    }

    private static void logCollection(int level, String tag, Collection cs) {
        StringBuilder sb = new StringBuilder();
        sb.append(" \n");
        sb.append(
                "┌──Collection───────────────────────────────────────────────────────────────────────────\n");


        if (cs.size() <= 0) {
            sb.append("│\tCollection is empty\n");
        } else {
            Iterator iterator = cs.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                Object next = iterator.next();
                sb.append("│\tindex:" + index + " | " + next + "\n");
                index++;
            }
        }
        sb.append(
                "└───────────────────────────────────────────────────────────────────────────────────────\n");
        baseLog(level, tag, sb.toString());
    }

    private static void logArray(int level, String tag, Object[] objs) {
        StringBuilder sb = new StringBuilder();
        sb.append(" \n");
        sb.append(
                "┌──Array────────────────────────────────────────────────────────────────────────────────\n");
        if (objs.length <= 0) {
            sb.append("│\tArray is empty");
        } else {
            for (int i = 0; i < objs.length; i++) {
                sb.append("│\tindex:" + i + " | " + objs[i] + "\n");
            }
        }
        sb.append(
                "└───────────────────────────────────────────────────────────────────────────────────────\n");
        baseLog(level, tag, sb.toString());
    }

    private static void baseLog(int level, String tag, String msg) {
        switch (level) {
            case LEVEL_INFO:
                Log.i(tag, msg);
                break;
            case LEVEL_WARNING:
                Log.w(tag, msg);
                break;
            case LEVEL_ERROR:
                Log.e(tag, msg);
                break;
        }
    }

}