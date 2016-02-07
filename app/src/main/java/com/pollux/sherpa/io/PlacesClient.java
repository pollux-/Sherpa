package com.pollux.sherpa.io;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by idea on 07-02-2016.
 */
public class PlacesClient {
    private static final String PLACES_DEV_URL = "https://maps.googleapis.com/maps/api/place";

    private SherpaEndpoint mServies;


    /**
     * Build the Rest adapter
     */
    public PlacesClient() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(PLACES_DEV_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");

                    }
                })
                .build();
        mServies = restAdapter.create(SherpaEndpoint.class);


    }

    /**
     * Get the Latch service object
     *
     * @return
     */
    public SherpaEndpoint getPlacesServices() {
        return mServies;
    }
}
