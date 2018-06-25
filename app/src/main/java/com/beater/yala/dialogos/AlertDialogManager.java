package com.beater.yala.dialogos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by JuanCarlos on 24/06/2018.
 */
public class AlertDialogManager {

    public void showAlertDialog(Context context, String title, String message, Boolean status){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        //ASIGNAR TÍTULO DEL DIALOGO
        alertDialog.setTitle(title);

        alertDialog.setMessage(message);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
        }
}
