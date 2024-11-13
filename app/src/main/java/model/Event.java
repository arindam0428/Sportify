package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
    private String name;
    private String location;
    private Date date;
    private String time;
    private int imageResId;
    private double latitude;
    private double longitude;

    public Event(String name, String location, Date date, String time, int imageResId, double latitude, double longitude) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
        this.imageResId = imageResId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getImageResId() {
        return imageResId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public String getFormattedTime() {
        return time;
    }
}
