package com.baidingapp.faceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestPasswordResetCallback;

public class LoginActivity extends AppCompatActivity {

    private AutoCompleteTextView mEmailAddressView;
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    // private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setTitle(getString(R.string.action_login));
        mEmailAddressView = (AutoCompleteTextView) findViewById(R.id.email_address_login);
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.username_login);


        mPasswordView = (EditText) findViewById(R.id.password_login);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        Button mLoginButton = (Button) findViewById(R.id.action_login_second);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


        Button mResetPasswordButton = (Button) findViewById(R.id.action_reset_password);
        mResetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptResetPassword();
            }
        });

        // mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    private void attemptLogin() {
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        final String username = mUsernameView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            mProgressView.setVisibility(View.VISIBLE);

            AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null) {
                        Intent intent = new Intent(LoginActivity.this, BasicInformationDefaultActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    } else {
                        mProgressView.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }


    private void attemptResetPassword() {
        mEmailAddressView.setError(null);
        final String emailAddress = mEmailAddressView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(emailAddress)) {
            mEmailAddressView.setError(getString(R.string.error_field_required));
            focusView = mEmailAddressView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            AVUser.requestPasswordResetInBackground(emailAddress, new RequestPasswordResetCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        Toast.makeText(LoginActivity.this, R.string.please_reset_password, Toast.LENGTH_SHORT).show();
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    private boolean isPasswordValid(String password) {
        //TODO
        return password.length() > 6;
    }
}











