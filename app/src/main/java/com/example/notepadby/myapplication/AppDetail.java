package com.example.notepadby.myapplication;


import android.graphics.drawable.Drawable;

class AppDetail {
    static final AppDetail EMPTY_APP = new AppDetail("", null);
    private final CharSequence name;
    private final Drawable icon;

    public AppDetail(CharSequence name, Drawable icon) {
        this.name = name;
        this.icon = icon;
    }

    public CharSequence getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }
}
