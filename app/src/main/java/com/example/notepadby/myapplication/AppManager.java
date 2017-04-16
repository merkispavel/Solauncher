package com.example.notepadby.myapplication;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;


class AppManager {
    private static List<AppDetail> apps;
    private static List<AppDetail> favorites = new ArrayList<>();

    static {
        new AppManager();
    }

    private static List<ResolveInfo> getLaunchActivities() {
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities =
                DataOwner.getContext()
                        .getPackageManager().queryIntentActivities(i, 0);
        return availableActivities;
    }

    private AppManager() {
        List<ResolveInfo> launchActivities = getLaunchActivities();
        PackageManager packageManager = DataOwner.getContext().getPackageManager();
        apps = new ArrayList<>(launchActivities.size());
        for (ResolveInfo info : launchActivities) {
            Drawable icon = info.loadIcon(packageManager);
            CharSequence name = info.loadLabel(packageManager);
            apps.add(new AppDetail(name, icon));
        }
    }



    static AppDetail getPopularApp(int pos) {
        return getApp(pos);
    }

    static AppDetail getNewApp(int pos) {
        return getApp(pos);
    }

    static AppDetail getApp(int pos) {
        if (pos >= apps.size())
            return AppDetail.EMPTY_APP;
        return apps.get(pos);
    }

    static void removeApp(int pos) {

    }


    static int size() {
        return 3 + 2 * DataOwner.getColumns() + apps.size();
    }
}
