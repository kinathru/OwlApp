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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getName();

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/GeosansLight.ttf");

        initUi();
    }

    private void initUi() {
        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);

        usernameEditText.setTypeface(typeface);
        passwordEditText.setTypeface(typeface);

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    }

    private void doLogin() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Invalid input fields", Toast.LENGTH_LONG).show();
        } else {
            // get registered user
            User user = PreferenceUtil.getUser(this);

            // get SHA256 hash of password
            String hasedPwd = null;
            try {
                hasedPwd = CryptoUtil.hashSHA256(password);
            } catch (NoSuchAlgorithmException e) {
                Log.e("Login : ", e.getMessage());
            }

            // log registered user's password hash and new password hash
            String savedPwd = user.getPassword();

            // compare username and password with registered users username and password
            if (hasedPwd != null && savedPwd != null && savedPwd.equals(hasedPwd))
            {
                // for valid login navigate to home
                navigateHome();
            }
            else
            {
                Log.e("Login : ", "Wrong user name or password ");
                Toast.makeText(this, "Wrong username or password", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void navigateHome() {
        Intent intent = new Intent(this, ContactListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        this.finish();
    }
}

