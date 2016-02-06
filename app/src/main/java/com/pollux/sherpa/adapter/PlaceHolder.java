package com.pollux.sherpa.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pollux.sherpa.R;
import com.pollux.sherpa.model.TripSearchResponse;

/**
 * Created by SPARK on 07/02/16.
 */
public class PlaceHolder extends RecyclerView.ViewHolder {

    public TextView detail;
    public ImageView bg;
    private LayoutInflater inflater;
    private TripSearchResponse response;




    public PlaceHolder(View itemView) {
        super(itemView);
        detail = (TextView) itemView.findViewById(R.id.info);
        bg = (ImageView) itemView.findViewById(R.id.bg_image);
    }
}
