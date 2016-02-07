package com.pollux.sherpa.model;

import android.location.Location;

/**
 * Created by idea on 07-02-2016.
 */
public class PlaceDataResponse {

    private Results[] results;

    public Results[] getResults() {
        return results;
    }

    public void setResults(Results[] results) {
        this.results = results;
    }

    public class Results {
        private Photos[] photos;

        private String name;

        private String formatted_address;

        private String rating;

        private String[] types;

        private Geometry geometry;

        public Photos[] getPhotos() {
            return photos;
        }

        public void setPhotos(Photos[] photos) {
            this.photos = photos;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String[] getTypes() {
            return types;
        }

        public void setTypes(String[] types) {
            this.types = types;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

    }

    public class Geometry {
        private Location location;

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

    }

    public class Location {
        private String lng;

        private String lat;

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

    }

    public class Photos {
        private String[] html_attributions;

        public String[] getHtml_attributions() {
            return html_attributions;
        }

        public void setHtml_attributions(String[] html_attributions) {
            this.html_attributions = html_attributions;
        }

    }

}
