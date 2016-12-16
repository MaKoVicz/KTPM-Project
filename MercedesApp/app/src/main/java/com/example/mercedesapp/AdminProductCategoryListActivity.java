package com.example.mercedesapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.Adapters.AdminProductCategoryAdapter;
import com.example.BUS.ProductCategoryBUS;
import com.example.DTO.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class AdminProductCategoryListActivity extends AppCompatActivity {

    //region Initiation
    private ListView productCategoryListView;
    private AdminProductCategoryAdapter adminProductCategoryAdapter;
    private List<ProductCategory> categoryList;
    private SearchView searchView;
    private String filterText;
    //endregion

    //region Override Methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        productCategoryListView = (ListView) findViewById(R.id.admin_product_category_list);

        setupListView();
        setOnListViewItemClick();
        registerForContextMenu(productCategoryListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.admin_product_category_list) {
            menu.add(Menu.NONE, 0, 0, "Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ProductCategory productCategory = (ProductCategory) adminProductCategoryAdapter.getItem(info.position);

        if (new ProductCategoryBUS(this).deleteProductCategoryData(productCategory.getName())) {
            Toast.makeText(this, "Delete Succeeded", Toast.LENGTH_SHORT).show();
            setupListView();
            adminProductCategoryAdapter.getFilter().filter(filterText);
        } else {
            Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show();
            setupListView();
            adminProductCategoryAdapter.getFilter().filter(filterText);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_list_menu, menu);

        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.btnSearchList));
        searchView.setIconifiedByDefault(false);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);

        setSearchViewOnFocusChangeListener();
        setSearchViewOnQueryTextListener();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                returnToMainActivity();
                return true;
            case R.id.btnAdd:
                gotoProductCategoryDetail("Add", "");
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

    @Override
    protected void onResume() {
        super.onResume();
        setupListView();
        adminProductCategoryAdapter.getFilter().filter(filterText);
    }
    //endregion

    //region Personal Methods
    public void returnToMainActivity() {
        Intent intent = new Intent(AdminProductCategoryListActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setupListView() {
        categoryList = new ArrayList<>();
        categoryList = new ProductCategoryBUS(this).getProductCategoryData();
        adminProductCategoryAdapter =
                new AdminProductCategoryAdapter(this, R.layout.admin_product_category_list_item, categoryList);

        productCategoryListView.setAdapter(adminProductCategoryAdapter);
        productCategoryListView.setTextFilterEnabled(false);
    }

    public void setOnListViewItemClick() {
        productCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductCategory productCategory = (ProductCategory) parent.getAdapter().getItem(position);
                gotoProductCategoryDetail("Update", productCategory.getName());
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
                adminProductCategoryAdapter.getFilter().filter(newText);
                filterText = newText;
                return true;
            }
        });
    }

    public void setSearchViewOnFocusChangeListener() {
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    searchView.setQuery("", false);
                    searchView.setIconifiedByDefault(true);
                    searchView.setIconified(true);
                }
            }
        });
    }

    public void gotoProductCategoryDetail(String buttonQueryText, String categoryNameData) {
        Intent intent = new Intent(AdminProductCategoryListActivity.this, AdminCategoryDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("buttonQueryText", buttonQueryText);
        intent.putExtra("productCategoryName", categoryNameData);
        startActivity(intent);
    }
    //endregion
}
