package com.example.andrej.primerprojekta;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.andrej.primerprojekta.Utils.parseVars;

import java.util.ArrayList;

public class WeatherScreen extends AppCompatActivity implements WeatherVarHandler {
    private Bundle bundle;
    private double[]coords;
    private ArrayList<String>items;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_variables_screen);
        bundle=getIntent().getBundleExtra("bundle");
        items=bundle.getStringArrayList("items");
        list= (ListView) findViewById(R.id.VariablesList);
        coords=bundle.getDoubleArray("coords");
        new parseVars().parse(coords,this,items,this);
    }
    @Override
    public void returnVars(ArrayList<String> s) {
        //On successful response, bind data to adapter
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,s);
        list.setAdapter(arrayAdapter);

    }
}
