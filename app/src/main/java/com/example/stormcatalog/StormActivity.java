package com.example.stormcatalog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

public class StormActivity extends AppCompatActivity {
    ImageView stormImage;
    TextView title ;
    TextView description ;
    TextView season;
    TextView place ;

    TableLayout stk;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storm);
        stormImage=findViewById(R.id.storm_image);
        title =findViewById(R.id.as_title);
        description =findViewById(R.id.as_desc);
        season=findViewById(R.id.as_season);
        place =findViewById(R.id.as_place);
        stk =  findViewById(R.id.table_main);

        String storm_id =  (String) getIntent().getStringExtra("storm_id");
        String storm_name = storm_id.split("-")[0];
        String storm_year = storm_id.split("-")[1];
        String url = "https://zoom.earth/assets/images/storms/2048/"+storm_year+"/"+storm_name+".jpg";
        System.out.println(url);
        Picasso.get().load(url).placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(stormImage);

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

                    StormActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            try {
                                // get JSONObject from JSON file
                                JSONObject obj = new JSONObject(myResponse);
                                System.out.println(myResponse);

                                title.setText("Storm Name : "+obj.getString("title").trim());
                                description.setText("Storm Description : "+obj.getString("description").trim());
                                season.setText("Storm Season : "+obj.getString("season").trim());
                                place.setText("Place : "+obj.getString("place").trim());

                                //table
                                // date time type wind(km/h) pressure(hPa)


                                TableRow tbrow0 = new TableRow(stk.getContext());
                                tbrow0.setPadding(5,5,5,5);
                                TextView tv0 = new TextView(stk.getContext());
                                tv0.setText("  Date  ");
                                tv0.setTextColor(Color.WHITE);
                                tv0.setGravity(Gravity.CENTER);
                                tv0.setPadding(5,5,5,5);
                                tbrow0.addView(tv0);
                                TextView tv1 = new TextView(stk.getContext());
                                tv1.setText("  Time  ");
                                tv1.setTextColor(Color.WHITE);
                                tv1.setGravity(Gravity.CENTER);
                                tv1.setPadding(5,5,5,5);
                                tbrow0.addView(tv1);
                                TextView tv2 = new TextView(stk.getContext());
                                tv2.setText(" Type ");
                                tv2.setTextColor(Color.WHITE);
                                tv2.setGravity(Gravity.CENTER);
                                tv2.setPadding(5,5,5,5);
                                tbrow0.addView(tv2);
                                TextView tv3 = new TextView(stk.getContext());
                                tv3.setText(" wind(km/h) ");
                                tv3.setTextColor(Color.WHITE);
                                tv3.setGravity(Gravity.CENTER);
                                tv3.setPadding(5,5,5,5);
                                tbrow0.addView(tv3);
                                TextView tv4 = new TextView(stk.getContext());
                                tv4.setText(" pressure(hPa) ");
                                tv4.setTextColor(Color.WHITE);
                                tv4.setGravity(Gravity.CENTER);
                                tv4.setPadding(5,5,5,5);
                                tbrow0.addView(tv4);
                                tbrow0.setBackgroundColor(getResources().getColor(R.color.B));
                                stk.addView(tbrow0);

                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                                SimpleDateFormat date_col = new SimpleDateFormat("dd-MMM");
                                SimpleDateFormat time_col = new SimpleDateFormat("HH:mm");


                                JSONArray js_list = obj.getJSONArray("track");
                                for(int i=0 ;i<js_list.length();i++){
                                    JSONObject track = js_list.getJSONObject(i);
                                    TableRow tbrow = new TableRow(stk.getContext());

                                    try {
                                        Date date = format.parse(track.getString("date"));
                                        TextView t1v = new TextView(stk.getContext());
                                        t1v.setText(date_col.format(date));
                                        t1v.setTextColor(Color.WHITE);
                                        t1v.setGravity(Gravity.CENTER);
                                        tbrow.addView(t1v);
                                        TextView t2v = new TextView(stk.getContext());
                                        t2v.setText(time_col.format(date));
                                        t2v.setTextColor(Color.WHITE);
                                        t2v.setGravity(Gravity.CENTER);
                                        tbrow.addView(t2v);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    TextView t3v = new TextView(stk.getContext());
                                    t3v.setText(track.getString("code"));
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        t3v.setTooltipText(track.getString("description"));
                                    }
                                    //t3v.setText(track.getString("description"));

                                    t3v.setTextColor(Color.WHITE);
                                    t3v.setGravity(Gravity.CENTER);
                                    tbrow.addView(t3v);
                                    TextView t4v = new TextView(stk.getContext());
                                    t4v.setText(String.valueOf(track.getInt("wind")));
                                    t4v.setTextColor(Color.WHITE);
                                    t4v.setGravity(Gravity.CENTER);
                                    tbrow.addView(t4v);
                                    TextView t5v = new TextView(stk.getContext());
                                    t5v.setText(String.valueOf(track.getInt("pressure")));
                                    t5v.setTextColor(Color.WHITE);
                                    t5v.setGravity(Gravity.CENTER);
                                    tbrow.addView(t5v);

                                    stk.addView(tbrow);
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