package com.beater.yala.dialogos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.beater.yala.R;

/**
 * Created by JuanCarlos on 14/06/2018.
 */
public class SolicitudAlbum extends DialogFragment{

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.addalbum_dialog, null))
            .setTitle("Solicitar Álbum");

        return builder.create();
    }
}
