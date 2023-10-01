package com.example.lookbook1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewContactsActivity extends AppCompatActivity {
    ListView contactsListView;
    DatabaseHelper databaseHelper;

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);

        contactsListView = findViewById(R.id.listViewContacts);
        databaseHelper = new DatabaseHelper(this);
        backButton = findViewById(R.id.btnBack);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Quay lại màn hình chính (MainActivity)
                Intent intent = new Intent(ViewContactsActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Đóng màn hình ViewContactsActivity
            }
        });

        ArrayList<Contact> contacts = getContacts();
        ContactAdapter adapter = new ContactAdapter(this, R.layout.contact_item, contacts);

        contactsListView.setAdapter(adapter);
    }

    private ArrayList<Contact> getContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String[] projection = {
                DatabaseHelper.COLUMN_ID,
                DatabaseHelper.COLUMN_NAME,
                DatabaseHelper.COLUMN_BIRTHDAY,
                DatabaseHelper.COLUMN_EMAIL
        };

        Cursor cursor = db.query(
                DatabaseHelper.TABLE_CONTACTS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
            String birthday = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BIRTHDAY));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL));

            Contact contact = new Contact(id, name, birthday, email);
            contacts.add(contact);
        }

        cursor.close();
        return contacts;
    }
}
