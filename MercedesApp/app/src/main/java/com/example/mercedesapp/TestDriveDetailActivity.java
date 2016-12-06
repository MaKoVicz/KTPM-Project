package com.example.mercedesapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BUS.TestDriveBUS;
import com.example.DTO.TestDrive;

public class TestDriveDetailActivity extends AppCompatActivity {

    //region Initiation
    private TextView guessName;
    private EditText phone, email, address, registerDate, testProduct;
    private TestDrive testDriveData;
    //endregion

    //region Override Methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testdrive_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getTestDriveData();
        initComponents();
        setComponentsText();
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
                returnToTestDriveList();
                return true;
            case R.id.btnDeleteClientInfo:
                deleteTestDriveData();
                return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        returnToTestDriveList();
    }
    //endregion

    //region Personal Methods
    public void returnToTestDriveList() {
        Intent intent = new Intent(TestDriveDetailActivity.this, TestDriveListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void initComponents() {
        guessName = (TextView) findViewById(R.id.testdrive_detail_name_txtv);
        phone = (EditText) findViewById(R.id.testdrive_detail_phone_edt);
        email = (EditText) findViewById(R.id.testdrive_detail_email_edt);
        address = (EditText) findViewById(R.id.testdrive_detail_address_edt);
        registerDate = (EditText) findViewById(R.id.testdrive_detail_registerDate_edt);
        testProduct = (EditText) findViewById(R.id.testdrive_detail_testProduct_edt);
    }

    public void setComponentsText() {
        guessName.setText(testDriveData.getName());
        phone.setText(testDriveData.getPhone());
        email.setText(testDriveData.getEmail());
        address.setText(testDriveData.getAddress());
        registerDate.setText(testDriveData.getRegisterDate());
        testProduct.setText(testDriveData.getTestProduct());
    }

    public void deleteTestDriveData() {
        new AlertDialog.Builder(this, R.style.AppTheme_Light_Diaglog)
                .setTitle("Message").setMessage("Do you want to delete this information?")
                .setCancelable(false).setIcon(R.drawable.ic_vector_message)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (new TestDriveBUS(TestDriveDetailActivity.this).deleteTestDriveData(testDriveData.getRegisterDate())) {
                            Toast.makeText(TestDriveDetailActivity.this, " Delete Succeeded", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(TestDriveDetailActivity.this, TestDriveListActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            Toast.makeText(TestDriveDetailActivity.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    public void getTestDriveData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            testDriveData = new TestDriveBUS(this).getTestDriveDetailData(bundle.getString("registerDate"));
        }
    }
    //endregion
}
