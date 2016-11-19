package com.example.mercedesapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.BUS.UserBUS;
import com.example.DTO.User;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {

    //region Initiation
    private static final String TAG = "SignupActivity";
    User user;

    @InjectView(R.id.signup_userNameTextView)
    EditText usernameTextView;
    @InjectView(R.id.signup_passwordTextView)
    EditText passwordTextView;
    @InjectView(R.id.signup_retypePasswordTextView)
    EditText retypePasswordTextView;
    @InjectView(R.id.signup_guessNameTextView)
    EditText guessNameTextView;
    @InjectView(R.id.signup_dobTextView)
    EditText dobTextView;
    @InjectView(R.id.signup_emailTextView)
    EditText emailTextView;
    @InjectView(R.id.signup_phoneTextView)
    EditText phoneTextView;
    @InjectView(R.id.btn_signup)
    Button btnSignup;
    @InjectView(R.id.link_login_textView)
    TextView linkLoginTextView;
    //endregion

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
        onButtonSignUpClick();
        onTextViewLinkLoginClick();
    }

    public void onButtonSignUpClick() {
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    public void onTextViewLinkLoginClick() {
        linkLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToLoginActivity();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!isValidate()) {
            return;
        }

        if (!checkSignup()) {
            onSignupFailed();
            return;
        }

        btnSignup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
                onSignupSuccess();
            }
        }, 3000);
    }

    public void onSignupSuccess() {
        btnSignup.setEnabled(true);
        returnToLoginActivity();
    }

    public void onSignupFailed() {
        new AlertDialog.Builder(this, R.style.AppTheme_Light_Diaglog).setTitle("Message")
                .setMessage("Username already exists")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setCancelable(false)
                .setIcon(R.drawable.ic_vector_message).show();

        btnSignup.setEnabled(true);
    }

    public boolean isValidate() {
        boolean valid = true;
        String username = usernameTextView.getText().toString();
        String password = passwordTextView.getText().toString();
        String retypePassword = retypePasswordTextView.getText().toString();
        String email = emailTextView.getText().toString();
        String phone = phoneTextView.getText().toString();
        //String dob = dobTextView.getText().toString();
        String guessName = guessNameTextView.getText().toString();

        if (username.isEmpty() || username.length() < 6) {
            usernameTextView.setError("Username must be at least 6 characters");
            valid = false;
        } else {
            usernameTextView.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            passwordTextView.setError("Password must be at least 6 characters");
            valid = false;
        } else {
            passwordTextView.setError(null);
        }

        if (!retypePassword.equals(password)) {
            retypePasswordTextView.setError("Password doesn't match");
            valid = false;
        } else {
            retypePasswordTextView.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailTextView.setError("Please enter a valid email address");
            valid = false;
        } else {
            emailTextView.setError(null);
        }

        if (guessName.isEmpty()) {
            guessNameTextView.setError("Please enter your name");
            valid = false;
        } else {
            guessNameTextView.setError(null);
        }

        if (phone.isEmpty() || phone.length() < 10) {
            phoneTextView.setError("Please enter a valid phone number");
            valid = false;
        } else {
            phoneTextView.setError(null);
        }

        return valid;
    }

    public void returnToLoginActivity() {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public boolean checkSignup() {
        user = new User();
        user.setUsername(usernameTextView.getText().toString());
        user.setPassword(passwordTextView.getText().toString());
        user.setName(guessNameTextView.getText().toString());
        user.setEmail(emailTextView.getText().toString());
        user.setPhone(phoneTextView.getText().toString());
        user.setDob(dobTextView.getText().toString());
        return new UserBUS(this).addUserData(user);
    }
}
