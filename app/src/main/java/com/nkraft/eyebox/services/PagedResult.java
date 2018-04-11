package com.nkraft.eyebox.services;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class PagedResult<T> {

    public enum Status {
        SUCCESS,
        FAILURE
    }

    private final Status status;

    @Nullable
    public final T data;

    public final long totalCount;

    @Nullable
    public final String errorMessage;

    public PagedResult(@NonNull T data, long totalCount) {
        this.status = Status.SUCCESS;
        this.data = data;
        this.totalCount = totalCount;
        this.errorMessage = null;
    }

    public PagedResult(@NonNull String errorMessage) {
        this.status = Status.FAILURE;
        this.data = null;
        this.totalCount = 0;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return status == Status.SUCCESS && data != null;
    }

}
