package com.example.graduationproject.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 对SharedPreference文件中的各种类型的数据进行存取操作
 */
public class SPUtils {

    private static SharedPreferences sp;

    private static void init(Context context) {
        if (sp == null) {
            sp = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }

    public static void setSharedIntData(Context context, String key, int value) {
        if (sp == null) {
            init(context);
        }
        sp.edit().putInt(key, value).apply();
    }

    public static int getSharedIntData(Context context, String key, int defValue) {
        if (sp == null) {
            init(context);
        }
        return sp.getInt(key, defValue);
    }

    public static void setSharedlongData(Context context, String key, long value) {
        if (sp == null) {
            init(context);
        }
        sp.edit().putLong(key, value).apply();
    }

    public static long getSharedlongData(Context context, String key) {
        if (sp == null) {
            init(context);
        }
        return sp.getLong(key, 0L);
    }

    public static void setSharedFloatData(Context context, String key,
                                          float value) {
        if (sp == null) {
            init(context);
        }
        sp.edit().putFloat(key, value).apply();
    }

    public static Float getSharedFloatData(Context context, String key) {
        if (sp == null) {
            init(context);
        }
        return sp.getFloat(key, 0f);
    }

    public static void setSharedBooleanData(Context context, String key, boolean value) {
        if (sp == null) {
            init(context);
        }
        sp.edit().putBoolean(key, value).apply();
    }

    public static boolean getSharedBooleanData(Context context, String key) {
        if (sp == null) {
            init(context);
        }
        return sp.getBoolean(key, false);
    }

    public static void setSharedStringData(Context context, String key, String value) {
        if (sp == null) {
            init(context);
        }
        sp.edit().putString(key, value).apply();
    }

    public static String getSharedStringData(Context context, String key) {
        if (sp == null) {
            init(context);
        }
        return sp.getString(key, "");
    }

    public static String getSharedStringData(Context context, String key, String defValue) {
        if (sp == null) {
            init(context);
        }
        return sp.getString(key, defValue);
    }

    /**
     * 保存List
     *
     * @param tag
     * @param datalist
     */
    public static <T> void setDataList(Context context, String tag, List<T> datalist) {
        if (sp == null) {
            init(context);
        }
        if (null == datalist || datalist.size() <= 0) {
            sp.edit().remove(tag).apply();
            return;
        }
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(datalist);
        sp.edit().putString(tag, strJson).apply();

    }


    public static <K, V> void setMap(Context context, String tag, Map<K, V> map) {
        if (sp == null) {
            init(context);
        }
        if (null == map || map.size() <= 0) {
            sp.edit().remove(tag).apply();
            return;
        }
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(map);
        sp.edit().putString(tag, strJson).apply();
    }


    public static <K, V> Map<K, V> getLinkedHashMap(Context context, String tag) {
        Map<K, V> map = new LinkedHashMap<>();
        if (sp == null) {
            init(context);
        }
        String strJson = sp.getString(tag, null);
        if (TextUtils.isEmpty(strJson)) {
            return map;
        }
        Gson gson = new Gson();
        map = gson.fromJson(strJson, map.getClass());
        return map;
    }
}