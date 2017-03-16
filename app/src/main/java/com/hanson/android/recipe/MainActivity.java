package com.hanson.android.recipe;

import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.hanson.android.recipe.Helper.DBHelper;
import com.hanson.android.recipe.dummy.DummyContent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DBHelper mDBhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //mDBhelper = new DBHelper(this);

//        try
//        {
//            mDBhelper.open();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        /* CATEGORY = "category";
        RECIPENAME = "recipename";
        AUTHOR = "author";
        UPLOADDATE = "uploaddate";
        HOWTO = "howto";
        DESCRIPTION = "description";
        THUMBNAIL = "thumbnail";
        MAINIMG = "mainimg";
        LIKECOUNT = "likecount";
        _RECIPESTABLENAME = "recipes";
//        */
//        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.imgr_main_bibimbap);
//        byte[] imgresource = mDBhelper.getByteArrayFromDrawable(image);
//
//        // 시스템으로부터 현재시간(ms) 가져오기
//        long now = System.currentTimeMillis();
//        // Data 객체에 시간을 저장한다.
//        Date date = new Date(now);
//        // 각자 사용할 포맷을 정하고 문자열로 만든다.
//        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        String strNow = sdfNow.format(date);
//        mDBhelper.recipes_insertColumn("Korea", "Bibimbap", "Shyjoo",strNow, "1. Prepare and cook ingredients as below.",
//               "Bibimbap (비빔밥) is probably one of the most well-known and beloved Korean dishes to many people.",
//                imgresource, imgresource, 0 );
//        mDBhelper.recipes_statemet("Korea", "Bibimbap", "Shyjoo",strNow, "1. Prepare and cook ingredients as below.",
//               "Bibimbap (비빔밥) is probably one of the most well-known and beloved Korean dishes to many people.",
//                0 );


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check search recipes menu on the slide menu
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.setCheckedItem(R.id.nav_search);
                //connect to search recipes page
                FragmentManager manager = getSupportFragmentManager();
                SearchFragment searchFragment = new SearchFragment();
                manager.beginTransaction().replace(R.id.root_layout, searchFragment, searchFragment.getTag()).addToBackStack(null).commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        HomeFragment homeFragment = new HomeFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.root_layout, homeFragment, homeFragment.getTag()).commit();
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

        FragmentManager manager = getSupportFragmentManager();

        if (id == R.id.nav_home)
        {
            //connect to home page
            HomeFragment homeFragment = new HomeFragment();
            manager.beginTransaction().replace(R.id.root_layout, homeFragment, homeFragment.getTag()).addToBackStack(null).commit();

        }
        else if (id == R.id.nav_login)
        {
            //connect to login page
            LoginFragment loginFragment = new LoginFragment();
            manager.beginTransaction().replace(R.id.root_layout, loginFragment, loginFragment.getTag()).addToBackStack(null).commit();

        }
        else if (id == R.id.nav_category)
        {
            //connect to category page
            CategoryFragment categoryFragment = new CategoryFragment();
            manager.beginTransaction().replace(R.id.root_layout, categoryFragment, categoryFragment.getTag()).addToBackStack(null).commit();

        }
        else if (id == R.id.nav_search)
        {
            //connect to search recipes page
            SearchFragment searchFragment = new SearchFragment();
            manager.beginTransaction().replace(R.id.root_layout, searchFragment, searchFragment.getTag()).addToBackStack(null).commit();
        }
        else if (id == R.id.nav_add)
        {
            //connect to add recipe page
//            AddRecipeFragment addRecipeFragment = new AddRecipeFragment();
//            manager.beginTransaction().replace(R.id.root_layout, addRecipeFragment, addRecipeFragment.getTag()).addToBackStack(null).commit();
            Intent intent = new Intent(this, AddRecipeActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_info)
        {
            //connect to information page
            InformationFragment informationFragment = new InformationFragment();
            manager.beginTransaction().replace(R.id.root_layout, informationFragment, informationFragment.getTag()).addToBackStack(null).commit();

        }
        else if (id == R.id.nav_logout)
        {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onDestroy() {
        mDBhelper.close();
        super.onDestroy();
    }


}
