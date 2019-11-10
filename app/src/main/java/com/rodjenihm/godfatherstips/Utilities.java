package com.rodjenihm.godfatherstips;

import android.app.AlertDialog;
import android.content.Context;

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

    public static void setFragment(FragmentManager fragmentManager, Fragment fragment, int containerViewId) {
        try {
            fragmentManager.beginTransaction().replace(containerViewId, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showAlertDialog(Context context, String title, String message, Action action) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    if (action != null) {
                        action.invoke();
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}
