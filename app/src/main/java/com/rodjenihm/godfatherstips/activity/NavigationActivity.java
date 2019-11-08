package com.rodjenihm.godfatherstips.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.rodjenihm.godfatherstips.R;
import com.rodjenihm.godfatherstips.Utilities;
import com.rodjenihm.godfatherstips.model.AppUser;

public class NavigationActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    AppUser user;

    private final FragmentManager fragmentManager = getSupportFragmentManager();

    private Drawer drawer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        user = (AppUser) getIntent().getSerializableExtra("user");

        int accessLevel = user.getAccessLevel();
        drawer = buildContentMaterialDrawer(user, accessLevel);
    }

    private Drawer buildContentMaterialDrawer(AppUser user, int accessLevel) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(user.getEmail())
                                .withEmail(user.getEmail())
                                .withIcon(getResources().getDrawable(R.drawable.ic_profile))
                )
                .withTextColor(getResources().getColor(R.color.colorTextLight))
                .withOnAccountHeaderListener((view, profile, currentProfile) -> false)
                .build();

        /*PrimaryDrawerItem itemHome = new PrimaryDrawerItem()
                .withName(R.string.drawer_item_home)
                .withTextColor(getResources().getColor(R.color.colorText))
                .withIcon(getResources().getDrawable(R.drawable.ic_home))
                .withOnDrawerItemClickListener((view, position, drawerItem) -> setFragment(HomeFragment.class));

        PrimaryDrawerItem itemAbout = new PrimaryDrawerItem()
                .withName(R.string.drawer_item_about)
                .withTextColor(getResources().getColor(R.color.colorText))
                .withIcon(getResources().getDrawable(R.drawable.about_icon))
                .withOnDrawerItemClickListener((view, position, drawerItem) -> setFragment(AboutFragment.class));

        PrimaryDrawerItem itemContact = new PrimaryDrawerItem()
                .withName(R.string.drawer_item_contact)
                .withIcon(getResources().getDrawable(android.R.drawable.ic_dialog_email))
                .withTextColor(getResources().getColor(R.color.colorText))
                .withOnDrawerItemClickListener((view, position, drawerItem) -> setFragment(ContactFragment.class));

        PrimaryDrawerItem itemShare = new PrimaryDrawerItem()
                .withName(R.string.drawer_item_share)
                .withIcon(getResources().getDrawable(R.drawable.share_icon))
                .withTextColor(getResources().getColor(R.color.colorText))
                .withOnDrawerItemClickListener((view, position, drawerItem) -> showShareMenu());

        SecondaryDrawerItem itemTipsHot = new SecondaryDrawerItem()
                .withEnabled(accessLevel > 1)
                .withLevel(4)
                .withName(R.string.drawer_item_tips_active)
                .withTextColor(getResources().getColor(R.color.colorText))
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    try {
                        Query query = FirebaseFirestore.getInstance().collection("tips")
                                .whereEqualTo("status", 1);
                        Fragment fragment = TipFragment.class.newInstance().withQuery(query).withAdmin(accessLevel == 3);
                        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
                        drawer.closeDrawer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });

        SecondaryDrawerItem itemTipsHistory = new SecondaryDrawerItem()
                .withEnabled(accessLevel > 1)
                .withLevel(4)
                .withName(R.string.drawer_item_tips_archive)
                .withTextColor(getResources().getColor(R.color.colorText))
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    try {
                        Query query = FirebaseFirestore.getInstance().collection("tips")
                                .whereGreaterThan("status", 1);
                        Fragment fragment = TipFragment.class.newInstance().withQuery(query).withAdmin(accessLevel == 3);
                        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
                        drawer.closeDrawer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });

        SecondaryDrawerItem itemTipsAdd = new SecondaryDrawerItem()
                .withEnabled(accessLevel == 3)
                .withLevel(4)
                .withName(R.string.drawer_item_tips_add)
                .withTextColor(getResources().getColor(R.color.colorText))
                .withOnDrawerItemClickListener((view, position, drawerItem) -> setFragment(AddTipFragment.class));

        ExpandableDrawerItem itemTips = new ExpandableDrawerItem()
                .withEnabled(accessLevel > 1)
                .withName(R.string.drawer_item_tips)
                .withTextColor(getResources().getColor(R.color.colorText))
                .withIcon(getResources().getDrawable(R.drawable.tip_icon))
                .withArrowColor(getResources().getColor(R.color.colorText))
                .withSubItems(itemTipsHot, itemTipsHistory, itemTipsAdd);

        PrimaryDrawerItem itemChat = new PrimaryDrawerItem()
                .withEnabled(accessLevel > 1)
                .withName(R.string.drawer_item_chat)
                .withIcon(getResources().getDrawable(R.drawable.chat_icon))
                .withTextColor(getResources().getColor(R.color.colorText))
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    try {
                        Fragment fragment = ChatFragment.class.newInstance().forUser(user);
                        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
                        drawer.closeDrawer();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                });*/

        PrimaryDrawerItem itemUsers = new PrimaryDrawerItem()
                .withEnabled(accessLevel == 3)
                .withName(R.string.drawer_item_users)
                .withIcon(getResources().getDrawable(R.drawable.ic_users))
                .withTextColor(getResources().getColor(R.color.colorTextLight))
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    //Utilities.setFragment(fragmentManager, UserFragment.class, R.id.flContent);
                    return true;
                });

        PrimaryDrawerItem itemSignOut =
                new PrimaryDrawerItem()
                        .withName(R.string.sign_out)
                        .withIcon(R.drawable.ic_sign_out)
                        .withTextColor(getResources().getColor(R.color.colorTextLight))
                        .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                            mAuth.signOut();
                            startActivity(new Intent(this, AuthActivity.class));
                            finish();
                            return true;
                        });

        DrawerBuilder drawerBuilder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .addDrawerItems(//itemHome, itemAbout, itemContact, itemShare,
                        //new DividerDrawerItem(),
                        //itemTips, itemChat, itemUsers,
                        itemUsers,
                        new DividerDrawerItem(),
                        itemSignOut)
                .withSliderBackgroundColor(getResources().getColor(R.color.colorBackgroundDark));

        return drawerBuilder.build();
    }
}
