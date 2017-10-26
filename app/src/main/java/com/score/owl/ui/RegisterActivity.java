package com.score.owl.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.score.owl.R;
import com.score.owl.pojo.User;
import com.score.owl.util.CryptoUtil;
import com.score.owl.util.PreferenceUtil;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getName();

    private EditText usernameEditText;
    private EditText passwordEditText;
    // todo add confirmPasswordEditText

    private Button registerButton;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/GeosansLight.ttf");

        initUi();
    }

    private void initUi() {
        usernameEditText = (EditText) findViewById(R.id.username);
        // todo init other edit texts

        usernameEditText.setTypeface(typeface);
        // todo set font for other edit texts

        registerButton = (Button) findViewById(R.id.register_button);
        // todo set on click listener for button
    }

    private void doRegister() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        // todo get text of confirm password edit text
        String confirmPassword = "";

        // todo add log of username and password
        // todo display toast with username and password

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Invalid input fields", Toast.LENGTH_LONG).show();
        } else if (!password.equalsIgnoreCase(confirmPassword)) {
            Toast.makeText(this, "Mismatching passwords", Toast.LENGTH_LONG).show();
        } else {
            // todo get sha256 hash of the password

            // todo create user with hashed password

            // todo save users(username and hashed password) in shared preference via PreferenceUtil, or write your own util

            // todo [wait till learning key generation and encryption] initialize rsa key pair

            // todo navigate to home
        }
    }

    public void navigateHome() {
        Intent intent = new Intent(this, ContactListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }
}

