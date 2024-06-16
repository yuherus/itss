package model;

public class Notification {

        public enum TourType {
            QUICKBOOK,
            OPTIONALBOOK,
        }

        private int notificationId;

        private Tour tour;
        private String message;

        private TourType tourType;
        private boolean status;
        private User user;

        public Notification(String message, boolean status, User user) {
            this.message = message;
            this.status = status;
            this.user = user;
        }

        public Notification() {
        }

        public int getNotificationId() {
            return notificationId;
        }

        public void setNotificationId(int notificationId) {
            this.notificationId = notificationId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean getStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Tour getTour() {
            return tour;
        }

        public void setTour(Tour tour) {
            this.tour = tour;
        }

        public TourType getTourType() {
            return tourType;
        }

        public void setTourType(TourType tourType) {
            this.tourType = tourType;
        }


}
