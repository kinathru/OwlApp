package com.score.owl.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.score.owl.R;
import com.score.owl.util.PreferenceUtil;

/**
 * Splash activity, send login query from here
 *
 * @author eranga herath(erangaeb@gmail.com)
 */
public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private Button submitButton;
    private TextView splashText;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_layout);
        initNavigation();

        submitButton = (Button) findViewById(R.id.splash_button);
        splashText = (TextView) findViewById(R.id.splash_name);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                splashText.setText("Button Clicked!!!");
            }
        });

    }

    private void initNavigation() {
        // todo determine where to go
        // if have registered user go to login, otherwise goto registration
    }

    private void navigateSplash() {
        // todo wait in splash screen for 3 seconds and navigate to registration
    }

    private void navigateRegistration() {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        SplashActivity.this.finish();
    }

    public void navigateLogin() {
        // todo go to login
    }

    public void navigateHome() {
        // todo go to home/contact list
    }



}