package com.judian.watch.videos.Utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 * Copyright (©) 2014
 * <p/>
 * 配置文件读写工具类
 *
 * @author eastonc
 * @version 1.0, 14-7-28 13:43
 * @since 14-7-28
 */
public class PreferencesUtil {




    public static void putString(String keyString, String valueString) {
        if (keyString != null) {
            getDefaultSharedPreferences().edit().putString(keyString, valueString).apply();
        }
    }



    /**
     * @
     */
    private static SharedPreferences getDefaultSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
    }

    /**
     * 获取String类型的键值
     *
     * @
     * @
     * @
     * @return Key value
     */
    public static String getString(String keyString) {
        if (keyString == null)
            return null;
        return getDefaultSharedPreferences().getString(keyString, null);
    }



    /**
     * 清空键名为 keyString 的键值对
     *
     * @
     */
    public static void clearKey(String keyString) {
        if (!TextUtils.isEmpty(keyString))
            getDefaultSharedPreferences().edit().remove(keyString).apply();
//            getDefaultSharedPreferences().edit().clear().commit();
    }

    /**
     * 移除键名为 keyString 的键值对
     *
     * @
     * @
     * @
     */
    public static void removeKey(String keyString) {
        if (keyString != null) {
            if (!TextUtils.isEmpty(getString(keyString)))
                getDefaultSharedPreferences().edit().remove(keyString).apply();
        }
    }

}
