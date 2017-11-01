package com.score.owl.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.score.owl.R;
import com.score.owl.db.ContactDbSource;
import com.score.owl.pojo.Contact;

import java.util.ArrayList;

/**
 * Activity which responsible to display Contact list
 *
 * @author eranga herath(erangaeb@gmail.com)
 */
public class ContactListActivity extends AppCompatActivity {

    private ListView expenseListView;
    private ContactListAdapter contactListAdapter;
    private ArrayList<Contact> expenseList;
    private FloatingActionButton newButton;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list_layout);
        typeface = Typeface.createFromAsset(getAssets(), "fonts/GeosansLight.ttf");

        initActionBar();
        initUi();
        initList();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    private void initActionBar() {
        // set up action bar
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.action_bar_layout, null);

        TextView textView = (TextView) view.findViewById(R.id.title_text);
        textView.setText("Contacts");
        textView.setTypeface(typeface, Typeface.BOLD);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(view, params);
    }

    private void initUi() {
        newButton = (FloatingActionButton) findViewById(R.id.new_expense);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactListActivity.this, NewContactActivity.class);
                ContactListActivity.this.startActivity(intent);
            }
        });
    }

    private void initList() {
        expenseListView = (ListView) findViewById(R.id.list);
        expenseListView.setTextFilterEnabled(false);

        // fill sample data to contact list
        expenseList = new ArrayList<>();

        // [wait till learning db] load contacts list from db
        ContactDbSource contactDbSource = new ContactDbSource(this);
        expenseList.addAll(contactDbSource.getContacts());

        contactListAdapter = new ContactListAdapter(this, expenseList);
        expenseListView.setAdapter(contactListAdapter);

        expenseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // todo[wait till learning db and encryption] go to view contact activity
                Log.e("Contact : ", "user clicked on item");
                Intent intent = new Intent(ContactListActivity.this, ViewContactActivity.class);
                Contact contact = expenseList.get(position);

                ContactListActivity.this.startActivity(intent);
            }
        });
    }

    private void refreshList() {
        expenseList.clear();
        expenseList.addAll(new ContactDbSource(this).getContacts());
        // todo[wait till doing new contact] notify adapter about new data
    }

}
