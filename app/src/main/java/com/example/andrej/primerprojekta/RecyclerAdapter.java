package com.example.andrej.primerprojekta;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by andrej on 28.9.16..
 */
public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    //Adapter for choosing country

public Context context;
private MainModel model;
private EditText searchBar;
JSONArray items=new JSONArray();
ArrayList <String> searchItems=new ArrayList<>();
RecyclerAdapter adapter=this;


    public static class ViewHolder extends RecyclerView.ViewHolder {

    public TextView mTextView;
    public ViewHolder(View v) {

        super(v);
        mTextView= (TextView) v.findViewById(R.id.tvItemName);
    }
}
    public RecyclerAdapter(Context context, EditText search){

        searchBar=search;
        searchBar.addTextChangedListener(SearchWatcher);
        model=new MainModel(this,"https://restcountries.eu/rest/v1/region/europe");
        this.context=context;
    }
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_holder, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerAdapter.ViewHolder holder, final int position) {
        //If searchBar is empty use non filtered data
            if(searchBar.getText().toString().equals("")){
                try {
                    String item=items.getJSONObject(position).getString("name");
                    holder.mTextView.setText(item);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        else if(searchItems.size()>0){
                holder.mTextView.setText(searchItems.get(position));
            }
        //Listener for selected item, it fills the search
        View.OnClickListener itemSelected=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchBar.getText().length()<=0){
                    try {
                        searchBar.setText(items.getJSONObject(position).getString("name"));
                        searchBar.setSelection(searchBar.getText().length());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    searchBar.setText(searchItems.get(position));
                }
            }
        };
        holder.mTextView.setOnClickListener(itemSelected);
    }
    @Override
    public int getItemCount() {
         if(searchItems.size()>0){
            return searchItems.size();
        }
        return items.length();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    TextWatcher SearchWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
            //On text change filter the results using Users input
            searchItems.clear();
            String item="";
            for(int i=0;i<items.length();i++){
                try {
                     item=items.getJSONObject(i).getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (item.contains(editable)){
                    searchItems.add(item);
                    notifyDataSetChanged();
                }
            }

        }
    };

}
