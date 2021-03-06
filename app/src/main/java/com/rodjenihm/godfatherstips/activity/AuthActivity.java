package com.rodjenihm.godfatherstips.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.rodjenihm.godfatherstips.R;
import com.rodjenihm.godfatherstips.Utilities;
import com.rodjenihm.godfatherstips.fragment.CreateAccountFragment;
import com.rodjenihm.godfatherstips.fragment.ResetPasswordFragment;
import com.rodjenihm.godfatherstips.fragment.SignInFragment;
import com.rodjenihm.godfatherstips.model.AppUser;
import com.rodjenihm.godfatherstips.model.Message;
import com.rodjenihm.godfatherstips.service.AppDatabase;

public class AuthActivity extends AppCompatActivity {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private Drawer drawer = null;

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            View my_view = findViewById(R.id.textView);
            my_view.setVisibility(View.GONE);
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            drawer = buildAuthMaterialDrawer();
            Utilities.setFragment(fragmentManager, SignInFragment.class, R.id.flAuth);
        } else {
            final ProgressDialog dlg = new ProgressDialog(this);
            dlg.setTitle(R.string.please_wait);
            dlg.setMessage(getResources().getString(R.string.loading_user_data));
            dlg.show();

            FirebaseFirestore.getInstance().collection("users")
                    .document(currentUser.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        dlg.dismiss();
                        AppUser.CURRENT_USER = documentSnapshot.toObject(AppUser.class);
                        startActivity(new Intent(this, NavigationActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        dlg.dismiss();
                        //recreate();
                        Utilities.showAlertDialog(
                                this,
                                getResources().getString(R.string.loading_user_data_error),
                                e.getLocalizedMessage(),
                                mAuth::signOut);
                    });
        }
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
                        .withTextColor(getResources().getColor(R.color.colorText))
                        .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                            Utilities.setFragment(fragmentManager, SignInFragment.class, R.id.flAuth);
                            drawer.closeDrawer();
                            return true;
                        });

        PrimaryDrawerItem itemCreateAccount =
                new PrimaryDrawerItem()
                        .withName(R.string.create_account)
                        .withIcon(R.drawable.ic_create_account)
                        .withTextColor(getResources().getColor(R.color.colorText))
                        .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                            Utilities.setFragment(fragmentManager, CreateAccountFragment.class, R.id.flAuth);
                            drawer.closeDrawer();
                            return true;
                        });

        PrimaryDrawerItem itemResetPassword =
                new PrimaryDrawerItem()
                        .withName(R.string.reset_password)
                        .withIcon(R.drawable.ic_password)
                        .withTextColor(getResources().getColor(R.color.colorText))
                        .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                            Utilities.setFragment(fragmentManager, ResetPasswordFragment.class, R.id.flAuth);
                            drawer.closeDrawer();
                            return true;
                        });

        drawerBuilder.addDrawerItems(itemSignIn, itemCreateAccount, itemResetPassword, new DividerDrawerItem());

        return drawerBuilder.build();
    }
}
