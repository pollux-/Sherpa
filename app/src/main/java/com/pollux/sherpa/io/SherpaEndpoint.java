package com.pollux.sherpa.io;

import com.pollux.sherpa.model.AlchemyVo;
import com.pollux.sherpa.model.TravelRequest;
import com.pollux.sherpa.model.TripSearchResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by SPARK on 06/02/16.
 */
public interface SherpaEndpoint {
    @POST("/search?key=AIzaSyDlo9wpADi9DWnRZdoIMh6NuKzCVb3Wq0A")
    void getFlightDetails(@Body TravelRequest user, Callback<TripSearchResponse> responseCallback);

    @GET("/text/TextGetRankedNamedEntities?text=lets go to goa&outputMode=json&apikey=21285e461cb36450a7d40b28aaa992564dd1a7bf")
    void checkForPlace(@Query("text") String text, Callback<AlchemyVo> response);
}
