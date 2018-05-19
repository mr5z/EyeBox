package com.nkraft.eyebox.services;

import com.nkraft.eyebox.utils.HttpUtil;

import java.io.IOException;

@Deprecated
public class BaseService {

    static final String API_KEY = "DFHSQWEPOKK3249SDFKJNDS97234KJNSDF0924NKJ";

    private static final String DOMAIN = "http://host.felbros.com/";
    private static final String DIRECTORY = "aaronz/";

    String get(String path, HttpUtil.KeyValue ...params) throws IOException {
        return HttpUtil.get(DOMAIN + DIRECTORY + path, params);
    }

    String post(String path, HttpUtil.KeyValue ...params) throws IOException {
        return HttpUtil.post(DOMAIN + DIRECTORY + path, params);
    }
}
