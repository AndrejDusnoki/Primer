package com.example.andrej.primerprojekta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.andrej.primerprojekta.Utils.ParseCoords;
import com.example.andrej.primerprojekta.Utils.checkNetwork;

public class MainActivity extends AppCompatActivity implements CompletionHandler {
    //User chooses country from list
    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    RecyclerAdapter adapter;
    checkNetwork check=new checkNetwork();
    EditText searchBar;
    Button btnSelect;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress= (ProgressBar) findViewById(R.id.progressBar);
        recyclerView= (RecyclerView) findViewById(R.id.recCountryChoose);
        manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        btnSelect= (Button) findViewById(R.id.SelectButton);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user input is correct pass country coordinates to next activity, and start next activity
                if(adapter.searchItems.get(0).equals(searchBar.getText().toString())){
                    Bundle bundle=new Bundle();
                    bundle.putDoubleArray("coords",new ParseCoords().parse(adapter.items,searchBar.getText().toString()));
                    Intent intent=new Intent(getApplicationContext(),WeatherVariablesScreen.class);
                    intent.putExtra("bundle",bundle);
                    startActivity(intent);
                }
                //if User inputs non existing country
                else {
                    Toast toast =Toast.makeText(getApplicationContext(),"Selected country doesn't exist",Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });
        check.isNetworkOnline(this,this);


    }

    @Override
    public void onCompletionFailed() {
        this.finish();
    }
    @Override
    public void onCompletionSuccsess(String s) {
        //if there is network connection continue acitivty
        progress.setVisibility(View.GONE);
        btnSelect= (Button) findViewById(R.id.SelectButton);
        searchBar= (EditText) findViewById(R.id.SearchBar);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        adapter=new RecyclerAdapter(this,searchBar);
        recyclerView.setAdapter(adapter);
    }

}
