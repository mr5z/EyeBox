package com.nkraft.eyebox.services;

import com.nkraft.eyebox.models.Log;
import com.nkraft.eyebox.utils.HttpUtil;

import java.util.List;

public class LogService extends RBaseService<Log> {
    private LogService() {
        super("logs.php");
    }

    private static LogService _instance = new LogService();

    public static LogService instance() {
        return _instance;
    }

    public PagedResult<Log> uploadLog(long userId, String message) {
        return getObject(action("upload"),
                HttpUtil.KeyValue.make("userId", userId),
                HttpUtil.KeyValue.make("message", message));
    }

    public PagedResult<List<Log>> downloadLogs(long userId, int page, int pageSize) {
        return getList(action("download"),
                HttpUtil.KeyValue.make("userId", userId),
                HttpUtil.KeyValue.make("page", page),
                HttpUtil.KeyValue.make("pageSize", pageSize));
    }

}
