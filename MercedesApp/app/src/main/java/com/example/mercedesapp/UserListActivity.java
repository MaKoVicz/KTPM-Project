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
import android.widget.Toast;

import com.example.Adapters.UserAdapter;
import com.example.BUS.UserBUS;
import com.example.DTO.User;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    //region Initiation
    private SearchView searchView;
    private ListView userLV;
    private UserAdapter userAdapter;
    private List<User> userList;
    private User userData;
    private String filterText;
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
        registerForContextMenu(userLV);
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
                Toast.makeText(this, "Search by user name", Toast.LENGTH_SHORT).show();
                return true;
        }

        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.user_list) {
            menu.add(Menu.NONE, 0, 0, "Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        userData = userAdapter.getItem(info.position);

        if (new UserBUS(this).deleteUserData(userData.getEmail())) {
            Toast.makeText(this, "Delete Succeeded", Toast.LENGTH_SHORT).show();
            setupUserList();
            userAdapter.getFilter().filter(filterText);
        } else {
            Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show();
            setupUserList();
            userAdapter.getFilter().filter(filterText);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        returnToMainActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupUserList();
        userAdapter.getFilter().filter(filterText);
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
        userAdapter = new UserAdapter(this, R.layout.user_list_item, userList);
        userLV.setAdapter(userAdapter);
    }

    public void setOnListItemClickListener() {
        userLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userData = (User) parent.getAdapter().getItem(position);

                Intent intent = new Intent(UserListActivity.this, UserDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("userEmail", userData.getEmail());
                startActivity(intent);
            }
        });
    }

    public void setSearchViewOnQueryTextListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                userAdapter.getFilter().filter(newText);
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
    //endregion
}