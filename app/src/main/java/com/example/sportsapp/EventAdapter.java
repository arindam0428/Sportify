package com.example.sportsapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import model.Event;

public class EventAdapter extends ArrayAdapter<Event> {

    private final Activity context;
    private final List<Event> eventList;

    public EventAdapter(Activity context, List<Event> eventList) {
        super(context, R.layout.event_list_item, eventList);
        this.context = context;
        this.eventList = eventList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.event_list_item, null, true);

        TextView eventNameTextView = listViewItem.findViewById(R.id.event_name);
        TextView eventLocationTextView = listViewItem.findViewById(R.id.event_location);
        TextView eventDateTextView = listViewItem.findViewById(R.id.event_date);
        TextView eventTimeTextView = listViewItem.findViewById(R.id.event_time);
        ImageView eventImageView = listViewItem.findViewById(R.id.event_image);
        Button bookNowButton = listViewItem.findViewById(R.id.buttonBookNow);

        Event currentEvent = eventList.get(position);

        eventNameTextView.setText(currentEvent.getName());
        eventLocationTextView.setText(currentEvent.getLocation());
        eventDateTextView.setText(currentEvent.getFormattedDate());
        eventTimeTextView.setText(currentEvent.getFormattedTime());
        eventImageView.setImageResource(currentEvent.getImageResId());

        bookNowButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookingActivity.class);
            intent.putExtra("eventName", currentEvent.getName());
            intent.putExtra("eventLocation", currentEvent.getLocation());
            intent.putExtra("eventDate", currentEvent.getFormattedDate());
            intent.putExtra("eventTime", currentEvent.getFormattedTime());
            context.startActivity(intent);
        });
        eventImageView.setOnClickListener(v -> {
            double latitude = currentEvent.getLatitude();
            double longitude = currentEvent.getLongitude();
            Uri locationUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(mapIntent);
            } else {
            }
        });

        return listViewItem;
    }
}
