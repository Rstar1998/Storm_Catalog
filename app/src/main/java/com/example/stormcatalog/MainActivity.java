package com.example.stormcatalog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<Integer> getNumbersUsingIntStreamIterate(int start, int limit) {

        return new ArrayList<Integer>(IntStream.iterate(start, i -> i - 1)
                .limit(limit)
                .boxed()
                .collect(Collectors.toList()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
//        getSupportActionBar().hide(); // hide the title bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);
        ArrayList<Integer> year_list = getNumbersUsingIntStreamIterate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.YEAR)-2000+1);
        mRecyclerView=findViewById(R.id.recycle);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mViewAdapter =new Year_Adapter(getApplicationContext(),year_list);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mViewAdapter);

    }
}