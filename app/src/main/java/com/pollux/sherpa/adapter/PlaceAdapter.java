package com.pollux.sherpa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pollux.sherpa.R;
import com.pollux.sherpa.model.TripSearchResponse;

/**
 * Created by SPARK on 07/02/16.
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceHolder> {

    private LayoutInflater inflater;
    private TripSearchResponse  response;

    public PlaceAdapter(Context context, TripSearchResponse response) {
        this.inflater = LayoutInflater.from(context);
        this.response = response;

    }


    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  inflater.inflate(R.layout.place_item_layout,parent,false);
        return new PlaceHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
