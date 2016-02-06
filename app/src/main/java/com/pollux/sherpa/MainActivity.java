package com.pollux.sherpa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pollux.sherpa.adapter.FlightAdapter;
import com.pollux.sherpa.io.SherpaClient;
import com.pollux.sherpa.model.TravelRequest;
import com.pollux.sherpa.model.TripSearchResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView flightList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flightList = (RecyclerView) findViewById(R.id.flight_list);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        flightList.setLayoutManager(manager);

        SherpaClient client = new SherpaClient();
        TravelRequest request = new TravelRequest();
        TravelRequest.Slice slice = new TravelRequest.Slice();


        slice.origin ="BOM";
        slice.destination="BLR";
        slice.date ="2016-02-11";
        request.request.slice.add(slice);

        client.getSherpaServices().getFlightDetails(request, new Callback<TripSearchResponse>() {
            @Override
            public void success(TripSearchResponse tripSearchResponse, Response response) {

                Log.d(TAG, "Hello " + tripSearchResponse);

                flightList.setAdapter(new FlightAdapter(MainActivity.this,tripSearchResponse));

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


    }
}
