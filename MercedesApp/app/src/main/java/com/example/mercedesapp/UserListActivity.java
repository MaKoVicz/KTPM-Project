package com.example.mercedesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.Adapters.UserAdapter;
import com.example.BUS.UserBUS;
import com.example.DTO.User;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    //region Initiation
    private ListView userLV;
    private List<User> userList;
    //endregion

    //region Override Methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userLV = (ListView) findViewById(R.id.user_list);

        setupUserList();
        setOnListItemClickListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                returnToMainActivity();
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
        Intent intent = new Intent(UserListActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setupUserList() {
        userList = new ArrayList<>();
        userList = new UserBUS(this).getAllUserData();
        //Toast.makeText(this, userList.size(), Toast.LENGTH_LONG).show();
        UserAdapter userAdapter = new UserAdapter(this, R.layout.user_list_item, userList);
        userLV.setAdapter(userAdapter);
    }

    public void setOnListItemClickListener() {
        userLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(UserListActivity.this, "OK BABE", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion
}