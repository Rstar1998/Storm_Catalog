package com.example.stormcatalog;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Storm_List_Adapter extends RecyclerView.Adapter<Storm_List_Adapter.ExampleViewHolder>{
    private ArrayList<String> mexampleList;
    private Context context;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public TextView title;

        public CardView layout;

        public ImageView image_view;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.stormtitle);
            image_view = itemView.findViewById(R.id.si_imageView);
            layout=itemView.findViewById(R.id.cardview2);
            //textview.setBackgroundDrawable( getResources().getDrawable(R.drawable.cellborder) );

        }
    }

    public Storm_List_Adapter(Context context, ArrayList<String> exampleList ){
        mexampleList=exampleList;
        this.context=context;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.storm_item,parent,false);
        ExampleViewHolder evh =new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        final String currentItem=mexampleList.get(position);

        String storm_name = currentItem.split("-")[0];
        String storm_year = currentItem.split("-")[1];

        holder.title.setText("Storm ("+storm_name+") Season : "+storm_year);



        String url = "https://zoom.earth/assets/images/storms/2048/"+storm_year+"/"+storm_name+".jpg";
        System.out.println(url);
        Picasso.get().load(url).fit()
                .centerCrop()
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(holder.image_view);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, StormActivity.class);

                intent.putExtra("storm_id",currentItem);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);


            }
        });




    }

    @Override
    public int getItemCount() {
        return mexampleList.size();
    }
}

