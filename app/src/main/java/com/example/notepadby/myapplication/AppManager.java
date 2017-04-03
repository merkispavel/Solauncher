package com.example.notepadby.myapplication;


import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

class MyComparator implements Comparator<Pair<String, Integer> > {
    @Override
    public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
        return o1.first.compareTo(o2.first);
    }
}

class AppManager {
    private static List<Pair<Pair<String, Integer>, Integer> > myDataset = new ArrayList<>();
    private static List<Pair<String, Integer> > newApps = new ArrayList<>();
    private static List<Pair<Pair<String, Integer>, Integer> > popularApps = new ArrayList<>();

    private static int numberOfApps;
    private static final int[] drawables = new int[]
            {R.drawable.ic_android_black_24dp,
                    R.drawable.ic_apps_black_24dp,
                    R.drawable.ic_assessment_black_24dp,
                    R.drawable.ic_beach_access_black_24dp,
                    R.drawable.ic_casino_black_24dp,
                    R.drawable.ic_child_care_black_24dp,
                    R.drawable.ic_visibility_black_24dp,
                    R.drawable.ic_do_not_disturb_alt_black_24dp,
                    R.drawable.ic_content_cut_black_24dp,
                    R.drawable.ic_control_point_black_24dp,
                    R.drawable.ic_gps_fixed_black_24dp
            };


    private AppManager() {
        for (int i = 1; i <= numberOfApps; i++) {
            Pair<Pair<String, Integer>, Integer> element =
                    makeElement(Integer.toHexString(i), drawables[(i - 1) % drawables.length], 0);
            myDataset.add(element);
        }
        HashSet<Integer> hashSet = new HashSet<>();
        Random random = new Random();
        while (hashSet.size() < myDataset.size()
                && hashSet.size() < 7){
            hashSet.add(random.nextInt(myDataset.size()));
        }
        for (int i : hashSet) {
            newApps.add(myDataset.get(i).first);
            popularApps.add(myDataset.get(i));
        }
    }

    private static Pair<Pair<String, Integer>, Integer> makeElement(String s, int id, int count) {
        return new Pair<>(new Pair<>(s, id), count);
    }

    static int getNumberOfApps() {
        return myDataset.size();
    }

    static void setNumberOfApps(int numberOfApps) {
        AppManager.numberOfApps = numberOfApps;
        new AppManager();
    }

    static Pair<String, Integer> getPopularApp(int pos) {
        if (pos >= popularApps.size())
            return new Pair<>("", 0);
        return popularApps.get(pos).first;
    }

    static Pair<String, Integer> getNewApp(int pos) {
        if (pos >= newApps.size())
            return new Pair<>("", 0);
        return newApps.get(pos);
    }

    static Pair<String, Integer> getApp(int pos) {
        if (pos >= myDataset.size())
            return new Pair<>("", 0);
        return myDataset.get(pos).first;
    }

    static void  removeApp(int pos) {
        myDataset.remove(pos);
    }

    private static void updatePopular(int pos) {
        popularApps.add(myDataset.get(pos));
        for (int i = popularApps.size() - 2; i >= 0; i--) {
            Pair<Pair<String, Integer>, Integer> element1 = popularApps.get(i);
            Pair<Pair<String, Integer>, Integer> element2 = popularApps.get(i + 1);
            if (element1.second < element2.second) {
                popularApps.set(i, element2);
                popularApps.set(i + 1, element1);
            } else
                break;
        }
        popularApps.remove(popularApps.size() - 1);
    }

    static void appVisit(int pos) {
        Pair<Pair<String, Integer>, Integer> element = myDataset.get(pos);
        myDataset.set(pos, new Pair<>(element.first, element.second + 1));
//        updatePopular();
    }

    static int size() {
        return 3 + 2 * DataOwner.getColumns() + myDataset.size();
    }
}
