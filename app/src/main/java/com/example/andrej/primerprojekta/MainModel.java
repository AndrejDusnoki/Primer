package com.example.andrej.primerprojekta;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.andrej.primerprojekta.Utils.sendHttpRequest;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by andrej on 28.9.16..
 */
public class MainModel implements CompletionHandler {
    RecyclerAdapter adapter;
    sendHttpRequest request;


     MainModel(RecyclerAdapter adapter, String url){
         this.adapter=adapter;
         request=new sendHttpRequest();
         request.send(url,this);
     }


    @Override
    public void onCompletionFailed() {
        //If HttpRequest failed notify user
        Toast toast=Toast.makeText(adapter.context,"Server error",Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    public void onCompletionSuccsess(String s) {
        //On complete update adapter with response data
        try {
            JSONArray countries=new JSONArray(s);
            adapter.items=countries;
            adapter.notifyDataSetChanged();
            Log.d("JSON ARRAY",countries.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
