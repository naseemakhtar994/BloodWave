package com.example.bloodwave;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class Common {
    public void showDialog(final Activity activity,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message);
        builder.setTitle("Blood Wave");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void CreatAccount(final Activity activity,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message);
        builder.setTitle("Blood Wave");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(activity,DashBoardActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
