package com.beater.yala.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beater.yala.R;
import com.beater.yala.fragments.SearchFragment;
import com.beater.yala.model.Coleccionista;

import java.util.ArrayList;

/**
 * Created by JuanCarlos on 5/07/2018.
 */
public class SearchUsersAdapterRecyclerView extends RecyclerView.Adapter<SearchUsersAdapterRecyclerView.UserViewHolder> {

    private ArrayList<Coleccionista> coleccionistas;
    private int resource;
    private Activity activity;
    private SearchFragment searchFragment;

    public SearchUsersAdapterRecyclerView(ArrayList<Coleccionista> coleccionistas, int resource, Activity activity) {
        this.coleccionistas = coleccionistas;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final Coleccionista coleccionista = coleccionistas.get(position);
        searchFragment = new SearchFragment();

        holder.username.setText(coleccionista.getUsername());
        holder.location.setText(coleccionista.getLocation());
        holder.phoneNumber.setText(coleccionista.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return coleccionistas.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private TextView phoneNumber;
        private TextView location;

        public UserViewHolder(View itemView) {
            super(itemView);

            username = (TextView) itemView.findViewById(R.id.userSearch);
            phoneNumber= (TextView) itemView.findViewById(R.id.phoneSearch);
            location = (TextView) itemView.findViewById(R.id.locationSearch);
        }
    }
}
