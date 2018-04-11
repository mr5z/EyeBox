package com.nkraft.eyebox.services;

import com.nkraft.eyebox.utils.HttpUtil;

import java.io.IOException;

public class BaseService {

    static final String API_KEY = "DFHSQWEPOKK3249SDFKJNDS97234KJNSDF0924NKJ";

    static final String DOMAIN = "http://host.felbros.com/";

    String get(String path, HttpUtil.KeyValue ...params) throws IOException {
        return HttpUtil.get(DOMAIN + path, params);
    }

    String post(String path, HttpUtil.KeyValue ...params) throws IOException {
        return HttpUtil.post(DOMAIN + path, params);
    }
}
