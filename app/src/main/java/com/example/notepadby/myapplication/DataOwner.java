package com.example.notepadby.myapplication;

import android.content.Context;

class DataOwner {
    private static int columns = 4;
    private static Context context;

    private DataOwner() {
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        DataOwner.context = context;
    }

    public static void setColumns(int c) {
        columns = c;
    }
    public static int getColumns() {
        return columns;
    }
}
