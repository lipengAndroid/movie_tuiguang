package com.judian.watch.videos.Utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON转换工具类
 *
 * @param <T>
 */
public class JsonUtil<T> {



    public T json2Bean(String jsonString, String className) {
        try {
            Class<?> clazz = Class.forName(className);
            if (TextUtils.isEmpty(jsonString)) {
                return (T) clazz.newInstance();
            } else {
                jsonString = jsonString.replaceAll(", null", "");
                GsonBuilder builder;
                builder = new GsonBuilder();
//                builder.registerTypeAdapter(Uri.class, new UriDeserializer());
                Gson gson = builder.create();
                T t = (T) gson.fromJson(jsonString, clazz);
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String bean2Json(T t) {
        return new Gson().toJson(t);
    }



}
