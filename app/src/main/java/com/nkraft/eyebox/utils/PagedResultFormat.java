package com.nkraft.eyebox.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class PagedResultFormat<T> {

    private JSONObject jsonObject;

    public PagedResultFormat(String rawResponse) throws JSONException {
        jsonObject = new JSONObject(rawResponse);
    }

    public boolean success() throws JSONException {
        return jsonObject.getBoolean("success");
    }

    public T single(Object object) throws JSONException {
        return toObject(object, data());
    }

    public List<T> list(Object object) throws JSONException {
        return toList(object, data());
    }

    public String message() throws JSONException {
        return data();
    }

    public long count() throws JSONException {
        return jsonObject.getLong("totalCount");
    }

    private String data() throws JSONException {
        return jsonObject.getString("data");
    }

    @SuppressWarnings("unchecked")
    private static <T> T toObject(Object object, String responseData) throws IllegalStateException {
        if (responseData == null || responseData.isEmpty()) {
            return null;
        }
        return new Gson().fromJson(responseData, getType(object));
    }

    private static  <T> List<T> toList(Object object, String responseData) throws IllegalStateException {
        if (responseData == null || responseData.isEmpty()) {
            return null;
        }
        Class<T> typeOfT = getGenericTypeClass(object);
        Type type = TypeToken.getParameterized(List.class, typeOfT).getType();
        return new Gson().fromJson(responseData, type);
    }

    private static Type getType(Object object) {
        return ((ParameterizedType) object.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> getGenericTypeClass(Object object) throws IllegalStateException {
        try {
            String className = getType(object).toString();
            return (Class<T>) Class.forName(className.replace("class ", ""));
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type! Please use extends <>");
        }
    }
}