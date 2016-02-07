package com.pollux.sherpa;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.pollux.sherpa.fragment.FlightFragment;
import com.pollux.sherpa.fragment.PlaceFragment;
import com.pollux.sherpa.manager.DataManager;
import com.pollux.sherpa.messages.CloseDetails;

import java.util.List;

import de.greenrobot.event.EventBus;

public class DetailsActivity extends AppCompatActivity implements DataManager.AnalysisCallback{

    private static final String TAG = "DetailsActivity";
    public static final int PLACE = 100;
    public static final int FLIGHT = 200;
    public static final String BUNDLE_MESSAGE = "MESSAGE";
    private RadioGroup toolbar;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
             message = extras.getString(BUNDLE_MESSAGE);
            Log.d(TAG,"Message :" + message);
            DataManager.newInstance().doAnalysis(message,this);

        }

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


    @Override
    public void onCityFound(List<String> citiesList) {
        if(citiesList!=null && citiesList.size() > 0){
            DataManager.newInstance().findTaxonomy(message,this);
            DataManager.newInstance().findCitiesLatLong(new DataManager.FlightCodeCallback() {
                @Override
                public void onFlightCodeFound(String code) {
                    Log.d("tony1", "Code"+code);
                }
            });
        }

    }

    @Override
    public void onTaxamonyFound(List<String> eventSearchList) {
        DataManager.newInstance().searchEvents(this);


    }

    @Override
    public void onEventFound() {
        replaceFragment(PLACE);
        setListeners();
        findViewById(R.id.progress).setVisibility(View.GONE);


    }
}
