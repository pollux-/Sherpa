package com.pollux.sherpa.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SPARK on 06/02/16.
 */
public class TravelRequest {

    public Request request = new Request();


    public  class Request{
        public Passengers passengers =new Passengers();
        public List<Slice> slice = new ArrayList<>(1);
        public class Passengers{
            public int adultCount =1;

        }
    }

    public  static class Slice{
        public String origin;
        public String destination;
        public String date;

    }

}
