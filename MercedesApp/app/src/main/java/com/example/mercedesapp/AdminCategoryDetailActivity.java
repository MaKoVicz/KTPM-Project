package com.example.mercedesapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.BUS.ProductBUS;
import com.example.BUS.ProductCategoryBUS;
import com.example.DTO.ProductCategory;

public class AdminCategoryDetailActivity extends AppCompatActivity {

    //region Initiation
    private EditText categoryNameTextView, imgLinkTextView;
    private Button btnQuery;
    private String categoryName;
    //endregion

    //region Override Methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoryNameTextView = (EditText) findViewById(R.id.admin_category_detail_name_edt);
        imgLinkTextView = (EditText) findViewById(R.id.admin_category_detail_imgLink_edt);
        btnQuery = (Button) findViewById(R.id.btnQueryProductCategoryData);

        setComponentsText();
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
                returnToAdminCategoryList();
                return true;
            case R.id.btnDeleteClientInfo:
                deleteProductCategoryData();
                return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        returnToAdminCategoryList();
    }
    //endregion

    //region Personal Methods
    public void returnToAdminCategoryList() {
        Intent intent = new Intent(AdminCategoryDetailActivity.this, AdminProductCategoryListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void addProductCategoryData() {
        if (categoryNameTextView.getText().toString().equals("")
                || imgLinkTextView.getText().toString().equals("")) {
            Toast.makeText(this, "Fields can not be empty!!!", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(categoryNameTextView.getText().toString());
        productCategory.setImageURL(imgLinkTextView.getText().toString());

        if (new ProductCategoryBUS(this).addProductCategoryData(productCategory)) {
            Toast.makeText(this, "Add Succeeded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Add Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateProductCategoryData() {
        if (categoryNameTextView.getText().toString().equals("")
                || imgLinkTextView.getText().toString().equals("")) {
            Toast.makeText(this, "Fields can not be empty!!!", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductCategory productCategory = new ProductCategory();
        productCategory.setName(categoryNameTextView.getText().toString());
        productCategory.setImageURL(imgLinkTextView.getText().toString());

        if (new ProductCategoryBUS(this).updateProductCategoryData(categoryName, productCategory)) {
            Toast.makeText(this, "Update Succeeded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteProductCategoryData() {
        new AlertDialog.Builder(this, R.style.AppTheme_Light_Diaglog)
                .setTitle("Message").setMessage("Do you want to delete this data?")
                .setCancelable(false).setIcon(R.drawable.ic_vector_message)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (new ProductCategoryBUS(AdminCategoryDetailActivity.this).deleteProductCategoryData(categoryName)) {
                            new ProductBUS(AdminCategoryDetailActivity.this).updateProductWhenDeleteCategory(categoryName);
                            Toast.makeText(AdminCategoryDetailActivity.this, "Delete Succeeded", Toast.LENGTH_SHORT).show();
                            returnToAdminCategoryList();
                        } else {
                            Toast.makeText(AdminCategoryDetailActivity.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public void setButtonQueryClick() {
        btnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnQuery.getText().toString().equals("Add")) {
                    addProductCategoryData();
                } else {
                    updateProductCategoryData();
                }
            }
        });
    }

    public void setComponentsText() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            if (extras.getString("buttonQueryText").equals("Update")) {
                categoryName = extras.getString("productCategoryName");
                ProductCategory productCategory = new ProductCategoryBUS(this)
                        .getProductCategoryDetailData(categoryName);

                btnQuery.setText("Update");
                categoryNameTextView.setText(productCategory.getName());
                imgLinkTextView.setText(productCategory.getImageURL());
            } else {
                btnQuery.setText("Add");
                categoryNameTextView.setText("");
                imgLinkTextView.setText("");
            }
        }
    }
    //endregion
}
