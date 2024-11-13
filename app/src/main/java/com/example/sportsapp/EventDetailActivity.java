package com.example.sportsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class EventDetailActivity extends AppCompatActivity {

    private TextView eventNameTextView, eventDescriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        eventNameTextView = findViewById(R.id.event_name);
        eventDescriptionTextView = findViewById(R.id.event_description);

        Intent intent = getIntent();
        String eventName = intent.getStringExtra("event_name");
        String eventDescription = intent.getStringExtra("event_description");
        eventNameTextView.setText(eventName);
        eventDescriptionTextView.setText(eventDescription);
    }
}
