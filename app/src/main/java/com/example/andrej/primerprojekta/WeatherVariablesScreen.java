package com.example.andrej.primerprojekta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WeatherVariablesScreen extends AppCompatActivity {
    //Screen for picking Weather Variables for selected Country
    ArrayList<String> weatherVars;
    ListView listVar;
    ArrayList<String> selectedItems;
    Bundle rootBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_variables_screen);
        rootBundle=getIntent().getBundleExtra("bundle");
        selectedItems=new ArrayList<>();
        listVar= (ListView) findViewById(R.id.VariablesList);
        weatherVars=new WeatherVars().get();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.weather_var_item,R.id.WeatherVarItem,weatherVars);
        listVar.setAdapter(adapter);

        listVar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               final TextView weatherVarItem= (TextView) view.findViewById(R.id.WeatherVarItem);
                final CheckBox check= (CheckBox) view.findViewById(R.id.chkBox);
                if(check.isChecked()){
                    //remove selected item from ArrayList
                    check.setChecked(false);
                    selectedItems.remove(weatherVarItem.getText().toString());
                }
                else {
                    //add selected item to ArrayList
                    selectedItems.add(weatherVarItem.getText().toString());
                    check.setChecked(true);
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //error handling if user doesn't select any vars
        if(selectedItems.size()==0){
            Toast toast=Toast.makeText(this,"No variables selected",Toast.LENGTH_LONG);
            toast.show();
            return super.onOptionsItemSelected(item);
        }
        //pass coords ,selected variables and go to next activity
        Intent intent=new Intent(this,WeatherScreen.class);
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("items",selectedItems);
        bundle.putDoubleArray("coords",rootBundle.getDoubleArray("coords"));
        intent.putExtra("bundle",bundle);
        startActivity(intent);
        return super.onOptionsItemSelected(item);

    }
}
