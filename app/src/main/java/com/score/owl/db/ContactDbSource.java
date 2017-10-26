package com.score.owl.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.score.owl.pojo.Contact;

import java.util.ArrayList;

/**
 * We do
 * 1. Object create
 * 4. Get objects
 * from here
 */
public class ContactDbSource {

    private static Context context;

    private static final String TAG = ContactDbSource.class.getName();

    /**
     * Init db helper
     *
     * @param context application context
     */
    public ContactDbSource(Context context) {
        Log.d(TAG, "Init: db source");
        this.context = context;
    }

    /**
     * Find all contacts,
     * <p>
     * executing query
     * SELECT * FROM contacts;
     *
     * @return contacts list
     */
    public ArrayList<Contact> getContacts() {
        SQLiteDatabase db = ContactDbHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = db.query(ContactDbContract.Contact.TABLE_NAME, // table
                null, // columns
                null, // filter
                null, // selection
                null, // order by
                null, // group by
                null); // join

        ArrayList<Contact> contactsList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndex(ContactDbContract.Contact.COLUMN_NAME_USERNAME));
            String phone = cursor.getString(cursor.getColumnIndex(ContactDbContract.Contact.COLUMN_NAME_PHONE));
            // todo[wait till learn digital signature] get digsig

            contactsList.add(new Contact(username, phone));
        }

        return contactsList;
    }

    /**
     * Get contact with given username,
     * <p>
     * executing query
     * SELECT * FROM contacts WHERE username = '<given username>';
     *
     * @return contact list
     */
    public Contact getContact(String username) {
        SQLiteDatabase db = ContactDbHelper.getInstance(context).getReadableDatabase();
        Cursor cursor = db.query(ContactDbContract.Contact.TABLE_NAME, // table
                null, // columns
                ContactDbContract.Contact.COLUMN_NAME_USERNAME + " = ?", // constraint
                new String[]{username}, // prams
                null, // order by
                null, // group by
                null); // join

        if (cursor.moveToFirst()) {
            String phone = cursor.getString(cursor.getColumnIndex(ContactDbContract.Contact.COLUMN_NAME_PHONE));
            // todo[wait till learn digital signature] get digsig

            return new Contact(username, phone);
        }

        return null;
    }

    /**
     * Add Contact to the database
     */
    public long createContact(Contact contact) throws SQLException {
        Log.d(TAG, "Create expense - " + contact.getName() + ": " + contact.getPhone());

        SQLiteDatabase db = ContactDbHelper.getInstance(context).getWritableDatabase();

        // content values to inset
        ContentValues values = new ContentValues();
        values.put(ContactDbContract.Contact.COLUMN_NAME_USERNAME, contact.getName());
        values.put(ContactDbContract.Contact.COLUMN_NAME_PHONE, contact.getPhone());
        // todo put digsig

        // insert the new row, if fails throw an error
        return db.insertOrThrow(ContactDbContract.Contact.TABLE_NAME, null, values);
    }
}
