package com.example.stormcatalog;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        String storm_id =  (String) getIntent().getStringExtra("storm_id");
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://zoom.earth/data/storms/?id="+storm_id)
                .method("GET", null)
                .build();



        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    System.out.println(myResponse);

                    ChartActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                // get JSONObject from JSON file
                                JSONObject obj = new JSONObject(myResponse);
                                System.out.println(myResponse);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                                SimpleDateFormat date_col = new SimpleDateFormat("dd-MMM");



                                JSONArray js_list = obj.getJSONArray("track");
                                for(int i=0 ;i<js_list.length();i++){
                                    JSONObject track = js_list.getJSONObject(i);
                                    try {
                                        Date date = format.parse(track.getString("date"));
                                        //seriesData.add(new CustomDataEntry(date_col.format(date), track.getInt("wind"), track.getInt("pressure")));


                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                }





                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }


}