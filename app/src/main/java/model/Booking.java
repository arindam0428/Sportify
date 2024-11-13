package model;

public class Booking {

    private String name;
    private String email;
    private String phone;
    private String eventName;
    private String eventDate;
    private String eventTime;
    private String eventLocation;
    private String imageUrl;
    private String key;

    public Booking() {}

    public Booking(String name, String email, String phone, String eventName, String eventDate, String eventTime, String eventLocation, String key) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventLocation = eventLocation;
        this.key= key;
    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getEventDate() { return eventDate; }
    public void setEventDate(String eventDate) { this.eventDate = eventDate; }

    public String getEventTime() { return eventTime; }
    public void setEventTime(String eventTime) { this.eventTime = eventTime; }

    public String getEventLocation() { return eventLocation; }
    public void setEventLocation(String eventLocation) { this.eventLocation = eventLocation; }
}
