package com.example.atry;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {

    private final Context context;
    private final List<Contact> contacts;

    public ContactAdapter(Context context, List<Contact> contacts) {
        super(context, R.layout.item_contact, contacts);
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_contact, parent, false);
        }

        Contact contact = contacts.get(position);

        TextView textViewName = convertView.findViewById(R.id.textViewName);
        TextView textViewNumber = convertView.findViewById(R.id.textViewNumber);

        textViewName.setText(contact.getName());
        textViewNumber.setText(contact.getPhoneNumber());

        return convertView;
    }
}
