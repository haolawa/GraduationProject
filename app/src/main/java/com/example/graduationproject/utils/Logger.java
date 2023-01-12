package com.example.graduationproject.utils;

import android.util.Log;

import com.example.graduationproject.BuildConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xh on 2017/12/1.
 * 打印日志
 * logcat对message分配的内存空间为4K，打印超过4K的日志时超过4K部分的日志默认会被截断
 * 解决方案：分割大于4K的日志，分别打印
 */

public class Logger {

    private static final String TAG = "XHMM-LOG";

    private static final int MAX_LENGTH = 2048;

    public static void i(Object... args) {
        printLogs(Log.INFO, args);
    }

    public static void d(Object... args) {
        printLogs(Log.DEBUG, args);
    }

    public static void e(Object... args) {
        printLogs(Log.ERROR, args);
    }

    public static void w(Object... args) {
        printLogs(Log.WARN, args);
    }

    public static void printLogs(int priority, Object... args) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        if (args == null || args.length == 0) {
            return;
        }
        List<Object> logs = splitLogs(args);
        if (logs == null || logs.isEmpty()) {
            return;
        }
        for (Object log : logs) {
            Log.println(priority, TAG, log.toString());
        }
    }

    private static List<Object> splitLogs(Object... args) {
        if (args == null || args.length <= 0) {
            return null;
        }
        List<Object> objects = new ArrayList<>();
        for (Object arg : args) {
            if (arg == null) continue;
            String tempStr = arg.toString();
            long tempLength = tempStr.length();
            if (tempLength <= MAX_LENGTH) {
                objects.add(tempStr);
                continue;
            } else {
                int index = 0;
                String tempSub;
                while (index < tempLength) {
                    if (tempLength <= index + MAX_LENGTH) {
                        tempSub = tempStr.substring(index);
                    } else {
                        tempSub = tempStr.substring(index, index + MAX_LENGTH);
                    }
                    index += MAX_LENGTH;
                    objects.add(tempSub);
                }
            }
        }
        return objects;
    }

}
