package com.example.mercedesapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.example.Adapters.TestDriveAdapter;
import com.example.BUS.TestDriveBUS;
import com.example.DTO.TestDrive;

import java.util.ArrayList;
import java.util.List;

public class TestDriveListActivity extends AppCompatActivity {

    //region Initiation
    int currentMenu = R.menu.admin_menu;
    private ListView testDriveLV;
    private List<TestDrive> testdriveList;
    private SearchView searchView;
    //endregion

    //region Override Methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testdrive_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        testDriveLV = (ListView) findViewById(R.id.testdrive_list);

        setupListView();
        setListItemClickListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin_menu, menu);

        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.btnSearchList));
        searchView.setIconifiedByDefault(false);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);

        setSearchViewOnQueryTextListener();
        setSearchViewOnFocusChangeListener();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                returnToMainActivity();
                return true;
            case R.id.btnSearchList:
                searchView.setIconifiedByDefault(false);
                searchView.setIconified(false);
                return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        returnToMainActivity();
    }
    //endregion

    //region Personal Methods
    public void returnToMainActivity() {
        Intent intent = new Intent(TestDriveListActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setSearchViewOnQueryTextListener() {
        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    public void setSearchViewOnFocusChangeListener() {
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    searchView.setQuery("", false);
                    searchView.setIconifiedByDefault(true);
                    searchView.setIconified(true);
                }
            }
        });
    }

    public void setupListView() {
        testdriveList = new ArrayList<>();
        testdriveList = new TestDriveBUS(this).getAllTestDriveData();

        TestDriveAdapter testDriveAdapter =
                new TestDriveAdapter(this, R.layout.testdrive_list_item, testdriveList);
        testDriveLV.setAdapter(testDriveAdapter);
    }

    public void setListItemClickListener() {
        testDriveLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TestDriveListActivity.this, "OK BABE", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion
}