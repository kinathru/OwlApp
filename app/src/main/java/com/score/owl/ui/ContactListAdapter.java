package com.score.owl.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.score.owl.R;
import com.score.owl.pojo.Contact;

import java.util.ArrayList;

/**
 * Display expense list
 *
 * @author eranga herath(erangaeb@gmail.com)
 */
public class ContactListAdapter extends BaseAdapter {

    private ContactListActivity activity;
    private ArrayList<Contact> expenseList;

    private Typeface typeface;

    /**
     * Initialize context variables
     *
     * @param activity friend list activity
     */
    public ContactListAdapter(ContactListActivity activity, ArrayList<Contact> expenseList) {
        this.activity = activity;
        this.expenseList = expenseList;
        typeface = Typeface.createFromAsset(activity.getAssets(), "fonts/GeosansLight.ttf");
    }

    /**
     * Get size of expense list
     *
     * @return expenseList size
     */
    @Override
    public int getCount() {
        return expenseList.size();
    }

    /**
     * Get specific item from expense list
     *
     * @param i item index
     * @return list item
     */
    @Override
    public Object getItem(int i) {
        return expenseList.get(i);
    }

    /**
     * Get expense list item id
     *
     * @param i item index
     * @return current item id
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Create list row view
     *
     * @param position index
     * @param view     current list item view
     * @param parent   parent
     * @return view
     */
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unnecessary calls
        // to findViewById() on each row.
        final ViewHolder holder;
        final Contact expense = (Contact) getItem(position);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.contact_list_row_layout, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.contact_list_row_layout_name);
            // todo init phone

            holder.name.setTypeface(typeface, Typeface.NORMAL);
            // todo set custom font for phone

            view.setTag(holder);
        } else {
            // get view holder back
            holder = (ViewHolder) view.getTag();
        }

        // bind text with view holder content view for efficient use
        holder.name.setText(expense.getName());
        // todo set text for phone

        return view;
    }


    /**
     * Keep reference to children view to avoid unnecessary calls
     */
    private static class ViewHolder {
        TextView name;
        TextView phone;
    }

}
