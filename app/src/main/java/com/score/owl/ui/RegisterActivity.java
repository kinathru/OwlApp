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
    private EditText confirmPasswordEditText;

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
        passwordEditText = (EditText) findViewById(R.id.password);
        confirmPasswordEditText = (EditText) findViewById(R.id.confirm_password);

        usernameEditText.setTypeface(typeface);
        passwordEditText.setTypeface(typeface);
        confirmPasswordEditText.setTypeface(typeface);

        registerButton = (Button) findViewById(R.id.register_button);
        registerButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        doRegister();
                    }
                }
        );
    }

    private void doRegister() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        Log.i("Password", "User name is " + username + " and password is : " + password);
        Toast.makeText(this, ("U " + username + " : P " + password), Toast.LENGTH_LONG).show();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Invalid input fields", Toast.LENGTH_LONG).show();
        } else if (!password.equalsIgnoreCase(confirmPassword)) {
            Toast.makeText(this, "Mismatching passwords", Toast.LENGTH_LONG).show();
        } else {
            String hashedPassword = null;
            try {
                hashedPassword = CryptoUtil.hashSHA256(password);
            } catch (NoSuchAlgorithmException e) {
                Log.e("Register : ", e.getMessage());
            }

            // todo create user with hashed password
            User user = new User(username, hashedPassword);
            // todo save users(username and hashed password) in shared preference via PreferenceUtil, or write your own util
            PreferenceUtil.saveUser(this, user);
            // todo [wait till learning key generation and encryption] initialize rsa key pair
            try {
                CryptoUtil.initRSAKeyPair(this);
            } catch (NoSuchProviderException e) {
                Log.e("Register : ", e.getMessage());
            } catch (NoSuchAlgorithmException e) {
                Log.e("Register : ", e.getMessage());
            }

            // todo navigate to home
            navigateHome();
        }
    }

    public void navigateHome() {
        Intent intent = new Intent(this, ContactListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }
}

