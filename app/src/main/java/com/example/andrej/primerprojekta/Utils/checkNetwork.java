package com.example.andrej.primerprojekta.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import com.example.andrej.primerprojekta.CompletionHandler;
import com.example.andrej.primerprojekta.MainActivity;

/**
 * Created by andrej on 28.9.16..
 */
public class checkNetwork  extends BroadcastReceiver{
    CompletionHandler handler;
    //Checks if user connected to internet
    public checkNetwork(){

    }
    public void isNetworkOnline(final Context context, final CompletionHandler handler) {
        this.handler=handler;
            ConnectivityManager cm =
                    (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork!=null) {
                //if connected trigger completion handler
                handler.onCompletionSuccsess(null);
            }else {
                //if not connected bring up dialog with two options(Exit application or go to WIFI settings)
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Internet connection required")
                        .setTitle("Warning");
                builder.setPositiveButton("Go to WIFI settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        handler.onCompletionFailed();
                        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                });
                builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(homeIntent);
                    }
                });
                builder.show();
            }

    }
    @Override
    //Listens to wfi state change, if connected start activity
    public void onReceive(Context context, Intent inte) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        Intent intent=new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(activeNetwork!=null) {
            context.startActivity(intent);
        }
    }

}
