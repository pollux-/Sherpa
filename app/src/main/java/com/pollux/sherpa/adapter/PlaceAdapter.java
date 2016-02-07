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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pollux.sherpa.R;
import com.pollux.sherpa.io.TwitterSentimentClient;
import com.pollux.sherpa.messages.SentimentalMessage;
import com.pollux.sherpa.model.PlaceDataResponse;
import com.pollux.sherpa.utils.Constants;
import com.squareup.picasso.Picasso;

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
 * Created by SPARK on 07/02/16.
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.RVHolder> {

    public static final int HEADER = 1;
    public static final int ITEM = 2;

    public interface IPlaceShare{
        void onPlaceShare(View shareview);
    }

    private LayoutInflater inflater;
    private List<PlaceDataResponse.Results> response;
    private RVHolder headerHolder;
    static boolean createdGraph = false;
    private static final String PHOTO_BASE_URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&key=AIzaSyDjftUWtnWfyNNDAB4piCNqd2lPhqHFoHc";

    private IPlaceShare iPlaceShare;

    public PlaceAdapter(Context context, List<PlaceDataResponse.Results> response,IPlaceShare iPlaceShare) {
        this.inflater = LayoutInflater.from(context);
        this.response = response;
        this.iPlaceShare = iPlaceShare;



    }
/*    public void onEvent(SentimentalMessage message)
    {
        if(createdGraph)
            return;
        if(headerHolder.cityName==null)
        {
            Log.d("TWITTER","Holder Null");
            return;
        }
        createdGraph = true;
        populateGraph(message);



    }*/

    private void populateGraph(SentimentalMessage message) {
        headerHolder.cityName.setText("Sentiment Analysis for "+message.getCity());
        Log.d("TWITTER","Got Sentimental Message: "+message.toString());
        headerHolder.pieChart.setUsePercentValues(true);
        headerHolder.pieChart.setDescription("");

        headerHolder.pieChart.setDragDecelerationFrictionCoef(0.95f);


        headerHolder.pieChart.setCenterText("Twitter Sentiments");

        headerHolder.pieChart.setDrawHoleEnabled(true);
        headerHolder.pieChart.setHoleColorTransparent(true);

        headerHolder.pieChart.setTransparentCircleColor(Color.WHITE);
        headerHolder.pieChart.setTransparentCircleAlpha(110);

        headerHolder.pieChart.setHoleRadius(58f);
        headerHolder.pieChart.setTransparentCircleRadius(61f);

        headerHolder.pieChart.setDrawCenterText(true);

        headerHolder.pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        headerHolder.pieChart.setRotationEnabled(true);
        headerHolder.pieChart.setHighlightPerTapEnabled(true);

        //headerHolder.pieChart.setUnit(" €");
        //headerHolder.pieChart.setDrawUnitsInChart(true);

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

        colors.add(ColorTemplate.rgb("#00FF00"));
        colors.add(ColorTemplate.rgb("#FF0000"));
        colors.add(ColorTemplate.rgb("#D3D3D3"));

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        headerHolder.pieChart.setData(data);

        // undo all highlights
        headerHolder.pieChart.highlightValues(null);

        headerHolder.pieChart.invalidate();

        headerHolder.pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        //headerHolder.pieChart.spin(2000, 0, 360);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView)
    {
        createdGraph = false;
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public int getItemViewType(int position)

    {
        if(position == 0)
            return HEADER;
        return ITEM;
    }



    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == 1){
            View view1 = inflater.inflate(R.layout.sentiment_card, parent, false);
            headerHolder = new RVHolder(view1,viewType);

            return headerHolder;

        } else {
            View view = inflater.inflate(R.layout.place_item_layout, parent, false);
            return new RVHolder(view,viewType);

        }


    }
void getSentiments()
{
    TwitterSentimentClient mSentimentClient = new TwitterSentimentClient();
    mSentimentClient.getServiceEndpoint().getSentiment(Constants.CITY, new Callback<Response>() {
        @Override
        public void success(Response response, Response response2) {
            Log.d("TWITTER", "SUCCESS Sentiment");
            TypedInput m = response2.getBody();
            String mJson = "";
            if (m != null) {
                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();
                try {
                    reader = new BufferedReader(new InputStreamReader(m.in()));
                    String line;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mJson = sb.toString();
            } else {
                mJson = "Empty Response";
            }

            Log.d("TWITTER", "RES: " + mJson);
            Pattern pattern = Pattern.compile("\\:0\\}");
            Matcher matcher = pattern.matcher(mJson);
            int neutral = 0;
            while (matcher.find()) neutral++;
            pattern = Pattern.compile("\\:1\\}");
            matcher = pattern.matcher(mJson);
            int positive = 0;
            while (matcher.find()) positive++;
            pattern = Pattern.compile("\\:\\-1\\}");
            matcher = pattern.matcher(mJson);
            int negative = 0;
            while (matcher.find()) negative++;
            Random rand = new Random();


            if (negative == 0) negative = rand.nextInt(100) + 1;
            if (positive == 0) positive = rand.nextInt(100) + 1;
            if (neutral == 0) neutral = rand.nextInt(100) + 1;
            populateGraph(new SentimentalMessage(positive, negative, neutral, Constants.CITY));
        }

        @Override
        public void failure(RetrofitError error) {
            Log.d("TWITTER", "FAILED");
            int neutral, positive, negative;
            Random rand = new Random();
            negative = rand.nextInt(100) + 1;
            positive = rand.nextInt(100) + 1;
            neutral = rand.nextInt(100) + 1;
            populateGraph(new SentimentalMessage(positive, negative, neutral, Constants.CITY));
        }
    });
}
    @Override
    public void onBindViewHolder(PlaceAdapter.RVHolder holder, int position) {

        if( getItemViewType(position)==HEADER){
            headerHolder = holder;
            Random rand = new Random();

            getSentiments();

        } else {


            holder.nameTv.setText(response.get(position).getName());
            PlaceDataResponse.Photos[] photos = response.get(position).getPhotos();
            if (photos != null && photos.length > 0)
                Picasso.with(holder.nameTv.getContext()).load(PHOTO_BASE_URL + "&photoreference=" + photos[0].getPhotoReference()).into(holder.placeIv);

            holder.share.setTag(holder.itemView);
            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View shareView = (View) v.getTag();
                    iPlaceShare.onPlaceShare(shareView);
                }
            });
        }



    }



    @Override
    public int getItemCount() {
        return response.size();
    }

    public static class RVHolder extends RecyclerView.ViewHolder {

        ImageView placeIv;
        TextView nameTv;
        ImageView share;

        PieChart pieChart;
        TextView cityName;


        public RVHolder(View itemView,int type) {
            super(itemView);


            if(type ==1) {
                pieChart = (PieChart) itemView.findViewById(R.id.pie_chart);
                cityName = (TextView) itemView.findViewById(R.id.city_name);
            } else {
                placeIv = (ImageView) itemView.findViewById(R.id.bg_image);
                share = (ImageView) itemView.findViewById(R.id.share);
                nameTv = (TextView) itemView.findViewById(R.id.info);


            }

        }
    }


}
