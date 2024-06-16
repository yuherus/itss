package model;

import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.List;

public class Tracking {
    private int pointId;
    private int tourId;
    private Location location;
    private LocalDateTime visitTime;

    public Tracking(int pointId, int tourId, Location location, LocalDateTime visitTime) {
        this.pointId = pointId;
        this.tourId = tourId;
        this.location = location;
        this.visitTime = visitTime;
    }

    public Tracking() {
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(LocalDateTime visitTime) {
        this.visitTime = visitTime;
    }
}
