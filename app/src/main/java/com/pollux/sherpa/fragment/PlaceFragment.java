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
import com.pollux.sherpa.adapter.PlaceAdapter;
import com.pollux.sherpa.manager.DataManager;

/**
 * Created by SPARK on 07/02/16.
 */
public class PlaceFragment extends Fragment {

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
        placeList.setAdapter(new PlaceAdapter(getActivity(), DataManager.newInstance().getPlaceDataList()));
        rootView.findViewById(R.id.progress).setVisibility(View.GONE);

    }
}
