package com.ximand;

final class StringFormatBuilder {

    private final StringBuilder stringBuilder;

    StringFormatBuilder() {
        this.stringBuilder = new StringBuilder();
    }

    public StringFormatBuilder(String str) {
        this.stringBuilder = new StringBuilder(str);
    }

    public StringFormatBuilder append(String str) {
        stringBuilder.append(str);
        return this;
    }

    public StringFormatBuilder appendf(String format, Object... args) {
        return append(String.format(format, args));
    }

    public String toString() {
        return stringBuilder.toString();
    }

}
