package com.example.lookbook1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private Context context;
    private int resource;
    private ArrayList<Contact> contacts;

    public ContactAdapter(Context context, int resource, ArrayList<Contact> contacts) {
        super(context, resource, contacts);
        this.context = context;
        this.resource = resource;
        this.contacts = contacts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView birthdayTextView = convertView.findViewById(R.id.birthdayTextView);
        TextView emailTextView = convertView.findViewById(R.id.emailTextView);

        Contact contact = contacts.get(position);

        nameTextView.setText(contact.getName());
        birthdayTextView.setText(contact.getBirthday());
        emailTextView.setText(contact.getEmail());

        return convertView;
    }
}

