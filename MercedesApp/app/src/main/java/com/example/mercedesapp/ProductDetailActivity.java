package com.example.mercedesapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BUS.ProductBUS;
import com.example.BUS.TestDriveBUS;
import com.example.DTO.CurrentLoginUser;
import com.example.DTO.Product;
import com.example.DTO.TestDrive;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductDetailActivity extends AppCompatActivity {
    //region Initiation
    private TextView productName, productPrice, productDescription;
    private ImageView productHeaderImg;
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
        buttonTestDriveRegisterClick();
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

    @Override
    public void onBackPressed() {
        returnToProductList();
    }
    //endregion

    //region Personal Methods
    public void InitiateView() {
        productName = (TextView) findViewById(R.id.product_detail_name_txtv);
        productPrice = (TextView) findViewById(R.id.product_detail_price_txtv);
        productDescription = (TextView) findViewById(R.id.product_detail_description_txtv);
        productHeaderImg = (ImageView) findViewById(R.id.product_header_img);
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
        Picasso.with(this)
                .load(product.getPic1())
                .placeholder(R.drawable.ic_vector_image_loading)
                .into(productHeaderImg);
        productName.setText(product.getName());
        productPrice.setText(String.valueOf(product.getPrice()));
        productDescription.setText(product.getDescription());
    }

    public void returnToProductList() {
        Intent intent = new Intent(ProductDetailActivity.this, ProductListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void buttonTestDriveRegisterClick() {
        btnTestDriveRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTestDriveDialog();
            }
        });
    }

    public void showTestDriveDialog() {
        new AlertDialog.Builder(this, R.style.AppTheme_Light_Diaglog).setTitle("Message")
                .setMessage("Do you want to register to test drive this car")
                .setCancelable(false).setIcon(R.drawable.ic_vector_message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (addTestDriveRegisterData()) {
                            Toast.makeText(ProductDetailActivity.this, "Successful registration", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProductDetailActivity.this, "Error!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public boolean addTestDriveRegisterData() {
        TestDrive testDriveData = new TestDrive();

        testDriveData.setName(CurrentLoginUser.currentUser.getName());
        testDriveData.setAddress(CurrentLoginUser.currentUser.getAddress());
        testDriveData.setPhone(CurrentLoginUser.currentUser.getPhone());
        testDriveData.setEmail(CurrentLoginUser.currentUser.getEmail());
        testDriveData.setRegisterDate(returnCurrentDate());
        testDriveData.setTestProduct(product.getName());

        return new TestDriveBUS(this).addTestDriveRegisterData(testDriveData);
    }

    public String returnCurrentDate() {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy 'at' hh:mm:ss a");

        return ft.format(dNow);
    }
    //endregion
}
