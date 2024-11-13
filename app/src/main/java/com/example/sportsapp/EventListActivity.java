package com.example.sportsapp;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import model.Event;

public class EventListActivity extends AppCompatActivity {

    private ListView eventsListView;
    private ArrayList<Event> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        eventsListView = findViewById(R.id.events_list);
        eventList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date date1 = sdf.parse("2024-11-15");
            Date date2 = sdf.parse("2024-11-16");
            Date date3 = sdf.parse("2024-11-18");
            Date date4 = sdf.parse("2024-11-19");
            Date date5 = sdf.parse("2024-11-20");
            Date date6 = sdf.parse("2024-11-21");

            eventList.add(new Event("Football Match", "Stadium A", date1, "10:00 AM", R.drawable.football, 40.748817, -73.985428));
            eventList.add(new Event("Basketball Tournament", "Gym B", date2, "2:00 PM", R.drawable.basketball, 40.730610, -73.935242));
            eventList.add(new Event("Tennis Match", "Court C", date3, "11:00 AM", R.drawable.tennis, 34.052235, -118.243683));
            eventList.add(new Event("Baseball Game", "Stadium D", date4, "1:00 PM", R.drawable.basketball, 37.774929, -122.419418));
            eventList.add(new Event("Volleyball Tournament", "Beach E", date5, "4:00 PM", R.drawable.volleyball, 36.778259, -119.417931));
            eventList.add(new Event("Rugby Match", "Field F", date6, "5:00 PM", R.drawable.rugby, 51.507351, -0.127758));

        } catch (Exception e) {
            e.printStackTrace();
        }

        EventAdapter adapter = new EventAdapter(this, eventList);
        eventsListView.setAdapter(adapter);
    }
}
