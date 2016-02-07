package com.pollux.sherpa;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioGroup;

import com.pollux.sherpa.fragment.FlightFragment;
import com.pollux.sherpa.fragment.PlaceFragment;
import com.pollux.sherpa.io.AirportDataClient;
import com.pollux.sherpa.io.AlchemyClient;
import com.pollux.sherpa.io.PlacesClient;
import com.pollux.sherpa.model.AirportDataResponse;
import com.pollux.sherpa.model.AlchemyResponse;
import com.pollux.sherpa.model.PlaceDataResponse;
import com.pollux.sherpa.model.NearbyPlacesResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.pollux.sherpa.messages.CloseDetails;

import de.greenrobot.event.EventBus;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";
    public static final int PLACE = 100;
    public static final int FLIGHT = 200;
    public static long miniAppId = -1;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        EventBus.getDefault().register(this);

        replaceFragment(PLACE);
        setListeners();


       /*  Intent intent = new Intent(DetailsActivity.this, FloatingActivity.class);
       TooleapPopOutMiniApp miniApp = new TooleapPopOutMiniApp(DetailsActivity.this, intent);
        miniApp.contentTitle = "Sherpa";
        miniApp.notificationText = "Hello! I'm Your Sherpa";
        miniApp.bubbleBackgroundColor = 0x78FFFFFF;
        Tooleap tooleap = Tooleap.getInstance(DetailsActivity.this);
        tooleap.removeAllMiniApps();
        miniAppId = tooleap.addMiniApp(miniApp);*/


    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEvent(CloseDetails m) {
        finish();
    }

    private void setListeners() {

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);

        // Checked change Listener for RadioGroup 1
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.places:
                        replaceFragment(PLACE);
                        break;
                    case R.id.flights:
                        replaceFragment(FLIGHT);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    private void replaceFragment(int type) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = null;
        switch (type) {

            case PLACE:
                fragment = new PlaceFragment();
                break;
            case FLIGHT:
                fragment = new FlightFragment();
                break;
        }

        transaction.replace(R.id.container, fragment);
        transaction.commit();

    }

    private void alchemyTest() {
        AlchemyClient alchemyClient = new AlchemyClient();
        alchemyClient.getAlchemyServices().checkForPlace("lets go to goa", new Callback<AlchemyResponse>() {
            @Override
            public void success(AlchemyResponse alchemyVo, Response response) {
                Log.d("tony", "yea");
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private void placesTest() {
        PlacesClient placesClient = new PlacesClient();
        placesClient.getPlacesServices().getLatLong("goa", new Callback<PlaceDataResponse>() {
            @Override
            public void success(PlaceDataResponse placeDataResponse, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        placesClient.getPlacesServices().getNearbyPlaces("15.2993265,74.12399599999999", new Callback<NearbyPlacesResponse>() {
            @Override
            public void success(NearbyPlacesResponse nearbyPlacesResponse, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void airportDataTest() {
        AirportDataClient airportDataClient = new AirportDataClient();
        airportDataClient.getAirportDataServices().getAirportCode("15.2993265", "74.12399599999999", new Callback<AirportDataResponse>() {
            @Override
            public void success(AirportDataResponse airportDataResponse, Response response) {
                Log.d("tony", "onSuccess");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("tony", "onFail");
            }
        });
    }
}
