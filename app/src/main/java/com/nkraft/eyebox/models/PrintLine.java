package com.nkraft.eyebox.models;

public class PrintLine {

    private Type type;
    private Alignment alignment;
    private String value;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public enum Type {
        TEXT,
        IMAGE
    }
    public enum Alignment {
        LEFT,
        RIGHT,
        CENTER
    }
}
