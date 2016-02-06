package com.pollux.sherpa.model;

import java.util.List;

/**
 * Created by SPARK on 06/02/16.
 */
public class TripSearchResponse {

    public Trips trips;

    public class Trips {

        public List<TripOption> tripOption;
    }

    public class TripOption {
        public String saleTotal;
        public List<Slice> slice;

    }

    public class Slice {

        public List<Segment> segment;
    }


    public class Segment {

        public List<Leg> leg;
    }


    public class Leg {
        public String arrivalTime;
        public String departureTime;
        public String origin;
        public String destination;
        public String duration;
        public String aircraft;

    }
}
