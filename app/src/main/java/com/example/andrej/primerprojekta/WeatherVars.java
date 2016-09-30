package com.example.andrej.primerprojekta;

import java.util.ArrayList;

/**
 * Created by andrej on 30.9.16..
 */
public class WeatherVars {
    ArrayList<String> WeatherVars=new ArrayList<>();
    public ArrayList<String> get(){
        WeatherVars.add("summary");
        WeatherVars.add("nearestStormDistance");
        WeatherVars.add("precipIntensity");
        WeatherVars.add("precipProbability");
        WeatherVars.add("precipType");
        WeatherVars.add("temperature");
        WeatherVars.add("apparentTemperature");
        WeatherVars.add("dewPoint");
        WeatherVars.add("humidity");
        WeatherVars.add("windSpeed");
        WeatherVars.add("windBearing");
        WeatherVars.add("cloudCover");
        WeatherVars.add("pressure");
        return WeatherVars;
    }
}
