package com.pollux.sherpa.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pollux.sherpa.R;
import com.pollux.sherpa.adapter.PlaceAdapter;
import com.pollux.sherpa.manager.DataManager;
import com.pollux.sherpa.utils.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by SPARK on 07/02/16.
 */
public class PlaceFragment extends Fragment implements PlaceAdapter.IPlaceShare{

    private RecyclerView placeList;
    private View rootView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        placeList = (RecyclerView) rootView.findViewById(R.id.flight_list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        placeList.setLayoutManager(manager);
        placeList.setAdapter(new PlaceAdapter(getActivity(), DataManager.newInstance().getPlaceDataList(),this));
        rootView.findViewById(R.id.progress).setVisibility(View.GONE);

    }

    @Override
    public void onPlaceShare(View shareView) {

        Bitmap bitmapFromView = Util.getBitmapFromView(shareView);
        File file = writeBitmapToFile(bitmapFromView);
        final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.setType("image/*");
        startActivity(Intent.createChooser(intent, "Share Cover Image"));

    }

    private File writeBitmapToFile(Bitmap bitmap){

        String filename = "pippo.png";
        File sd = Environment.getExternalStorageDirectory();
        File dest = new File(sd, filename);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(dest);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dest;
    }
}
