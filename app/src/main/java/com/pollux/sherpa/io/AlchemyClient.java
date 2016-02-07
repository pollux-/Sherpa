package com.pollux.sherpa.io;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by idea on 07-02-2016.
 */
public class AlchemyClient {
    private static final String ALCHEMY_DEV_URL = "http://access.alchemyapi.com/calls";

    private SherpaEndpoint mServies;


    /**
     * Build the Rest adapter
     */
    public AlchemyClient() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ALCHEMY_DEV_URL)
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
    public SherpaEndpoint getAlchemyServices() {
        return mServies;
    }
}
