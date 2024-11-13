package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.sportsapp.AboutUs;
import com.example.sportsapp.BookingsAdapter;
import com.example.sportsapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import model.Booking;

public class HomeFragment extends Fragment {

    private ListView bookingsListView;
    private ArrayList<Booking> bookingsList;
    private BookingsAdapter bookingsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        CardView cardEvent2 = rootView.findViewById(R.id.card_event_2);
        cardEvent2.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutUs.class);
            startActivity(intent);
        });

        bookingsListView = rootView.findViewById(R.id.bookings_list);
        bookingsList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference bookingsDatabaseRef = database.getReference("bookings");

        bookingsAdapter = new BookingsAdapter(getActivity(), bookingsList, bookingsDatabaseRef);
        bookingsListView.setAdapter(bookingsAdapter);
        bookingsDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookingsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Booking booking = snapshot.getValue(Booking.class);
                    if (booking != null) {
                        booking.setKey(snapshot.getKey());
                        bookingsList.add(booking);
                    }
                }
                bookingsAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Failed to load bookings.", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
