package com.pollux.sherpa.manager;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.pollux.sherpa.io.AirportDataClient;
import com.pollux.sherpa.io.AlchemyClient;
import com.pollux.sherpa.io.AlchemyTaxoClient;
import com.pollux.sherpa.io.PlacesClient;
import com.pollux.sherpa.io.TwitterSentimentClient;
import com.pollux.sherpa.messages.SentimentalMessage;
import com.pollux.sherpa.model.AirportDataResponse;
import com.pollux.sherpa.model.AlchemyResponse;
import com.pollux.sherpa.model.PlaceDataResponse;
import com.pollux.sherpa.model.TaxoResponse;
import com.pollux.sherpa.utils.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedInput;

/**
 * Created by idea on 07-02-2016.
 */
public class DataManager {

    private static DataManager sInstance;
    private List<String> citiesList;
    private List<String> eventSearchList;
    private List<String> destinationAirportCodes;
    private List<PlaceDataResponse.Results> placeDataList;

    public interface AnalysisCallback {
        void onCityFound(List<String> citiesList);

        void onTaxamonyFound(List<String> eventSearchList);

        void onEventFound();
    }

    public interface FlightCodeCallback {
        void onFlightCodeFound(String code);
    }

    public List<PlaceDataResponse.Results> getPlaceDataList() {
        return placeDataList;
    }

    private DataManager() {
        citiesList = new ArrayList<>();
        eventSearchList = new ArrayList<>();
        destinationAirportCodes = new ArrayList<>();
        placeDataList = new ArrayList<>();
    }

    public static DataManager newInstance() {
        if (sInstance == null) {
            sInstance = new DataManager();
        }

        return sInstance;
    }

    public void doAnalysis(String userInput, final AnalysisCallback callback) {
        citiesList.clear();
        AlchemyClient alchemyClient = new AlchemyClient();
        alchemyClient.getAlchemyServices().checkForPlace(userInput, new Callback<AlchemyResponse>() {
            @Override
            public void success(AlchemyResponse alchemyResponse, Response response) {
                Log.d("tony", "yea");
                for (AlchemyResponse.Entities singleEnt : alchemyResponse.getEntities()) {
                    if (!TextUtils.isEmpty(singleEnt.getType()) && singleEnt.getType().equalsIgnoreCase("city")
                            || singleEnt.getType().equalsIgnoreCase("Country")
                            || singleEnt.getType().equalsIgnoreCase("State")
                            || singleEnt.getType().equalsIgnoreCase("Town")) {
                        citiesList.add(singleEnt.getText());
                    }
                }
                if (callback != null) {
                    callback.onCityFound(citiesList);

                    Log.d("TWITTER", citiesList.toString());
                    if (citiesList.size() > 0) {


                        Constants.CITY =  citiesList.get(0);

                    } else {
                        Log.d("TWITTER", "NO Cities Found");
                    }
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void findTaxonomy(String userInput, final AnalysisCallback callback) {
        eventSearchList.clear();
        AlchemyTaxoClient alchemyTaxoClient = new AlchemyTaxoClient();
        alchemyTaxoClient.getAlchemyTaxoServices().getSentimentTaxonomy(userInput, new Callback<TaxoResponse>() {
            @Override
            public void success(TaxoResponse taxoResponse, Response response) {
                for (int j = 0; j < citiesList.size(); j++) {
                    eventSearchList.add("places in" + citiesList.get(j));
                }
                for (TaxoResponse.Taxonomy singleTaxo : taxoResponse.getTaxonomy()) {

                    if (singleTaxo.getLabel().contains("/travel/tourist destinations/")
                            || singleTaxo.getLabel().contains("/travel/hotels")
                            || singleTaxo.getLabel().contains("/travel/tourist facilities/hotel")) {
                        if (callback != null)
                            callback.onTaxamonyFound(eventSearchList);
                        continue;
                    }

                    String[] actions = singleTaxo.getLabel().split("/");

                    for (int i = 0; i < citiesList.size(); i++) {
                        try {

                            eventSearchList.add(actions[1] + " and " + actions[2] + " in " + citiesList.get(i));
                        } catch (Exception e) {

                        }
                    }
                    if (callback != null)
                        callback.onTaxamonyFound(eventSearchList);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void findCitiesLatLong(final FlightCodeCallback callback) {

        destinationAirportCodes.clear();
        PlacesClient placesClient = new PlacesClient();
        for (int i = 0; i < citiesList.size(); i++) {
            placesClient.getPlacesServices().getLatLong(citiesList.get(i), new Callback<PlaceDataResponse>() {
                @Override
                public void success(PlaceDataResponse placeDataResponse, Response response) {
                    if(placeDataResponse == null && placeDataResponse.getResults()[0]== null)
                        return;

                    final AirportDataClient airportDataClient = new AirportDataClient();
                    airportDataClient.getAirportDataServices()
                            .getAirportCode(placeDataResponse.getResults()[0].getGeometry().getLocation().getLat()
                                    , placeDataResponse.getResults()[0].getGeometry().getLocation().getLng()
                                    , new Callback<AirportDataResponse[]>() {
                                @Override
                                public void success(AirportDataResponse[] airportDataResponse, Response response) {
                                    Log.d("FLIGHT", "onSuccess");

                                    // destinationAirportCodes.add(airportDataResponse.get(0).getAirport());
                                    if (callback != null) {
                                        if (airportDataResponse != null && airportDataResponse.length > 0)
                                            callback.onFlightCodeFound(airportDataResponse[0].getAirport());
                                    }

                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Log.d("FLIGHT", "onFail");
                                }
                            });
                }

                @Override
                public void failure(RetrofitError error) {

                    Log.d("FLIGHT", "onFail");

                }
            });

        }
    }

    public void searchEvents(final AnalysisCallback analysisCallback) {
        placeDataList.clear();
        PlacesClient placesClient = new PlacesClient();
        for (int i = 0; i < eventSearchList.size(); i++) {
            Log.d("tony", "event=" + eventSearchList.get(i));
            if(i == 1)
                break;
            placesClient.getPlacesServices().getLatLong(eventSearchList.get(i), new Callback<PlaceDataResponse>() {
                @Override
                public void success(PlaceDataResponse placeDataResponse, Response response) {


                    for (int j = 0; j < placeDataResponse.getResults().length; j++) {
                        if (j == 10)
                            break;
                        placeDataList.add(placeDataResponse.getResults()[j]);

                    }
                    if (analysisCallback != null) {
                        analysisCallback.onEventFound();
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }
    }

}
