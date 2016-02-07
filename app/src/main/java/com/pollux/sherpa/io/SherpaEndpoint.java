package com.pollux.sherpa.io;

import com.pollux.sherpa.model.AirportDataResponse;
import com.pollux.sherpa.model.AlchemyResponse;
import com.pollux.sherpa.model.PlaceDataResponse;
import com.pollux.sherpa.model.NearbyPlacesResponse;
import com.pollux.sherpa.model.TaxoResponse;
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
    @POST("/search?key=AIzaSyD0Veflv-4fU7ycJijc-0s9o52U97BobCU")
    void getFlightDetails(@Body TravelRequest user, Callback<TripSearchResponse> responseCallback);

    @GET("/text/TextGetRankedNamedEntities?outputMode=json&apikey=21285e461cb36450a7d40b28aaa992564dd1a7bf")
    void checkForPlace(@Query("text") String text, Callback<AlchemyResponse> responseCallback);

    @GET("/TextGetRankedTaxonomy?outputMode=json&apikey=21285e461cb36450a7d40b28aaa992564dd1a7bf")
    void getSentimentTaxonomy(@Query("text") String text, Callback<TaxoResponse> responseCallback);

    @GET("/textsearch/json?key=AIzaSyAOFqhu6CSeTQkTEsGiq670YOz0SaLd2sg")
    void getLatLong(@Query("query") String query, Callback<PlaceDataResponse> responseCallback);

    @GET("/nearbysearch/json?key=AIzaSyDlo9wpADi9DWnRZdoIMh6NuKzCVb3Wq0A&radius=50000")
    void getNearbyPlaces(@Query("location") String location, Callback<NearbyPlacesResponse> responseCallback);

    @GET("/nearest-relevant?apikey=NAowoC2E8Xm0qKHmBKGb0wjr5ZbiqGVs")
    void getAirportCode(@Query("latitude") String latitude, @Query("longitude") String longitude
            , Callback<AirportDataResponse[]> responseCallback);

    @GET("/api/tweets")
    void getSentiment(@Query("query") String place
            , Callback<retrofit.client.Response> responseCallback);
}
