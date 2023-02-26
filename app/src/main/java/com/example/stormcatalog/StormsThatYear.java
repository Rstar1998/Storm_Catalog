package com.example.stormcatalog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.view.Window;
import android.view.WindowManager;

public class StormsThatYear extends AppCompatActivity {
    ProgressBar progressBar;
    ArrayList<String> list;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storms_that_year);
        progressBar=findViewById(R.id.progressBar);
        list = new ArrayList<String>();
        String year = String.valueOf(getIntent().getIntExtra("year",2000));



        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://zoom.earth/data/storms/?date="+year+"-01-01T00:00Z&to="+year+"-12-20T00:00Z")
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

                    StormsThatYear.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            try {
                                // get JSONObject from JSON file
                                JSONObject obj = new JSONObject(myResponse);
                                System.out.println(myResponse);

                                JSONArray userArray = obj.getJSONArray("storms");


                                for (int i = 0; i < userArray.length(); i++) {


                                    String storm_id = userArray.getString(i).trim();
                                    System.out.println(storm_id);
                                    list.add(storm_id );


                                }
                                progressBar.setVisibility(View.INVISIBLE);
                                mRecyclerView=findViewById(R.id.recycle1);
                                mRecyclerView.setHasFixedSize(true);
                                mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                mViewAdapter =new Storm_List_Adapter(getApplicationContext(),list);

                                mRecyclerView.setLayoutManager(mLayoutManager);
                                mRecyclerView.setAdapter(mViewAdapter);


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