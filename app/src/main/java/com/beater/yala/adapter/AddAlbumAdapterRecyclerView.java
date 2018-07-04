package com.beater.yala.adapter;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.beater.yala.R;
import com.beater.yala.data.SessionManagement;
import com.beater.yala.model.Album;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JuanCarlos on 18/06/2018.
 */
public class AddAlbumAdapterRecyclerView
        extends RecyclerView.Adapter<AddAlbumAdapterRecyclerView.AddAlbumViewHolder>
        implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

    private ArrayList<Album> albums;
    private int resource;
    private Activity activity;
    private View.OnClickListener listener;
    SessionManagement session;
    RequestQueue request;
    JsonObjectRequest jsonObject;


    public AddAlbumAdapterRecyclerView(ArrayList<Album> albums, int resource, Activity activity) {
        this.albums = albums;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public AddAlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        view.setOnClickListener(this);
        return new AddAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AddAlbumViewHolder holder, int position) {
        session = new SessionManagement(activity);
        request = Volley.newRequestQueue(activity);
        final Album album = albums.get(position);
        holder.albumNameCard.setText(album.getAlbumName());
        holder.editorialCard.setText(album.getEditorial());
        holder.totalCard.setText(Integer.toString(album.getTotal()));
        Picasso.get().load(album.getPicture()).fit().centerCrop().into(holder.pictureCard);

        holder.btnAddAlbumCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadService(album);

            }
        });

    }

    public void loadService(Album album){
        HashMap<String,String> user = session.getUserDetails();

        String url = "https://juanhb.000webhostapp.com/Insertar_Album_Coleccion.php?" +
                        "idAlbum="+ album.getIdAlbum() +
                        "&idColeccionista="+ user.get(SessionManagement.KEY_ID)+
                        "&faltantes=" + album.getTotal();

        jsonObject = new JsonObjectRequest(Request.Method.GET,url,null, this, this);
        request.add(jsonObject);
    }

    @Override
    public void onResponse(JSONObject response) {

        if(response.optInt("estado") == 1){
            //Snackbar.make(R.layout.fragment_add_album,"Álbum agregado correctamente",Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public int getItemCount() {
        return albums.size();
    }


    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!= null){
            listener.onClick(view);
        }
    }

    public class AddAlbumViewHolder extends RecyclerView.ViewHolder {

        private ImageView pictureCard;
        private TextView albumNameCard;
        private TextView editorialCard;
        private TextView totalCard;
        private Button btnAddAlbumCard;

        public AddAlbumViewHolder(View itemView) {
            super(itemView);

            pictureCard = (ImageView) itemView.findViewById(R.id.portada_add);
            albumNameCard = (TextView) itemView.findViewById(R.id.nameAlbum_add);
            editorialCard = (TextView) itemView.findViewById(R.id.editorial_add);
            totalCard = (TextView) itemView.findViewById(R.id.total_add);
            btnAddAlbumCard = (Button) itemView.findViewById(R.id.btn_addAlbum);

        }
    }
}
