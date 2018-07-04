package com.beater.yala.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beater.yala.R;
import com.beater.yala.fragments.AlbumDetailsFragment;
import com.beater.yala.model.Album;
//import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by JuanCarlos on 4/06/2018.
 */
public class AlbumAdapterRecyclerView  extends RecyclerView.Adapter<AlbumAdapterRecyclerView.AlbumViewHolder>
                    implements View.OnClickListener{

    private ArrayList<Album> albums;
    private int resource;
    private Activity activity;
    private AlbumDetailsFragment albumFragment;
    private View.OnClickListener listener;

    public AlbumAdapterRecyclerView(ArrayList<Album> albums, int resource, Activity activity) {
        this.albums = albums;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        view.setOnClickListener(this);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AlbumViewHolder holder, int position) {
        final Album album = albums.get(position);
        albumFragment = new AlbumDetailsFragment();

        holder.albumNameCard.setText(album.getAlbumName());
        holder.editorialCard.setText(album.getEditorial());
        holder.totalCard.setText(Integer.toString(album.getTotal()));
        holder.completadasCard.setText(Integer.toString(album.getCompletadas()));
        holder.repetidasCard.setText(Integer.toString(album.getRepetidas()));
        Picasso.get().load(album.getPicture()).fit().centerCrop().into(holder.pictureCard);

    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener!= null){
            listener.onClick(v);
        }
    }

    public class AlbumViewHolder extends RecyclerView.ViewHolder{

        private ImageView pictureCard;
        private TextView albumNameCard;
        private TextView editorialCard;
        private TextView totalCard;
        private TextView completadasCard;
        private TextView repetidasCard;

        public AlbumViewHolder(View itemView) {
            super(itemView);

            pictureCard     = (ImageView) itemView.findViewById(R.id.pictureCard);
            albumNameCard   = (TextView) itemView.findViewById(R.id.albumNameCard);
            editorialCard   = (TextView) itemView.findViewById(R.id.editorialCard);
            totalCard       = (TextView) itemView.findViewById(R.id.totalCard);
            completadasCard = (TextView) itemView.findViewById(R.id.completadasCard);
            repetidasCard   = (TextView) itemView.findViewById(R.id.repetidasCard);

        }
    }
}
