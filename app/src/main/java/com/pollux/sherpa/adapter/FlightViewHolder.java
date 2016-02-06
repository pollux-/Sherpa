package com.pollux.sherpa.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pollux.sherpa.R;

/**
 * Created by SPARK on 07/02/16.
 */
public class FlightViewHolder extends RecyclerView.ViewHolder {

    public TextView source;
    public TextView destination;
    public TextView startTime;
    public TextView endTime;
    public TextView duration;
    public TextView cost;
    public TextView aircraft;

    public FlightViewHolder(View itemView) {
        super(itemView);
        source = (TextView) itemView.findViewById(R.id.source);
        destination = (TextView) itemView.findViewById(R.id.dest);
        cost = (TextView) itemView.findViewById(R.id.price);
        startTime = (TextView) itemView.findViewById(R.id.start_time);
        endTime = (TextView) itemView.findViewById(R.id.end_time);
        duration = (TextView) itemView.findViewById(R.id.duration);
        aircraft = (TextView) itemView.findViewById(R.id.flight_name);

    }
}
