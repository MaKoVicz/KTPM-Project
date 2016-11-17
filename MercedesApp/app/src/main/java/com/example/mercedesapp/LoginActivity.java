package com.example.mercedesapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {

    //region Initiation
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    @InjectView(R.id.userNameTextView)
    EditText usernameEditText;
    @InjectView(R.id.passwordTextView)
    EditText passwordEditText;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.link_signup_textView)
    TextView signupLinkTextView;
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        ButterKnife.inject(this);

        onLoginButtonClick();
    }

    public void onLoginButtonClick() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        Log.d(TAG, "LoginActivity");

        if (!isValidate()) {
            onLoginFail();
            return;
        }

        btnLogin.setEnabled(false);
        final ProgressDialog progressDiaglog = new ProgressDialog(this, R.style.AppTheme_Dark_Dialog);
        progressDiaglog.setIndeterminate(true);
        progressDiaglog.setMessage("Authenticating...");
        progressDiaglog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        progressDiaglog.dismiss();
                        onLoginSuccess();
                    }
                }, 3000);
    }

    public void onLoginSuccess() {
        btnLogin.setEnabled(true);

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void onLoginFail() {
        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        btnLogin.setEnabled(true);
    }

    public boolean isValidate() {
        boolean valid = true;
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (username.isEmpty()) {
            usernameEditText.setError("Please enter a valid Username");
            valid = false;
        } else {
            usernameEditText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordEditText.setError("Password must be between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        return valid;
    }
}