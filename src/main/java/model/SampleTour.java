package model;

import javafx.util.Pair;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class SampleTour {
    private int sampleTourId;
    private String tourName;
    private String description;
    private Date startDate;
    private Date endDate;
    private double totalCost;
    private List<Pair<Location, Timestamp>> locations;

    public SampleTour(int sampleTourId, String tourName, String description, Date startDate, Date endDate, double totalCost, List<Pair<Location,Timestamp>> locations) {
        this.sampleTourId = sampleTourId;
        this.tourName = tourName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.locations = locations;
    }

    public SampleTour() {
    }

    public int getSampleTourId() {
        return sampleTourId;
    }

    public void setSampleTourId(int sampleTourId) {
        this.sampleTourId = sampleTourId;
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

    public List<Pair<Location,Timestamp>> getLocations() {
        return locations;
    }

    public void setLocations(List<Pair<Location,Timestamp>> locations) {
        this.locations = locations;
    }

    @Override
    public String toString() {
        return "SampleTour{" +
                "sampleTourId=" + sampleTourId +
                ", tourName='" + tourName + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalCost=" + totalCost +
                ", locations=" + locations +
                '}';
    }
}
