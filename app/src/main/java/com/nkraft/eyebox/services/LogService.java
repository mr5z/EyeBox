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
                makeValue("userId", userId),
                makeValue("message", message));
    }

    public PagedResult<List<Log>> downloadLogs(long userId, int page, int pageSize) {
        return getList(action("download"),
                makeValue("userId", userId),
                makeValue("page", page),
                makeValue("pageSize", pageSize));
    }

}
