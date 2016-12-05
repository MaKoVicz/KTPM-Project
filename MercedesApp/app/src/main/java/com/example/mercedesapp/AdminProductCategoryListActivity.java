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

import com.example.Adapters.AdminProductCategoryAdapter;
import com.example.BUS.ProductCategoryBUS;
import com.example.DTO.ProductCategory;

import java.util.ArrayList;
import java.util.List;

public class AdminProductCategoryListActivity extends AppCompatActivity {

    //region Initiation
    private ListView productCategoryListView;
    private List<ProductCategory> categoryList;
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_list_menu, menu);
        return true;
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
        Intent intent = new Intent(AdminProductCategoryListActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setupListView() {
        categoryList = new ArrayList<>();
        categoryList = new ProductCategoryBUS(this).getProductCategoryData();
        AdminProductCategoryAdapter adminProductCategoryAdapter =
                new AdminProductCategoryAdapter(this, R.layout.admin_product_category_list_item, categoryList);

        productCategoryListView.setAdapter(adminProductCategoryAdapter);
    }

    public void setOnListViewItemClick() {
        productCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(AdminProductCategoryListActivity.this, "OK BABE", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion
}
