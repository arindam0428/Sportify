package com.example.sportsapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import model.Event;

public class EventsFragment extends Fragment {

    private ListView eventsListView;
    private ArrayList<Event> eventList;
    private Button sortButton;
    private TextView noEventsText;

    public EventsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);

        eventsListView = rootView.findViewById(R.id.events_list);
        sortButton = rootView.findViewById(R.id.sort_button);
        noEventsText = rootView.findViewById(R.id.no_events_text);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        eventList = new ArrayList<>();
        try {
            eventList.add(new Event("Football Match", "Stadium A", sdf.parse("2024-11-15"), "10:00 AM", R.drawable.football, 40.748817, -73.985428));
            eventList.add(new Event("Basketball Tournament", "Gym B", sdf.parse("2024-11-16"), "2:00 PM", R.drawable.basketball, 40.730610, -73.935242));
            eventList.add(new Event("Tennis Match", "Court C", sdf.parse("2024-11-18"), "11:00 AM", R.drawable.tennis, 34.052235, -118.243683));
            eventList.add(new Event("Baseball Game", "Stadium D", sdf.parse("2024-11-19"), "1:00 PM", R.drawable.baseball, 37.774929, -122.419418));
            eventList.add(new Event("Volleyball Tournament", "Beach E", sdf.parse("2024-11-20"), "4:00 PM", R.drawable.volleyball, 36.778259, -119.417931));
            eventList.add(new Event("Rugby Match", "Field F", sdf.parse("2024-11-21"), "5:00 PM", R.drawable.rugby, 51.507351, -0.127758));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        EventAdapter adapter = new EventAdapter(getActivity(), eventList);
        eventsListView.setAdapter(adapter);
        eventsListView.setOnItemClickListener((parent, view, position, id) -> {
            Event selectedEvent = (Event) adapter.getItem(position);

            double latitude = selectedEvent.getLatitude();
            double longitude = selectedEvent.getLongitude();
            Uri locationUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });
        sortButton.setOnClickListener(v -> showDatePickerDialog());

        return rootView;
    }
    private void showDatePickerDialog() {
        final GregorianCalendar calendar = new GregorianCalendar();

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    calendar.set(year, monthOfYear, dayOfMonth);
                    Date selectedDate = calendar.getTime();
                    filterAndSortEventsByDate(selectedDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
    private void filterAndSortEventsByDate(Date selectedDate) {
        List<Event> filteredEvents = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Event event : eventList) {
            String eventDateString = sdf.format(event.getDate());
            String selectedDateString = sdf.format(selectedDate);

            if (eventDateString.equals(selectedDateString)) {
                filteredEvents.add(event);
            }
        }
        if (filteredEvents.isEmpty()) {
            noEventsText.setVisibility(View.VISIBLE);
            eventsListView.setVisibility(View.GONE);
            Log.d("EventsFragment", "No events found for the selected date.");
        } else {
            noEventsText.setVisibility(View.GONE);
            eventsListView.setVisibility(View.VISIBLE);
            Collections.sort(filteredEvents, (event1, event2) -> event1.getDate().compareTo(event2.getDate()));

            EventAdapter adapter = new EventAdapter(getActivity(), filteredEvents);
            eventsListView.setAdapter(adapter);
        }
    }
}

