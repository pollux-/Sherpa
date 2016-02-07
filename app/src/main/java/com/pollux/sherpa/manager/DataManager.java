package com.pollux.sherpa.manager;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.pollux.sherpa.io.AirportDataClient;
import com.pollux.sherpa.io.AlchemyClient;
import com.pollux.sherpa.io.AlchemyTaxoClient;
import com.pollux.sherpa.io.PlacesClient;
import com.pollux.sherpa.model.AirportDataResponse;
import com.pollux.sherpa.model.AlchemyResponse;
import com.pollux.sherpa.model.LatLongResponse;
import com.pollux.sherpa.model.TaxoResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by idea on 07-02-2016.
 */
public class DataManager {

    private static DataManager sInstance;
    private static Activity sActivity;

    private List<String> citiesList;
    private List<String> eventSearchList;
    private List<String> destinationAirportCodes;

    private DataManager() {
        citiesList = new ArrayList<>();
        eventSearchList = new ArrayList<>();
        destinationAirportCodes = new ArrayList<>();
    }

    public static DataManager newInstance(Activity activity) {
        if (sInstance == null) {
            new DataManager();
        }
        sActivity = activity;

        return sInstance;
    }

    public void doAnalysis(String userInput) {
        citiesList.clear();
        AlchemyClient alchemyClient = new AlchemyClient();
        alchemyClient.getAlchemyServices().checkForPlace(userInput, new Callback<AlchemyResponse>() {
            @Override
            public void success(AlchemyResponse alchemyResponse, Response response) {
                Log.d("tony", "yea");
                for (AlchemyResponse.Entities singleEnt : alchemyResponse.getEntities()) {
                    if (singleEnt.getType().equalsIgnoreCase("city")) {
                        citiesList.add(singleEnt.getText());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void findTaxonomy(String userInput) {
        eventSearchList.clear();
        AlchemyTaxoClient alchemyTaxoClient = new AlchemyTaxoClient();
        alchemyTaxoClient.getAlchemyTaxoServices().getSentimentTaxonomy(userInput, new Callback<TaxoResponse>() {
            @Override
            public void success(TaxoResponse taxoResponse, Response response) {
                for (TaxoResponse.Taxonomy singleTaxo : taxoResponse.getTaxonomy()) {
                    String[] actions = singleTaxo.getLabel().split("/");

                    for (int i = 0; i < citiesList.size(); i++) {
                        eventSearchList.add(actions[0] + " and " + actions[1] + " in " + citiesList.get(i));
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void findCitiesLatLong() {
        PlacesClient placesClient = new PlacesClient();
        for (int i = 0; i < citiesList.size(); i++) {
            placesClient.getPlacesServices().getLatLong(citiesList.get(i), new Callback<LatLongResponse>() {
                @Override
                public void success(LatLongResponse latLongResponse, Response response) {

                    final AirportDataClient airportDataClient = new AirportDataClient();
                    airportDataClient.getAirportDataServices()
                            .getAirportCode(latLongResponse.getResults()[0].getGeometry().getLocation().getLat()
                                    , latLongResponse.getResults()[0].getGeometry().getLocation().getLng()
                                    , new Callback<AirportDataResponse>() {
                                @Override
                                public void success(AirportDataResponse airportDataResponse, Response response) {
                                    Log.d("tony", "onSuccess");
                                    destinationAirportCodes.add(airportDataResponse.getAirport());

                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Log.d("tony", "onFail");
                                }
                            });
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        }
    }


}
