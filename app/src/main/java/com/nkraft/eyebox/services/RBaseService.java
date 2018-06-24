package com.nkraft.eyebox.services;

import com.nkraft.eyebox.utils.HttpUtil;
import com.nkraft.eyebox.utils.HttpUtil.KeyValue;
import com.nkraft.eyebox.utils.PagedResultFormat;

import org.json.JSONException;

import java.util.List;

abstract class RBaseService<T> {

    static final String API_KEY = "DFHSQWEPOKK3249SDFKJNDS97234KJNSDF0924NKJ";

    private static final String DOMAIN = "http://host.felbros.com/";
    private static final String DIRECTORY = "aaronz/";
    private static final String DOMAIN_NAME = DOMAIN + DIRECTORY;

    private String servicePath;

    RBaseService(String servicePath) {
        this.servicePath = DOMAIN_NAME + servicePath;
    }

    KeyValue key() {
        return KeyValue.make("key", API_KEY);
    }

    KeyValue identity(long userId) {
        return KeyValue.make("userId", String.valueOf(userId));
    }

    KeyValue action(String value) {
        return KeyValue.make("action", value);
    }

    KeyValue makeValue(String key, int value) {
        return HttpUtil.KeyValue.make(key, value);
    }

    KeyValue makeValue(String key, long value) {
        return HttpUtil.KeyValue.make(key, value);
    }

    KeyValue makeValue(String key, String value) {
        return HttpUtil.KeyValue.make(key, value);
    }

    PagedResult<T> getObject(KeyValue ...params) {
        try {
            String rawResponse = HttpUtil.get(servicePath, params);
            return serializeObject(rawResponse);
        }
        catch (Exception e) {
            return new PagedResult<>(e.getMessage());
        }
    }

    PagedResult<List<T>> getList(KeyValue ...params) {
        try {
            String rawResponse = HttpUtil.get(servicePath, params);
            return serializeList(rawResponse);
        }
        catch (Exception e) {
            return new PagedResult<>(e.getMessage());
        }
    }

    PagedResult<T> postObject(KeyValue ...params) {
        try {
            String rawResponse = HttpUtil.post(servicePath, params);
            return serializeObject(rawResponse);
        }
        catch (Exception e) {
            return new PagedResult<>(e.getMessage());
        }
    }

    private PagedResult<T> serializeObject(String rawResponse) throws JSONException {
        PagedResultFormat<T> result = new PagedResultFormat<>(rawResponse);
        if (result.success()) {
            return new PagedResult<>(result.single(this), 1);
        } else {
            return new PagedResult<>(result.message());
        }
    }

    private PagedResult<List<T>> serializeList(String rawResponse) throws JSONException {
        PagedResultFormat<T> result = new PagedResultFormat<>(rawResponse);
        if (result.success()) {
            return new PagedResult<>(result.list(this), result.count());
        } else {
            return new PagedResult<>(result.message());
        }
    }

    public String getServicePath() {
        return servicePath;
    }
}