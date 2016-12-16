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

import com.example.Adapters.AdminProductAdapter;
import com.example.BUS.ProductBUS;
import com.example.DTO.Product;

import java.util.ArrayList;
import java.util.List;

public class AdminProductListActivity extends AppCompatActivity {

    //region Initiation
    private ListView productListView;
    private List<Product> productList;
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
            case R.id.btnAdd:
                gotoAdminProductDetail("Add", "");
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
        Intent intent = new Intent(AdminProductListActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setupListView() {
        productList = new ArrayList<>();
        productList = new ProductBUS(this).getAllProductData();
        AdminProductAdapter adminProductAdapter =
                new AdminProductAdapter(this, R.layout.admin_product_list_item, productList);

        productListView.setAdapter(adminProductAdapter);
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
    //endregion
}
