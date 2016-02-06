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
import com.pollux.sherpa.model.TravelRequest;
import com.pollux.sherpa.model.TripSearchResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by SPARK on 07/02/16.
 */
public class FlightFragment extends Fragment {

    private RecyclerView flightList;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.fragment_list,container,false);
        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        flightList = (RecyclerView) rootView.findViewById(R.id.flight_list);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
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

               flightList.setAdapter(new FlightAdapter(getActivity(),tripSearchResponse));

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }
}
