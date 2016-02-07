package com.pollux.sherpa.io;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by Shabaz on 07-Feb-16.
 */
public class TwitterSentimentClient
{
    private static final String DEV_URL = "http://tweetsentiment.azurewebsites.net";

    private SherpaEndpoint mServies;
    public TwitterSentimentClient() {
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
    public SherpaEndpoint getServiceEndpoint() {
        return mServies;
    }
}
