package com.example.mercedesapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.BUS.UserBUS;
import com.example.DTO.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    //region Initiation
    private final Calendar myCalendar = Calendar.getInstance();
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
    @InjectView(R.id.signup_addressTextView)
    EditText addressTextView;
    @InjectView(R.id.signup_phoneTextView)
    EditText phoneTextView;
    @InjectView(R.id.btn_signup)
    Button btnSignup;
    @InjectView(R.id.link_login_textView)
    TextView linkLoginTextView;
    //endregion

    //region Override Methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
        addDateTimePicker();
        onButtonSignUpClick();
        onTextViewLinkLoginClick();
    }
    //endregion

    //region Personal Methods
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

        if (checkEmailExisted()) {
            onEmailExisted();
            return;
        }

        if (!checkSignup()) {
            onUserNameExisted();
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

    public void onUserNameExisted() {
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

    public void onEmailExisted() {
        new AlertDialog.Builder(this, R.style.AppTheme_Light_Diaglog).setTitle("Message")
                .setMessage("Email already exists")
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
        String address = addressTextView.getText().toString();
        String dob = dobTextView.getText().toString();
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

        if (address.isEmpty()) {
            addressTextView.setError("Please enter your address");
            valid = false;
        } else {
            addressTextView.setError(null);
        }

        if (dob.isEmpty()) {
            dobTextView.setError("Please enter your date of birth");
            valid = false;
        } else {
            dobTextView.setError(null);
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

    public boolean checkEmailExisted() {
        return new UserBUS(this).checkEmailExisted(emailTextView.getText().toString());
    }

    public boolean checkSignup() {
        user = new User();
        user.setUsername(usernameTextView.getText().toString());
        user.setPassword(passwordTextView.getText().toString());
        user.setName(guessNameTextView.getText().toString());
        user.setEmail(emailTextView.getText().toString());
        user.setPhone(phoneTextView.getText().toString());
        user.setDob(dobTextView.getText().toString());
        user.setAddress(addressTextView.getText().toString());
        return new UserBUS(this).addUserData(user);
    }

    public void addDateTimePicker() {
        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDOBText();
            }
        };

        dobTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int inType = dobTextView.getInputType();
                dobTextView.setInputType(InputType.TYPE_NULL);
                dobTextView.onTouchEvent(event);
                dobTextView.setInputType(inType);
                return true;
            }
        });

        dobTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(SignupActivity.this, R.style.AppTheme_Light_Diaglog, dateSetListener, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });
    }

    public void updateDOBText() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        dobTextView.setText(sdf.format(myCalendar.getTime()));
    }
    //endregion
}
