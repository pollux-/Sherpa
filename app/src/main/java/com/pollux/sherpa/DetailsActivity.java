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
import com.pollux.sherpa.messages.CloseDetails;
import com.pollux.sherpa.model.AirportDataResponse;
import com.pollux.sherpa.model.AlchemyResponse;
import com.pollux.sherpa.model.LatLongResponse;
import com.pollux.sherpa.model.NearbyPlacesResponse;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";
    public static final int PLACE = 100;
    public static final int FLIGHT = 200;
    public static final String BUNDLE_MESSAGE = "MESSAGE";
    public static long miniAppId = -1;
    private RadioGroup toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String message = extras.getString(BUNDLE_MESSAGE);
            Log.d(TAG,"Message :" + message);

        }

        replaceFragment(PLACE);
        setListeners();
        EventBus.getDefault().register(this);


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

        toolbar = (RadioGroup) findViewById(R.id.radioGroup1);

        // Checked change Listener for RadioGroup 1
        toolbar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
        placesClient.getPlacesServices().getLatLong("goa", new Callback<LatLongResponse>() {
            @Override
            public void success(LatLongResponse latLongResponse, Response response) {

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
