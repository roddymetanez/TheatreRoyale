package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Performance {

    private int ID;
    private int duration;
    private Double price;
    private LocalDateTime startDateTime;


    public Performance(String date, int duration) {
        this.setDuration(duration);
        this.startDateTime = LocalDateTime.parse(date);
        // Instantiate global variables
    }

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        ID = iD;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(startDateTime);
        this.startDateTime = startDateTime;
    }
}
