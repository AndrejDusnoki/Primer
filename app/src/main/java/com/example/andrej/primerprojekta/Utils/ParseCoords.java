package com.example.andrej.primerprojekta.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by andrej on 30.9.16..
 */
public class ParseCoords {

    public double [] parse(JSONArray items,String name){
        double []LatLong=new double[2];
             for (int i=0;i<items.length();i++){
                 try {

                     JSONObject country=items.getJSONObject(i);
                     if(country.getString("name").equals(name)){
                        JSONArray latlng= country.getJSONArray("latlng");
                         LatLong[0]=(latlng.getDouble(0));
                         LatLong[1]=(latlng.getDouble(1));
                     }
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }
        return LatLong;
    }
}
