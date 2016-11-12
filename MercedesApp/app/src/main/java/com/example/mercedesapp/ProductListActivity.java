package com.example.mercedesapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.Adapters.ProductAdapter;
import com.example.BUS.ProductBUS;
import com.example.DTO.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    private List<Product> products;
    private ListView productList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        productList = (ListView) findViewById(R.id.productListView);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String category = extras.getString("Category");
            setupProductList(category);
        }
    }

    public void setupProductList(String category) {
        products = new ArrayList<>();
        products = new ProductBUS(this).getProductData(category);

        ProductAdapter productAdapter = new ProductAdapter(this, R.layout.product_list_item, products);
        productList.setAdapter(productAdapter);
    }
}
