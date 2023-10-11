package com.example.lookbook1;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.time.LocalDate;

public class AddContactActivity extends AppCompatActivity {
    EditText nameEditText, birthdayEditText, emailEditText;
    Button saveButton;
    DatabaseHelper databaseHelper;

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
        {
            LocalDate d = LocalDate.now();
            int year = d.getYear();
            int month = d.getMonthValue();
            int day = d.getDayOfMonth();
            return new DatePickerDialog(getActivity(), this, year, --month, day);}
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day){
            LocalDate dob = LocalDate.of(year, ++month, day);
            ((AddContactActivity)getActivity()).updateDOB(dob);
        }
    }

    public  void updateDOB(LocalDate dob){
        TextView dobControl = findViewById(R.id.editTextBirthday);
        dobControl.setText(dob.toString());
    }

    public int convertContactType(String contactType){
        String[] contactTypes = {"Contact Type 1", "Contact Type 2", "Contact Type 3"};
        for(int i=0;i<contactTypes.length; i++){
            if(contactType == contactTypes[i]){
                return i;
            }
        }
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        nameEditText = findViewById(R.id.editTextName);
        birthdayEditText = findViewById(R.id.editTextBirthday);
        emailEditText = findViewById(R.id.editTextEmail);
        saveButton = findViewById(R.id.btnSave);
        databaseHelper = new DatabaseHelper(this);
        Spinner spinnerContactType = findViewById(R.id.spinnerContactType);

        String[] contactTypes = {"Contact Type 1", "Contact Type 2", "Contact Type 3"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, contactTypes);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerContactType.setAdapter(adapter);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lưu thông tin contact vào cơ sở dữ liệu
                String name = nameEditText.getText().toString();
                String birthday = birthdayEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String selectedOption = (String) spinnerContactType.getSelectedItem();
                int contactType = convertContactType(selectedOption);

                if (name.isEmpty() || birthday.isEmpty() || email.isEmpty()) {
                    Toast.makeText(AddContactActivity.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    SQLiteDatabase db = databaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(DatabaseHelper.COLUMN_NAME, name);
                    values.put(DatabaseHelper.COLUMN_BIRTHDAY, birthday);
                    values.put(DatabaseHelper.COLUMN_CONTACT_TYPE, contactType);
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
        birthdayEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
    }
}
