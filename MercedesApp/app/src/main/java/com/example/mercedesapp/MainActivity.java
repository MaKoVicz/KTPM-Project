package com.example.mercedesapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.Adapters.NavListAdapter;
import com.example.DTO.NavItem;
import com.example.fragments.AboutFragment;
import com.example.fragments.ContactFragment;
import com.example.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //region Initiation
    private DrawerLayout drawerLayout;
    private LinearLayout navDrawerPane;
    private ListView navList;
    private List<NavItem> navListItems;
    private List<Fragment> listFragments;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    //endregion

    //region Override Method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer_layout);
        navDrawerPane = (LinearLayout) findViewById(R.id.navDrawerPane);
        navList = (ListView) findViewById(R.id.navDrawerList);

        setupNavList();
        setupFragmentList();
        setListItemClickListener();
        setupNavDrawerButtonClick();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region Personal Method
    public void setupNavList() {
        navListItems = new ArrayList<>();
        navListItems.add(new NavItem("Home", R.drawable.ic_vector_home));
        navListItems.add(new NavItem("About us", R.drawable.ic_vector_about));
        navListItems.add(new NavItem("Contact us", R.drawable.ic_vector_contact));

        NavListAdapter navListAdapter = new NavListAdapter(getApplicationContext(), R.layout.nav_list_item, navListItems);
        navList.setAdapter(navListAdapter);
    }

    public void setupFragmentList() {
        listFragments = new ArrayList<>();
        listFragments.add(new HomeFragment());
        listFragments.add(new AboutFragment());
        listFragments.add(new ContactFragment());

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContentLayout, listFragments.get(0)).commit();

        setTitle(navListItems.get(0).getTitle());
        navList.setItemChecked(0, true);
        drawerLayout.closeDrawer(navDrawerPane);
    }

    public void setListItemClickListener() {
        navList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContentLayout, listFragments.get(position)).commit();

                setTitle(navListItems.get(position).getTitle());
                navList.setItemChecked(position, true);
                drawerLayout.closeDrawer(navDrawerPane);
            }
        });
    }

    public void setupNavDrawerButtonClick() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
    }
    //endregion
}
