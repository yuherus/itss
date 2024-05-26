package model;

public class Style {

        private int styleId;
        private String name;
        private String description;

        public Style(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public Style() {
        }

        public int getStyleId() {
            return styleId;
        }

        public void setStyleId(int styleId) {
            this.styleId = styleId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
}
