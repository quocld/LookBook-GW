package com.example.lookbook1;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddContactActivity extends AppCompatActivity {
    EditText nameEditText, birthdayEditText, emailEditText;
    Button saveButton;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        nameEditText = findViewById(R.id.editTextName);
        birthdayEditText = findViewById(R.id.editTextBirthday);
        emailEditText = findViewById(R.id.editTextEmail);
        saveButton = findViewById(R.id.btnSave);
        databaseHelper = new DatabaseHelper(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lưu thông tin contact vào cơ sở dữ liệu
                String name = nameEditText.getText().toString();
                String birthday = birthdayEditText.getText().toString();
                String email = emailEditText.getText().toString();

                if (name.isEmpty() || birthday.isEmpty() || email.isEmpty()) {
                    Toast.makeText(AddContactActivity.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(DatabaseHelper.COLUMN_NAME, name);
                    values.put(DatabaseHelper.COLUMN_BIRTHDAY, birthday);
                    values.put(DatabaseHelper.COLUMN_EMAIL, email);

                    long newRowId = db.insert(DatabaseHelper.TABLE_CONTACTS, null, values);

                    if (newRowId != -1) {
                        Toast.makeText(AddContactActivity.this, "Contact đã được lưu", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddContactActivity.this, "Lỗi khi lưu contact", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
