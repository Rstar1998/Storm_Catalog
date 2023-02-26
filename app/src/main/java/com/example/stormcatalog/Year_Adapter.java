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
        import androidx.core.content.ContextCompat;
        import androidx.recyclerview.widget.RecyclerView;



        import java.util.ArrayList;

public class Year_Adapter extends RecyclerView.Adapter<Year_Adapter.ExampleViewHolder>{
    private ArrayList<Integer> mexampleList;
    private Context context;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public TextView title;

        public CardView layout;

        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            layout=itemView.findViewById(R.id.cardview);
            //textview.setBackgroundDrawable( getResources().getDrawable(R.drawable.cellborder) );

        }
    }

    public Year_Adapter(Context context, ArrayList<Integer> exampleList ){
        mexampleList=exampleList;
        this.context=context;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.year_item,parent,false);
        ExampleViewHolder evh =new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        final Integer currentItem=mexampleList.get(position);
        System.out.println(currentItem);
        holder.title.setText(Integer.toString(currentItem));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, StormsThatYear.class);

                intent.putExtra("year",currentItem);
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

