
package com.pollux.sherpa.io;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by SreeKumar on 2/7/15
 */
public class SherpaClient {

    private static final String DEV_URL = "https://www.googleapis.com/qpxExpress/v1/trips";

    private SherpaEndpoint mServies;


    /**
     * Build the Rest adapter
     */
    public SherpaClient() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(DEV_URL)
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
    public SherpaEndpoint getSherpaServices() {
        return mServies;
    }


}
