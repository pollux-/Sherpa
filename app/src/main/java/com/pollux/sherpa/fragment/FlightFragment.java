package com.pollux.sherpa.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pollux.sherpa.R;
import com.pollux.sherpa.adapter.FlightAdapter;
import com.pollux.sherpa.io.SherpaClient;
import com.pollux.sherpa.manager.DataManager;
import com.pollux.sherpa.model.TravelRequest;
import com.pollux.sherpa.model.TripSearchResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by SPARK on 07/02/16.
 */
public class FlightFragment extends Fragment implements DataManager.FlightCodeCallback {

    private RecyclerView flightList;
    private View rootView;
    private FlightAdapter flightAdapter;
    private List<TripSearchResponse.TripOption> tripOption = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        flightList = (RecyclerView) rootView.findViewById(R.id.flight_list);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        flightList.setLayoutManager(manager);


        DataManager.newInstance().findCitiesLatLong(this);


    }

    @Override
    public void onFlightCodeFound(String code) {
        getFlightDetails(code);

    }


    public void getFlightDetails(String dest) {


        SherpaClient client = new SherpaClient();
        TravelRequest request = new TravelRequest();
        TravelRequest.Slice slice = new TravelRequest.Slice();

        Date date = new Date();
        String searchDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

        slice.origin = "BLR";
        slice.destination = dest;
        slice.date = searchDate;
        request.request.slice.add(slice);

        client.getSherpaServices().getFlightDetails(request, new Callback<TripSearchResponse>() {

            @Override
            public void success(TripSearchResponse tripSearchResponse, Response response) {

                if (getActivity() != null && tripSearchResponse != null && tripSearchResponse.trips != null && tripSearchResponse.trips.tripOption != null && tripSearchResponse.trips.tripOption.size() > 0) {
                    tripOption.addAll(tripSearchResponse.trips.tripOption);
                    if (flightAdapter == null) {
                        flightAdapter = new FlightAdapter(getActivity(), tripOption);
                        flightList.setAdapter(flightAdapter);
                    } else {
                        flightAdapter.notifyDataSetChanged();
                    }
                    rootView.findViewById(R.id.progress).setVisibility(View.GONE);
                }



            }

            @Override
            public void failure(RetrofitError error) {
                rootView.findViewById(R.id.progress).setVisibility(View.GONE);

            }
        });


    }

}
