package com.example.mercedesapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
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
    private ListView testDriveLV;
    private List<TestDrive> testdriveList;
    private SearchView searchView;
    private TestDriveAdapter testDriveAdapter;
    private TestDrive testDriveData;
    private String filterText;
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
        registerForContextMenu(testDriveLV);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.client_list_menu, menu);

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.testdrive_list) {
            menu.add(Menu.NONE, 0, 0, "Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        testDriveData = testDriveAdapter.getItem(info.position);

        if (new TestDriveBUS(this).deleteTestDriveData(testDriveData.getRegisterDate())) {
            Toast.makeText(this, "Delete Succeeded", Toast.LENGTH_SHORT).show();
            setupListView();
            testDriveAdapter.getFilter().filter(filterText);
        } else {
            Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show();
            setupListView();
            testDriveAdapter.getFilter().filter(filterText);
        }

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupListView();
        testDriveAdapter.getFilter().filter(filterText);
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
                testDriveAdapter.getFilter().filter(newText);
                filterText = newText;
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

        testDriveAdapter = new TestDriveAdapter(this, R.layout.testdrive_list_item, testdriveList);
        testDriveLV.setAdapter(testDriveAdapter);
        testDriveLV.setTextFilterEnabled(false);
    }

    public void setListItemClickListener() {
        testDriveLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                testDriveData = (TestDrive) parent.getAdapter().getItem(position);

                Intent intent = new Intent(TestDriveListActivity.this, TestDriveDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("registerDate", testDriveData.getRegisterDate());
                startActivity(intent);
            }
        });
    }
    //endregion
}