package model;

import javafx.util.Pair;

import java.util.List;

public class SampleTour {
    private int sampleTourId;
    private String tourName;
    private String description;
    private double totalCost;
    private List<Pair<Location,Integer>> locations;

    public SampleTour(String tourName, String description, double totalCost, List<Pair<Location,Integer>> locations) {
        this.tourName = tourName;
        this.description = description;
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

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public List<Pair<Location,Integer>> getLocations() {
        return locations;
    }

    public void setLocations(List<Pair<Location,Integer>> locations) {
        this.locations = locations;
    }
}

//import javafx.beans.property.SimpleStringProperty;
//
//public class SampleTour {
//    private final SimpleStringProperty sampletourId;
//    private final SimpleStringProperty tourName;
//    private final SimpleStringProperty description;
//    private final SimpleStringProperty totalCost;
//
//    public SampleTour(String sampletourId, String tourName, String description, String totalCost) {
//        this.sampletourId = new SimpleStringProperty(sampletourId);
//        this.tourName = new SimpleStringProperty(tourName);
//        this.description = new SimpleStringProperty(description);
//        this.totalCost = new SimpleStringProperty(totalCost);
//    }
//
//    public String getSampletourId() {
//        return sampletourId.get();
//    }
//
//    public String getTourName() {
//        return tourName.get();
//    }
//
//    public String getDescription() {
//        return description.get();
//    }
//
//    public String getTotalCost() {
//        return totalCost.get();
//    }
//}
