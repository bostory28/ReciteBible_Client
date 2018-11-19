package com.aucklandvision.biblerecite;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.aucklandvision.biblerecite.init.InitPresenter;
import com.aucklandvision.biblerecite.db.OpenSqliteDatabase;
import com.aucklandvision.biblerecite.main.MainFragment;
import com.aucklandvision.biblerecite.main.UserInfo;
import com.aucklandvision.biblerecite.utils.DirectoryOfApk;
import com.aucklandvision.biblerecite.utils.FontUtil;
import com.aucklandvision.biblerecite.utils.ThemeColor;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private MainFragment mainFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new DirectoryOfApk().makeDirectoryOfApp();
        // get token
        gettoken();
        //setting theme, font
        settingFontTheme();
        // grant
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            else
                init();
        } else
            init();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        mainFragment.displayNavigationItems(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setTheme(int resid) {
        super.setTheme(resid);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void gettoken() {
        FirebaseMessaging.getInstance().subscribeToTopic("AllMembers");
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this,  new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
            }
        });
    }

    public void settingFontTheme() {
        OpenSqliteDatabase openSqliteDatabase = new OpenSqliteDatabase(this);
        openSqliteDatabase.openDatabase();
        InitPresenter initPresenter = new InitPresenter();
        UserInfo userInfo = initPresenter.getInit();
        String themeName = userInfo.getThemeName();
        FontUtil.fontSize = userInfo.getFontsize();
        FontUtil.typeface = new FontUtil(this).setFonttype(userInfo.getFonttype());
        ThemeColor.ARGB = (int) Long.parseLong(userInfo.getArgb().substring(2), 16);
        ThemeColor.STRARGB = userInfo.getArgb();
        ThemeColor themeColor = new ThemeColor(this);
        themeColor.applyTheme(themeName);
    }

    public void init() {
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headView = navigationView.getHeaderView(0);
        headView.setBackgroundColor(ThemeColor.ARGB);
        navigationView.getBackground().setAlpha(220);
        navigationView.setNavigationItemSelectedListener(this);

        //MainView Fragment declare
        mainFragment = new MainFragment();
        mainFragment.setMenu(navigationView.getMenu());
        getFragmentManager().beginTransaction().replace(R.id.main_container, mainFragment).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        init();
    }
}
