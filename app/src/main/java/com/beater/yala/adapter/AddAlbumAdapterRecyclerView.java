package com.beater.yala.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beater.yala.R;
import com.beater.yala.model.Album;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by JuanCarlos on 18/06/2018.
 */
public class AddAlbumAdapterRecyclerView extends RecyclerView.Adapter<AddAlbumAdapterRecyclerView.AddAlbumViewHolder>{

    private ArrayList<Album> albums;
    private int resource;
    private Activity activity;

    public AddAlbumAdapterRecyclerView(ArrayList<Album> albums, int resource, Activity activity) {
        this.albums = albums;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public AddAlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        return new AddAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AddAlbumViewHolder holder, int position) {
        Album album = albums.get(position);
        holder.albumNameCard.setText(album.getAlbumName());
        holder.editorialCard.setText(album.getEditorial());
        holder.totalCard.setText(Integer.toString(album.getTotal()));
        Picasso.get().load(album.getPicture()).fit().centerCrop().into(holder.pictureCard);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public class AddAlbumViewHolder extends RecyclerView.ViewHolder {

        private ImageView pictureCard;
        private TextView albumNameCard;
        private TextView editorialCard;
        private TextView totalCard;

        public AddAlbumViewHolder(View itemView) {
            super(itemView);

            pictureCard = (ImageView) itemView.findViewById(R.id.portada_add);
            albumNameCard = (TextView) itemView.findViewById(R.id.nameAlbum_add);
            editorialCard = (TextView) itemView.findViewById(R.id.editorial_add);
            totalCard = (TextView) itemView.findViewById(R.id.total_add);


        }
    }
}
