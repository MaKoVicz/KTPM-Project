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

import com.example.Adapters.ProductAdapter;
import com.example.BUS.ProductBUS;
import com.example.DTO.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    private List<Product> products;
    private ListView productList;

    //region Override Methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        productList = (ListView) findViewById(R.id.productListView);

        getExtras();
        setListItemClickListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product, menu);
        setTitle("Mercedes");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                returnToMain();
                return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        returnToMain();
    }

    //endregion

    //region Personal Methods
    public void setupProductList(String category) {
        products = new ArrayList<>();
        products = new ProductBUS(this).getProductData(category);

        ProductAdapter productAdapter = new ProductAdapter(this, R.layout.product_list_item, products);
        productList.setAdapter(productAdapter);
    }

    public void setListItemClickListener() {
        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                intent.putExtra("ProductName", products.get(position).getName());
                startActivity(intent);
            }
        });
    }

    public void returnToMain() {
        Intent intent = new Intent(ProductListActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void getExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String category = extras.getString("Category");
            setupProductList(category);
        }
    }
    //endregion
}