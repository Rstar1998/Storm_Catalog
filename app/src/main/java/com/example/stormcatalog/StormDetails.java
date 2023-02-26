package com.example.stormcatalog;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StormDetails extends AppCompatActivity {
    TextView text_details;
    TextView text_title;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storm_details);
        text_details=findViewById(R.id.txtquestion);
        String storm_id =  (String) getIntent().getStringExtra("storm_id");
        text_title=findViewById(R.id.asdstormtitle);
        text_title.setText("Storm ( "+storm_id.split("-")[0]+" )");



        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("https://zoom.earth/data/storms/?details="+storm_id)
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

                    StormDetails.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            try {
                                // get JSONObject from JSON file
                                JSONObject obj = new JSONObject(myResponse);
                                System.out.println(myResponse);

                                String detail_text=obj.getString("details").trim();
                                String plainText= Jsoup.parse(detail_text).text();
                                if(plainText.trim().length() == 0){
                                    text_details.setText("No details available");
                                }
                                else{
                                    text_details.setText(plainText);
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