package com.pollux.sherpa.io;

import com.pollux.sherpa.model.TravelRequest;
import com.pollux.sherpa.model.TripSearchResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by SPARK on 06/02/16.
 */
public interface SherpaEndpoint {
    @POST("/search?key=AIzaSyDlo9wpADi9DWnRZdoIMh6NuKzCVb3Wq0A")
    void getFlightDetails(@Body TravelRequest user, Callback<TripSearchResponse> responseCallback);
}
