package com.example.mercedesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BUS.ProductBUS;
import com.example.DTO.Product;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {
    //region Initiation
    private TextView productName, productPrice, productDescription;
    private ImageView procductHeaderImg;
    private Button btnTestDriveRegister;
    private Product product;
    //endregion

    //region Override Methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        InitiateView();
        getExtras();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                returnToProductList();
                return true;
        }

        return false;
    }
    //endregion

    //region Personal Methods
    public void InitiateView() {
        productName = (TextView) findViewById(R.id.product_detail_name_txtv);
        productPrice = (TextView) findViewById(R.id.product_detail_price_txtv);
        productDescription = (TextView) findViewById(R.id.product_detail_description_txtv);
        procductHeaderImg = (ImageView) findViewById(R.id.product_header_img);
        btnTestDriveRegister = (Button) findViewById(R.id.test_drive_register_button);
    }

    public void getExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("ProductName");
            getProductData(name);
        }
    }

    public void getProductData(String name) {
        product = new ProductBUS(this).getProductDetailInformation(name);
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        Picasso.with(this)
                .load(product.getPic1())
                .placeholder(R.drawable.ic_vector_image_loading)
                .into(procductHeaderImg);
        productName.setText(product.getName());
        productPrice.setText(String.valueOf(product.getPrice()));
        productDescription.setText(product.getDescription());
    }

    public void returnToProductList() {
        Intent intent = new Intent(ProductDetailActivity.this, ProductListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    //endregion
}
