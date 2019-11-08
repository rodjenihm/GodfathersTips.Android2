package com.rodjenihm.godfatherstips;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class Utilities {
    public static void setFragment(FragmentManager fragmentManager, Class fragmentClass, int containerViewId) {
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(containerViewId, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
