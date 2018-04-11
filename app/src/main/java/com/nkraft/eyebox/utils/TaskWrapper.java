package com.nkraft.eyebox.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

public class TaskWrapper<T> extends AsyncTask<Void, Void, T> {

    public interface Task<TResult> {
        void onTaskBegin();
        TResult onTaskExecute();
        void onTaskEnd(TResult result);
    }

    private Task<T> mainTask;

    public TaskWrapper(@NonNull Task<T> mainTask) {
        this.mainTask = mainTask;
    }

    @Override
    protected void onPreExecute() {
        mainTask.onTaskBegin();
    }

    @Override
    protected T doInBackground(Void... voids) {
        return mainTask.onTaskExecute();
    }

    @Override
    protected void onPostExecute(T result) {
        mainTask.onTaskEnd(result);
    }

}
