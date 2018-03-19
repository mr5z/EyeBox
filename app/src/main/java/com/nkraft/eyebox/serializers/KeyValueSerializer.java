package com.nkraft.eyebox.serializers;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.nkraft.eyebox.utils.HttpUtil;

import java.io.IOException;

public class KeyValueSerializer  extends TypeAdapter<HttpUtil.KeyValue[]> {
    @Override
    public void write(JsonWriter jsonWriter, HttpUtil.KeyValue[] keyValues) throws IOException {
        jsonWriter.beginObject();
        for (HttpUtil.KeyValue kv : keyValues) {
            jsonWriter.name(kv.first);
            jsonWriter.value(kv.second);
        }
        jsonWriter.endObject();
    }

    @Override
    public HttpUtil.KeyValue[] read(JsonReader jsonReader) {
        return null;
    }
}