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

import com.example.BUS.UserBUS;
import com.example.DTO.User;

public class UserDetailActivity extends AppCompatActivity {

    //region Initiation
    private TextView guessName;
    private EditText username, password, dob, address, phone, email;
    private User userData;
    //endregion

    //region Override Methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getUserDetailData();
        initComponents();
        setComponentsData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.client_detail_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                returnToUserList();
                return true;
            case R.id.btnDeleteClientInfo:
                deleteUserData();
                return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        returnToUserList();
    }
    //endregion

    //region Personal Methods
    public void returnToUserList() {
        Intent intent = new Intent(UserDetailActivity.this, UserListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void initComponents() {
        guessName = (TextView) findViewById(R.id.user_detail_name_txtv);
        username = (EditText) findViewById(R.id.user_detail_username_edt);
        password = (EditText) findViewById(R.id.user_detail_password_edt);
        dob = (EditText) findViewById(R.id.user_detail_dob_edt);
        address = (EditText) findViewById(R.id.user_detail_address_edt);
        phone = (EditText) findViewById(R.id.user_detail_phone_edt);
        email = (EditText) findViewById(R.id.user_detail_email_edt);
    }

    public void setComponentsData() {
        guessName.setText(userData.getName());
        username.setText(userData.getUsername());
        password.setText(userData.getPassword());
        dob.setText(userData.getDob());
        address.setText(userData.getAddress());
        phone.setText(userData.getPhone());
        email.setText(userData.getEmail());
    }

    public void getUserDetailData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            userData = new UserBUS(this).getUserDetailDataByEmail(bundle.getString("userEmail"));
        }
    }

    public void deleteUserData() {
        new AlertDialog.Builder(this, R.style.AppTheme_Light_Diaglog)
                .setTitle("Message").setMessage("Do you want to delete this information?")
                .setCancelable(false).setIcon(R.drawable.ic_vector_message)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (new UserBUS(UserDetailActivity.this).deleteUserData(email.getText().toString())) {
                            Toast.makeText(UserDetailActivity.this, "Delete Succeeded", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserDetailActivity.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
    //endregion
}
