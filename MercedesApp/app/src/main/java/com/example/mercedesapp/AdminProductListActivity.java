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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.Adapters.AdminProductAdapter;
import com.example.BUS.ProductBUS;
import com.example.DTO.Product;

import java.util.ArrayList;
import java.util.List;

public class AdminProductListActivity extends AppCompatActivity {

    //region Initiation
    private ListView productListView;
    private List<Product> productList;
    private SearchView searchView;
    private String filterText;
    private AdminProductAdapter adminProductAdapter;
    //endregion

    //region Override Methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        productListView = (ListView) findViewById(R.id.admin_product_list);

        setupListView();
        setOnListViewItemClick();
        registerForContextMenu(productListView);
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
                gotoAdminProductDetail("Add", "");
                return true;
            case R.id.btnSearchList:
                searchView.setIconifiedByDefault(false);
                searchView.setIconified(false);
                Toast.makeText(this, "Search by product name", Toast.LENGTH_SHORT).show();
                return true;
        }

        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.admin_product_list) {
            menu.add(Menu.NONE, 0, 0, "Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Product product = (Product) adminProductAdapter.getItem(info.position);

        if (new ProductBUS(this).deleteProductData(product.getName())) {
            Toast.makeText(this, "Delete Succeeded", Toast.LENGTH_SHORT).show();
            setupListView();
            adminProductAdapter.getFilter().filter(filterText);
        } else {
            Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show();
            setupListView();
            adminProductAdapter.getFilter().filter(filterText);
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
        setupListView();
        adminProductAdapter.getFilter().filter(filterText);
    }
    //endregion

    //region Personal Methods
    public void returnToMainActivity() {
        Intent intent = new Intent(AdminProductListActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setupListView() {
        productList = new ArrayList<>();
        productList = new ProductBUS(this).getAllProductData();
        adminProductAdapter =
                new AdminProductAdapter(this, R.layout.admin_product_list_item, productList);

        productListView.setAdapter(adminProductAdapter);
        productListView.setTextFilterEnabled(false);
    }

    public void setOnListViewItemClick() {
        productListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) parent.getAdapter().getItem(position);
                gotoAdminProductDetail("Update", product.getName());
            }
        });
    }

    public void gotoAdminProductDetail(String buttonQueryText, String productNameData) {
        Intent intent = new Intent(AdminProductListActivity.this, AdminProductDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("buttonQueryText", buttonQueryText);
        intent.putExtra("productName", productNameData);
        startActivity(intent);
    }

    public void setSearchViewOnQueryTextListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adminProductAdapter.getFilter().filter(newText);
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
