package com.pollux.sherpa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pollux.sherpa.R;
import com.pollux.sherpa.model.PlaceDataResponse;

import java.util.List;

/**
 * Created by SPARK on 07/02/16.
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.RVHolder> {

    private LayoutInflater inflater;
    private List<PlaceDataResponse.Results> response;

    public PlaceAdapter(Context context, List<PlaceDataResponse.Results> response) {
        this.inflater = LayoutInflater.from(context);
        this.response = response;

    }


    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.place_item_layout, parent, false);
        return new RVHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceAdapter.RVHolder holder, int position) {
        holder.nameTv.setText(response.get(position).getName());

    }


    @Override
    public int getItemCount() {
        return response.size();
    }

    public static class RVHolder extends RecyclerView.ViewHolder {

        ImageView placeIv;
        TextView nameTv;

        public RVHolder(View itemView) {
            super(itemView);

            placeIv = (ImageView) itemView.findViewById(R.id.bg_image);
            nameTv = (TextView) itemView.findViewById(R.id.info);

        }
    }
}
