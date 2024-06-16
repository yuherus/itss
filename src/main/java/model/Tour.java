package model;

import javafx.util.Pair;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class Tour {
    public enum Status {
        PENDING,
        CONFIRMED,
        COMPLETED,
        CANCELLED,
        CREATED,
    };

    private int tourId;
    private int tourGuideId;

    private int touristId;
    private String tourName;
    private String description;
    private Status status;
    private Date startDate;
    private Date endDate;
    private double totalCost;
    private List<Pair<Location, Timestamp>> locations;

    public Tour() {
    }

    public Tour(int touristId,int tourGuideId, String tourName, String description, Status status, Date startDate, Date endDate, double totalCost, List<Pair<Location,Timestamp>> locations) {
        this.tourGuideId = tourGuideId;
        this.touristId = touristId;
        this.tourName = tourName;
        this.description = description;
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

    public int getTouristId() {
        return touristId;
    }

    public void setTouristId(int touristId) {
        this.touristId = touristId;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<Pair<Location, Timestamp>> getLocations() {
        return locations;
    }

    public void setLocations(List<Pair<Location, Timestamp>> locations) {
        this.locations = locations;
    }

    @Override
    public String toString() {
        return "Tour{" +
                "tourId=" + tourId +
                ", tourGuideId=" + tourGuideId +
                ", touristId=" + touristId +
                ", tourName='" + tourName + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalCost=" + totalCost +
                ", locations=" + locations +
                '}';
    }
}
