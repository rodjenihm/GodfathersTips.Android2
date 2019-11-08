package com.rodjenihm.godfatherstips.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.rodjenihm.godfatherstips.R;
import com.rodjenihm.godfatherstips.Utilities;
import com.rodjenihm.godfatherstips.fragment.CreateAccountFragment;
import com.rodjenihm.godfatherstips.fragment.ResetPasswordFragment;
import com.rodjenihm.godfatherstips.fragment.SignInFragment;

public class AuthActivity extends AppCompatActivity {
    private Drawer drawer = null;
    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        drawer = buildAuthMaterialDrawer();
        setFragment(SignInFragment.class);
    }

    private Drawer buildAuthMaterialDrawer() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerBuilder drawerBuilder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withSliderBackgroundColor(getResources().getColor(R.color.colorBackground));

        PrimaryDrawerItem itemSignIn =
                new PrimaryDrawerItem()
                        .withName(R.string.sign_in)
                        .withIcon(R.drawable.ic_profile)
                        .withTextColor(getResources().getColor(R.color.colorTextDark))
                        .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                            Utilities.setFragment(fragmentManager, SignInFragment.class, R.id.flAuth);
                            drawer.closeDrawer();
                            return true;
                        });

        PrimaryDrawerItem itemCreateAccount =
                new PrimaryDrawerItem()
                        .withName(R.string.create_account)
                        .withIcon(R.drawable.ic_create_account)
                        .withTextColor(getResources().getColor(R.color.colorTextDark))
                        .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                            Utilities.setFragment(fragmentManager, CreateAccountFragment.class, R.id.flAuth);
                            drawer.closeDrawer();
                            return true;
                        });

        PrimaryDrawerItem itemResetPassword =
                new PrimaryDrawerItem()
                        .withName(R.string.reset_password)
                        .withIcon(R.drawable.ic_password)
                        .withTextColor(getResources().getColor(R.color.colorTextDark))
                        .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                            Utilities.setFragment(fragmentManager, ResetPasswordFragment.class, R.id.flAuth);
                            drawer.closeDrawer();
                            return true;
                        });

        drawerBuilder.addDrawerItems(itemSignIn, itemCreateAccount, itemResetPassword, new DividerDrawerItem());

        return drawerBuilder.build();
    }

    private boolean setFragment(Class fragmentClass) {
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            fragmentManager.beginTransaction().replace(R.id.flAuth, fragment).commit();
            drawer.closeDrawer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
