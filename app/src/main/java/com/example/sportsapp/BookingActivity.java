package com.example.sportsapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BookingActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextPhone;
    private DatabaseReference bookingsDatabase;
    private String eventName, eventDate, eventTime, eventLocation;
    private String eventImageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        Button buttonBookNow = findViewById(R.id.buttonBookNow);
        bookingsDatabase = FirebaseDatabase.getInstance().getReference("bookings");
        eventName = getIntent().getStringExtra("eventName");
        eventDate = getIntent().getStringExtra("eventDate");
        eventTime = getIntent().getStringExtra("eventTime");
        eventLocation = getIntent().getStringExtra("eventLocation");

        buttonBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookEvent();
            }
        });
    }

    private void bookEvent() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        String bookingId = bookingsDatabase.push().getKey();
        HashMap<String, String> bookingData = new HashMap<>();
        bookingData.put("name", name);
        bookingData.put("email", email);
        bookingData.put("phone", phone);
        bookingData.put("eventName", eventName);
        bookingData.put("eventDate", eventDate);
        bookingData.put("eventTime", eventTime);
        bookingData.put("eventLocation", eventLocation);
        bookingData.put("imageUrl", eventImageUrl);
        if (bookingId != null) {
            bookingsDatabase.child(bookingId).setValue(bookingData)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(BookingActivity.this, "Booking successful", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(BookingActivity.this, "Booking failed, try again", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
