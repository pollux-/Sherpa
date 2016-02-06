package com.pollux.sherpa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pollux.sherpa.R;
import com.pollux.sherpa.model.TripSearchResponse;
import com.pollux.sherpa.utils.Util;

/**
 * Created by SPARK on 07/02/16.
 */
public class FlightAdapter extends RecyclerView.Adapter<FlightViewHolder> {

    private LayoutInflater inflater;
    private TripSearchResponse  response;

    public FlightAdapter(Context context, TripSearchResponse response) {
        this.inflater = LayoutInflater.from(context);
        this.response = response;

    }

    @Override
    public FlightViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.flight_item_layout,parent,false);
        return  new FlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlightViewHolder holder, int position) {

        if(response!= null && response.trips!=null && response.trips.tripOption!= null && response.trips.tripOption.size() > 0){

            TripSearchResponse.TripOption tripOption = response.trips.tripOption.get(position);
            if(tripOption !=null){
                final String cost = getCost(tripOption.saleTotal);
                holder.cost.setText(cost);

                if(tripOption.slice != null && tripOption.slice.size() > 0 ){

                    TripSearchResponse.Slice slice = tripOption.slice.get(0);

                    if(slice != null && slice.segment!= null && slice.segment.size() > 0 ){

                        TripSearchResponse.Segment segment = slice.segment.get(0);
                        if(segment != null && segment.leg != null && segment.leg.size() > 0){
                            TripSearchResponse.Leg leg = segment.leg.get(0);
                            if(leg != null) {
                                holder.source.setText(leg.origin);
                                holder.destination.setText(leg.destination);
                                holder.aircraft.setText(leg.aircraft);
                                holder.duration.setText(leg.duration + " minutes");
                                holder.startTime.setText(Util.getTime(leg.departureTime));
                                holder.endTime.setText(Util.getTime(leg.arrivalTime));
                            }

                        }

                    }

                }


            }


        }


    }

    private String getCost(String saleTotal) {

        String[] parts = saleTotal.split("INR");
        if(parts != null && parts.length > 1){
            return parts [1] + " INR";
        }
        return "";
    }

    @Override
    public int getItemCount() {

        if(response != null && response.trips !=null && response.trips.tripOption !=null)
        return response.trips.tripOption.size();
        return 0;
    }
}
