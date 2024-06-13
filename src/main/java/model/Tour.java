package model;

import javafx.util.Pair;

import java.util.Date;
import java.util.List;

public class Tour {
    public enum Status {
        PENDING,
        CONFIRMED,
        COMPLETED,
        CANCELLED,
    };

    private int tourId;
    private int tourGuideId;
    private String tourName;
    private Status status;
    private Date startDate;
    private Date endDate;
    private double totalCost;
    private List<Pair<Location,Integer>> locations;

    public Tour() {
    }

    public Tour(int tourGuideId, String tourName, Status status, Date startDate, Date endDate, double totalCost, List<Pair<Location,Integer>> locations) {
        this.tourGuideId = tourGuideId;
        this.tourName = tourName;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.locations = locations;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getTourGuideId() {
        return tourGuideId;
    }

    public void setTourGuideId(int tourGuideId) {
        this.tourGuideId = tourGuideId;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public List<Pair<Location, Integer>> getLocations() {
        return locations;
    }

    public void setLocations(List<Pair<Location, Integer>> locations) {
        this.locations = locations;
    }
}
