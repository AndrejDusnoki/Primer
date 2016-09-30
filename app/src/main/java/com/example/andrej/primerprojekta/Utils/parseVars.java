package com.example.andrej.primerprojekta.Utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.andrej.primerprojekta.CompletionHandler;
import com.example.andrej.primerprojekta.WeatherVarHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by andrej on 30.9.16..
 */
public class parseVars implements CompletionHandler{
    //extracts just the selected variables from response
    WeatherVarHandler hander;
    ArrayList<String> SelectedVars;
    ArrayList<String> returnVars;
    Context context;
    public void parse(double[] coords, WeatherVarHandler handler, ArrayList<String>SelectedVars, Context context){
        returnVars=new ArrayList<>();
        this.context=context;
        this.SelectedVars=SelectedVars;
        String lat= String.valueOf(coords[0]);
        String lon=String.valueOf(coords[1]);
        this.hander=handler;
        //Send request to server
        new sendHttpRequest().send("https://api.darksky.net/forecast/7c71f5d785dfe25ed42dbe2dd02b09c0/"+lat+","+lon,this);
    }
    @Override
    public void onCompletionFailed() {
        Toast toast=Toast.makeText(context,"Server error",Toast.LENGTH_SHORT);
        toast.show();
    }
    @Override
    public void onCompletionSuccsess(@Nullable String s) {
        //On request Succsessfull extract selected variables from response
        try {
            JSONObject response=new JSONObject(s);
            JSONObject currentWeather=response.getJSONObject("currently");
            Log.d("CURRENT WEATHER", String.valueOf(currentWeather));
            for (int i=0;i<SelectedVars.size();i++){
                String varText;
                String varValue;
                varText=SelectedVars.get(i);
                try{
                    varValue= String.valueOf(currentWeather.get(varText));
                    returnVars.add(varText+": "+varValue);
                }
                catch (JSONException e){
                    //If json response doesn't contain selected variable, notify user
                    Toast toast=Toast.makeText(context,"Variable "+varText+" could not be found",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            Log.d("VALUES + TEXT", SelectedVars.toString());
            //Notify WeatherScreen acitivty of succsess and pass variable values
            hander.returnVars(returnVars);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
