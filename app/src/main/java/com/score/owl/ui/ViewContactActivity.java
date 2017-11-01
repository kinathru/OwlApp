package com.score.owl.ui;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.score.owl.R;
import com.score.owl.db.ContactDbSource;
import com.score.owl.pojo.Contact;
import com.score.owl.util.CryptoUtil;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ViewContactActivity extends AppCompatActivity {

    private static final String TAG = ViewContactActivity.class.getName();

    private EditText nameEditText;
    private EditText phoneEditText;
    private Button verifyButton;
    private Typeface typeface;

    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_contact_layout);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/GeosansLight.ttf");

        initActionBar();
        initUi();
        initContact();
    }

    private void initActionBar() {
        // set up action bar
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.action_bar_layout, null);

        TextView textView = (TextView) view.findViewById(R.id.title_text);
        textView.setText("View contact");
        textView.setTypeface(typeface, Typeface.BOLD);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(view, params);
    }

    private void initUi() {
        nameEditText = (EditText) findViewById(R.id.name);
        phoneEditText = (EditText) findViewById(R.id.phone);
        nameEditText.setTypeface(typeface);
        phoneEditText.setTypeface(typeface);

        verifyButton = (Button) findViewById(R.id.verify_button);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyContact();
            }
        });
    }

    private void initContact() {
        if (getIntent().getExtras() != null && getIntent().hasExtra("USERNAME")) {
            String username = getIntent().getStringExtra("USERNAME");
            nameEditText.setText(contact.getName());

            // get contact with given username from db
            ContactDbSource contactDbSource = new ContactDbSource(this);
            Contact contact = contactDbSource.getContact(username);

            // decrypt phone and show in phone no
            String decryptedPhone = null;
            try {
                decryptedPhone = CryptoUtil.decryptRSA(this, contact.getPhone());
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
            phoneEditText.setText(decryptedPhone);
        }
    }

    private void verifyContact() {
        // todo init aes key

        // todo encrypt phone via aes and log output

        // todo decrypt phone via aes and log output

        // todo digitally sign phone no and log output

        // todo verify digital signature and log output(true, false) and show toast
    }

}
