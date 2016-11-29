package com.example.mercedesapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.Adapters.TestDriveAdapter;
import com.example.BUS.TestDriveBUS;
import com.example.DTO.TestDrive;

import java.util.ArrayList;
import java.util.List;

public class TestDriveListActivity extends AppCompatActivity {
    private ListView testDriveLV;
    private List<TestDrive> testdriveList;

    //region Override Methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testdrive_list);
        testDriveLV = (ListView) findViewById(R.id.testdrive_list);

        setupListView();
        setListItemClickListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    //endregion

    //region Personal Methods
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