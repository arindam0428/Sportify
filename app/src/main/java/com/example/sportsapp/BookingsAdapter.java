package com.example.sportsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import model.Booking;

public class BookingsAdapter extends ArrayAdapter<Booking> {

    private final Activity context;
    private final List<Booking> bookingsList;
    private final DatabaseReference bookingsDatabaseRef;

    public BookingsAdapter(Activity context, List<Booking> bookingsList, DatabaseReference bookingsDatabaseRef) {
        super(context, R.layout.booking_list_item, bookingsList);
        this.context = context;
        this.bookingsList = bookingsList;
        this.bookingsDatabaseRef = bookingsDatabaseRef;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.booking_list_item, null, true);

        TextView eventTitleTextView = listViewItem.findViewById(R.id.textViewEventName);
        TextView eventDateTextView = listViewItem.findViewById(R.id.textViewEventDate);
        TextView eventTimeTextView = listViewItem.findViewById(R.id.textViewEventTime);
        TextView eventLocationTextView = listViewItem.findViewById(R.id.textViewEventLocation);
        Button removeButton = listViewItem.findViewById(R.id.remove_button);

        Booking currentBooking = bookingsList.get(position);
        if (currentBooking == null) {
            return listViewItem;
        }

        eventTitleTextView.setText(currentBooking.getEventName());
        eventDateTextView.setText(currentBooking.getEventDate());
        eventTimeTextView.setText(currentBooking.getEventTime());
        eventLocationTextView.setText(currentBooking.getEventLocation());

        removeButton.setOnClickListener(v -> {
            String bookingKey = currentBooking.getKey();

            if (bookingKey == null || bookingKey.isEmpty()) {
                Toast.makeText(context, "Booking ID is missing", Toast.LENGTH_SHORT).show();
                return;
            }
            bookingsDatabaseRef.child(bookingKey).removeValue()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (position >= 0 && position < bookingsList.size()) {
                                bookingsList.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(context, "Booking removed", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Booking no longer exists", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Failed to remove booking", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Error removing booking: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });


        return listViewItem;
    }
}
