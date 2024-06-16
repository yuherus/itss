package model;

public class Notification {

        private int notificationId;
        private String message;
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
}
