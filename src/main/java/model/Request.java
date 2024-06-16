package model;

import java.util.List;

public class Request {
    private int requestId;
    private int touristId;
    private int styleId;

    private List<Location> locations;

    public Request() {
    }

    public Request(int requestId, int touristId, int styleId, List<Location> locations) {
        this.requestId = requestId;
        this.touristId = touristId;
        this.styleId = styleId;
        this.locations = locations;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public int getTouristId() {
        return touristId;
    }

    public void setTouristId(int touristId) {
        this.touristId = touristId;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId=" + requestId +
                ", touristId=" + touristId +
                ", styleId=" + styleId +
                ", locations=" + locations +
                '}';
    }
}
