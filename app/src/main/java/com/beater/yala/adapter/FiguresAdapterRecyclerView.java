package com.beater.yala.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beater.yala.R;
import com.beater.yala.fragments.AlbumDetailsFragment;
import com.beater.yala.model.Figure;

import java.util.ArrayList;

/**
 * Created by JuanCarlos on 3/07/2018.
 */
public class FiguresAdapterRecyclerView extends RecyclerView.Adapter<FiguresAdapterRecyclerView.FiguresViewHolder> {

    private ArrayList<Figure> figuras;
    private int resource;
    private Activity activity;
    private AlbumDetailsFragment albumDetails;

    public FiguresAdapterRecyclerView(ArrayList<Figure> figuras, int resource, Activity activity) {
        this.figuras = figuras;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public FiguresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new FiguresViewHolder(view);
    }

    @Override
    public void onBindViewHolder( final FiguresViewHolder holder, int position) {
        final Figure figure = figuras.get(position);
        holder.figura.setText(Integer.toString(figure.getNumber()));
    }

    @Override
    public int getItemCount() {
        return figuras.size();
    }

    public class FiguresViewHolder extends RecyclerView.ViewHolder {
        private TextView figura;

        public FiguresViewHolder(View itemView) {
            super(itemView);

            figura = (TextView) itemView.findViewById(R.id.figurita);

        }
    }
}
