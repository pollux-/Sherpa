package com.pollux.sherpa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pollux.sherpa.R;
import com.pollux.sherpa.messages.SentimentalMessage;
import com.pollux.sherpa.model.PlaceDataResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by SPARK on 07/02/16.
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.RVHolder> {

    public interface IPlaceShare{
        void onPlaceShare(View shareview);
    }

    private LayoutInflater inflater;
    private List<PlaceDataResponse.Results> response;
    private static final String PHOTO_BASE_URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&key=AIzaSyAOFqhu6CSeTQkTEsGiq670YOz0SaLd2sg";

    private IPlaceShare iPlaceShare;

    public PlaceAdapter(Context context, List<PlaceDataResponse.Results> response,IPlaceShare iPlaceShare) {
        this.inflater = LayoutInflater.from(context);
        this.response = response;
        this.iPlaceShare = iPlaceShare;

        EventBus.getDefault().register(this);


    }
    public void onEvent(SentimentalMessage message)
    {
        Log.d("TWITTER","Got Sentimental Message");
    }
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView)
    {
        EventBus.getDefault().unregister(this);
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.place_item_layout, parent, false);
        return new RVHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceAdapter.RVHolder holder, int position) {
        holder.nameTv.setText(response.get(position).getName());
        PlaceDataResponse.Photos[] photos = response.get(position).getPhotos();
        if(photos !=null && photos.length > 0)
        Picasso.with(holder.nameTv.getContext()).load(PHOTO_BASE_URL+"&photoreference=" + photos[0].getPhotoReference()).into(holder.placeIv);

        holder.share.setTag(holder.itemView);
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View shareView = (View) v.getTag();
                iPlaceShare.onPlaceShare(shareView);
            }
        });

    }


    @Override
    public int getItemCount() {
        return response.size();
    }

    public static class RVHolder extends RecyclerView.ViewHolder {

        ImageView placeIv;
        TextView nameTv;
        ImageView share;

        public RVHolder(View itemView) {
            super(itemView);

            placeIv = (ImageView) itemView.findViewById(R.id.bg_image);
            share = (ImageView) itemView.findViewById(R.id.share);
            nameTv = (TextView) itemView.findViewById(R.id.info);

        }
    }
}
