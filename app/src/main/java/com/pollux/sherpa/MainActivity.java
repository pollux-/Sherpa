package com.pollux.sherpa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pollux.sherpa.adapter.FlightAdapter;
import com.pollux.sherpa.io.AlchemyClient;
import com.pollux.sherpa.io.SherpaClient;
import com.pollux.sherpa.model.AlchemyVo;
import com.pollux.sherpa.model.TravelRequest;
import com.pollux.sherpa.model.TripSearchResponse;
import com.tooleap.sdk.Tooleap;
import com.tooleap.sdk.TooleapPopOutMiniApp;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView flightList;
    public static long miniAppId = -1;

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


        slice.origin = "BOM";
        slice.destination = "BLR";
        slice.date = "2016-02-11";
        request.request.slice.add(slice);

        client.getSherpaServices().getFlightDetails(request, new Callback<TripSearchResponse>() {
            @Override
            public void success(TripSearchResponse tripSearchResponse, Response response) {

                Log.d(TAG, "Hello " + tripSearchResponse);

                flightList.setAdapter(new FlightAdapter(MainActivity.this, tripSearchResponse));

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        Intent intent = new Intent(MainActivity.this, FloatingActivity.class);
        TooleapPopOutMiniApp miniApp = new TooleapPopOutMiniApp(MainActivity.this, intent);
        miniApp.contentTitle = "Sherpa";
        miniApp.notificationText = "Hello! I'm Your Sherpa";
        miniApp.bubbleBackgroundColor = 0x78FFFFFF;
        Tooleap tooleap = Tooleap.getInstance(MainActivity.this);
        tooleap.removeAllMiniApps();
        miniAppId = tooleap.addMiniApp(miniApp);

        alchemyTest();

    }

    private void alchemyTest() {
        AlchemyClient alchemyClient = new AlchemyClient();
        alchemyClient.getAlchemyServices().checkForPlace("lets go to go", new Callback<AlchemyVo>() {
            @Override
            public void success(AlchemyVo alchemyVo, Response response) {
                Log.d("tony", "yea");
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}
