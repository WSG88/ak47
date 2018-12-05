package com.r0411g.sql.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Created by supers on 2017/3/9.
 */
public class GsonUtils{
    private static final String EMPTY_JSON = "{}";
    private static final String EMPTY_JSON_ARRAY = "[]";
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public GsonUtils() {
    }

    public static String toJson(Object target) {
        return toJson(target, (Type)null, (String)null);
    }

    public static String toJson(Object target, Type targetType, String datePattern) {
        if(target == null) {
            return "{}";
        } else {
            GsonBuilder builder = new GsonBuilder();
            if(datePattern == null || datePattern.length() < 1) {
                datePattern = "yyyy-MM-dd HH:mm:ss";
            }

            builder.setDateFormat(datePattern);
            Gson gson = builder.create();
            String result = "{}";

            try {
                if(targetType == null) {
                    result = gson.toJson(target);
                } else {
                    result = gson.toJson(target, targetType);
                }
            } catch (Exception var7) {
                if(target instanceof Collection || target instanceof Iterator || target instanceof Enumeration || target.getClass().isArray()) {
                    result = "[]";
                }
            }

            return result;
        }
    }

    public static String toJson(Object target, Type targetType) {
        return toJson(target, targetType, (String)null);
    }

    public static <T> T fromJson(String json, TypeToken<T> token, String datePattern) {
        if(json != null && json.length() >= 1) {
            GsonBuilder builder = new GsonBuilder();
            if(datePattern == null || datePattern.length() < 1) {
                datePattern = "yyyy-MM-dd HH:mm:ss";
            }

            builder.setDateFormat(datePattern);
            Gson gson = builder.create();

            try {
                return gson.fromJson(json, token.getType());
            } catch (Exception var6) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static Object fromJson(String json, Type type, String datePattern) {
        if(json != null && json.length() >= 1) {
            GsonBuilder builder = new GsonBuilder();
            if(datePattern == null || datePattern.length() < 1) {
                datePattern = "yyyy-MM-dd HH:mm:ss";
            }

            builder.setDateFormat(datePattern);
            Gson gson = builder.create();

            try {
                return gson.fromJson(json, type);
            } catch (Exception var6) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static Object fromJson(String json, Type type) {
        return fromJson(json, (Type)type, (String)null);
    }

    public static <T> T fromJson(String json, TypeToken<T> token) {
        return (T) fromJson(json, (TypeToken)token, (String)null);
    }

    public static <T> T fromJson(String json, Class<T> clazz, String datePattern) {
        if(json != null && json.length() >= 1) {
            GsonBuilder builder = new GsonBuilder();
            if(datePattern == null || datePattern.length() < 1) {
                datePattern = "yyyy-MM-dd HH:mm:ss";
            }

            builder.setDateFormat(datePattern);
            Gson gson = builder.create();

            try {
                return gson.fromJson(json, clazz);
            } catch (Exception var6) {
                return null;
            }
        } else {
            return null;
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return (T)fromJson(json, (Class)clazz, (String)null);
    }
}
