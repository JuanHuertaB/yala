package com.beater.yala.fragments;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.beater.yala.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EstadisticasFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    PieChart pieChart_album;
    PieChart pieChart_collection;

    public EstadisticasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_estadisticas, container, false);
        pieChart_album = (PieChart) view.findViewById(R.id.piChart);

        pieChart_album.setUsePercentValues(false);
        pieChart_album.getDescription().setEnabled(true);

        Description des = new Description();
        des.setText("Valores en %");
        pieChart_album.setDescription(des);
        pieChart_album.setExtraOffsets(5,10,5,5);

        pieChart_album.setDrawHoleEnabled(false);
        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry((30f/300f)*100f ,"Rusia 2018"));
        yValues.add(new PieEntry((270f/300f)*100f,"faltantes"));

        PieDataSet dataSet = new PieDataSet(yValues,null);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLUE);

        pieChart_album.setData(data);
        return view;
    }

    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }
}
