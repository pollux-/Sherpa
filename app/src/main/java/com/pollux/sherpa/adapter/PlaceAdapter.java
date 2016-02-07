package com.pollux.sherpa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pollux.sherpa.R;
import com.pollux.sherpa.messages.SentimentalMessage;
import com.pollux.sherpa.model.PlaceDataResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by SPARK on 07/02/16.
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.RVHolder> {

    private LayoutInflater inflater;
    private List<PlaceDataResponse.Results> response;
    TextView cityName;
    PieChart mPieChart;
    private static final String PHOTO_BASE_URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&key=AIzaSyAOFqhu6CSeTQkTEsGiq670YOz0SaLd2sg";

    public PlaceAdapter(Context context, List<PlaceDataResponse.Results> response) {
        this.inflater = LayoutInflater.from(context);
        this.response = response;
        EventBus.getDefault().register(this);

    }
    public void onEvent(SentimentalMessage message)
    {
        cityName.setText("Sentiment Analysis for "+message.getCity());
        Log.d("TWITTER","Got Sentimental Message: "+message.toString());
        mPieChart.setUsePercentValues(true);
        mPieChart.setDescription("");
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);


        mPieChart.setCenterText("Twitter Sentiments");

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColorTransparent(true);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        // mPieChart.setUnit(" €");
        // mPieChart.setDrawUnitsInChart(true);

        // add a selection listener


        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        int count = 3;
            

        ArrayList<String> xVals = new ArrayList<String>();
        yVals1.add(new Entry(message.getPosRatio(), 0));
            xVals.add("Positive");
        yVals1.add(new Entry(message.getNegRatio(), 1));
            xVals.add("Negative");
        yVals1.add(new Entry(message.getNeuRatio(),2 ));
            xVals.add("Neutral");

        PieDataSet dataSet = new PieDataSet(yVals1, "Election Results");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);

        // undo all highlights
        mPieChart.highlightValues(null);

        mPieChart.invalidate();

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mPieChart.spin(2000, 0, 360);

        Legend l = mPieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);


    }
    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView)
    {
        EventBus.getDefault().unregister(this);
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public int getItemViewType(int position)
    {
        return super.getItemViewType(position);
    }

    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.place_item_layout, parent, false);
        View view1 = inflater.inflate(R.layout.sentiment_card, parent, false);
        cityName = (TextView) view1.findViewById(R.id.city_name);
        mPieChart = (PieChart) view1.findViewById(R.id.pie_chart);
        return new RVHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceAdapter.RVHolder holder, int position) {
        holder.nameTv.setText(response.get(position).getName());
        PlaceDataResponse.Photos[] photos = response.get(position).getPhotos();
        if(photos !=null && photos.length > 0)
        Picasso.with(holder.nameTv.getContext()).load(PHOTO_BASE_URL+"&photoreference=" + photos[0].getPhotoReference()).into(holder.placeIv);

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
