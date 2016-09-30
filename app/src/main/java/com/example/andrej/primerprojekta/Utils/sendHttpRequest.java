package com.example.andrej.primerprojekta.Utils;

import android.os.AsyncTask;

import com.example.andrej.primerprojekta.CompletionHandler;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by andrej on 29.9.16..
 */
public class sendHttpRequest {
    String stringResponse=" ";
    public CompletionHandler handler;
    public void send (final String url, final CompletionHandler handler){
        this.handler=handler;
        MyTask task=new MyTask();
        task.execute(url);
    }

    private class MyTask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(stringResponse!=null){
                handler.onCompletionSuccsess(stringResponse);
            }
            else {
                handler.onCompletionFailed();
            }

        }

        @Override
        protected Void doInBackground(String... voids) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(voids[0])
                    .build();
            try {
                Response response = client.newCall(request).execute();
                stringResponse=response.body().string();
            }
            catch (IOException e){
              stringResponse=null;
            }
            return null;
        }
    }

}
