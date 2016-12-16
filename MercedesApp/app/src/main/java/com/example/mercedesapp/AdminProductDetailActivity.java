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
import android.widget.EditText;
import android.widget.Toast;

import com.example.BUS.ProductBUS;
import com.example.DTO.Product;

public class AdminProductDetailActivity extends AppCompatActivity {

    //region Initiation
    private EditText name, color, category, description, pictureLink, price;
    private Button btnProductQuery;
    private String productName;
    //endregion

    //region Override Methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initComponents();
        setComponentsData();
        setButtonQueryClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                returnToProductList();
                return true;
            case R.id.btnDeleteClientInfo:
                deleteProductData();
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
    public void returnToProductList() {
        Intent intent = new Intent(AdminProductDetailActivity.this, AdminProductListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void initComponents() {
        name = (EditText) findViewById(R.id.admin_product_detail_name_edt);
        color = (EditText) findViewById(R.id.admin_product_detail_color_edt);
        category = (EditText) findViewById(R.id.admin_product_detail_category_edt);
        description = (EditText) findViewById(R.id.admin_product_detail_description_edt);
        pictureLink = (EditText) findViewById(R.id.admin_product_detail_picture_edt);
        price = (EditText) findViewById(R.id.admin_product_detail_price_edt);
        btnProductQuery = (Button) findViewById(R.id.btnQueryProductData);
    }

    public void setComponentsData() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            if (extras.getString("buttonQueryText").equals("Update")) {
                productName = extras.getString("productName");
                Product product = new ProductBUS(this).getProductDetailInformation(productName);

                btnProductQuery.setText("Update");
                name.setText(product.getName());
                color.setText(product.getColor());
                price.setText(product.getPrice());
                category.setText(product.getCategory());
                description.setText(product.getDescription());
                pictureLink.setText(product.getPic1());
            } else {
                btnProductQuery.setText("Add");
                name.setText("");
                color.setText("");
                price.setText("");
                category.setText("");
                description.setText("");
                pictureLink.setText("");
            }
        }
    }

    public void setButtonQueryClick() {
        btnProductQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnProductQuery.getText().toString().equals("Add")) {
                    addProductData();
                } else {
                    updateProductData();
                }
            }
        });
    }

    public void addProductData() {
        if (checkEmptyData()) {
            Toast.makeText(this, "Fields can not be empty!!!", Toast.LENGTH_SHORT).show();
            return;
        }

        Product product = new Product();
        product.setName(name.getText().toString());
        product.setColor(color.getText().toString());
        product.setPrice(price.getText().toString());
        product.setCategory(category.getText().toString());
        product.setDescription(description.getText().toString());
        product.setPic1(pictureLink.getText().toString());

        if (new ProductBUS(this).addProductData(product)) {
            Toast.makeText(this, "Add Succeeded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Add Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateProductData() {
        if (checkEmptyData()) {
            Toast.makeText(this, "Fields can not be empty!!!", Toast.LENGTH_SHORT).show();
            return;
        }

        Product product = new Product();
        product.setName(name.getText().toString());
        product.setColor(color.getText().toString());
        product.setPrice(price.getText().toString());
        product.setCategory(category.getText().toString());
        product.setDescription(description.getText().toString());
        product.setPic1(pictureLink.getText().toString());

        if (new ProductBUS(this).updateProductData(productName, product)) {
            Toast.makeText(this, "Update Succeeded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteProductData() {
        new AlertDialog.Builder(this, R.style.AppTheme_Light_Diaglog)
                .setTitle("Message").setMessage("Do you want to delete this information?")
                .setCancelable(false).setIcon(R.drawable.ic_vector_message)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (new ProductBUS(AdminProductDetailActivity.this).deleteProductData(productName)) {
                            Toast.makeText(AdminProductDetailActivity.this, "Delete Succeeded", Toast.LENGTH_SHORT).show();
                            returnToProductList();
                        } else {
                            Toast.makeText(AdminProductDetailActivity.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public boolean checkEmptyData() {
        if (name.getText().toString().equals("") || color.getText().toString().equals("") ||
                price.getText().toString().equals("") || category.getText().toString().equals("") ||
                description.getText().toString().equals("") || pictureLink.getText().toString().equals("")) {
            return true;
        }

        return false;
    }
    //endregion
}
