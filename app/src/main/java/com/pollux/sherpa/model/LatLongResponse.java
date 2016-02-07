package com.pollux.sherpa.model;

import android.location.Location;

/**
 * Created by idea on 07-02-2016.
 */
public class LatLongResponse {

    private Results[] results;

    public Results[] getResults() {
        return results;
    }

    public void setResults(Results[] results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ClassPojo [results = " + results + "]";
    }

    public class Results{
        private Geometry geometry;

        public Geometry getGeometry ()
        {
            return geometry;
        }

        public void setGeometry (Geometry geometry)
        {
            this.geometry = geometry;
        }
    }

    public class Geometry{
        private Location location;

        public Location getLocation ()
        {
            return location;
        }

        public void setLocation (Location location)
        {
            this.location = location;
        }

    }

    public class Location
    {
        private String lng;

        private String lat;

        public String getLng ()
        {
            return lng;
        }

        public void setLng (String lng)
        {
            this.lng = lng;
        }

        public String getLat ()
        {
            return lat;
        }

        public void setLat (String lat)
        {
            this.lat = lat;
        }

        @Override
        public String toString() {
            return "ClassPojo [lng = " + lng + ", lat = " + lat + "]";
        }
    }
}
