package com.pollux.sherpa.io;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by idea on 07-02-2016.
 */
public class AlchemyTaxoClient {
    private static final String ALCHEMY_TAXO_DEV_URL = "http://gateway-a.watsonplatform.net/calls/text";

    private SherpaEndpoint mServies;


    /**
     * Build the Rest adapter
     */
    public AlchemyTaxoClient() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ALCHEMY_TAXO_DEV_URL)
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
    public SherpaEndpoint getAlchemyTaxoServices() {
        return mServies;
    }
}
