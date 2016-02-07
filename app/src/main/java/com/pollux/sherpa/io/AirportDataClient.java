package com.pollux.sherpa.io;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by idea on 07-02-2016.
 */
public class AirportDataClient {
    private static final String DATA_DEV_URL = "https://api.sandbox.amadeus.com/v1.2/airports";

    private SherpaEndpoint mServies;


    /**
     * Build the Rest adapter
     */
    public AirportDataClient() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(DATA_DEV_URL)
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
    public SherpaEndpoint getAirportDataServices() {
        return mServies;
    }
}
