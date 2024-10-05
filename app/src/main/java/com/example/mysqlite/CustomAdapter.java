package com.example.mysqlite;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private ArrayList id, title, description, stock;
    int position;
    Activity act;

    CustomAdapter(Activity act, Context context, ArrayList id, ArrayList title, ArrayList description, ArrayList stock){
        this.act = act;
        this.context = context;
        this.id = id;
        this.title = title;
        this.description = description;
        this.stock = stock;
    }



    @NonNull
    @Override

    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(context);
        View V = inflate.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        this.position = position;
        holder.Id.setText(String.valueOf(id.get(position)));
        holder.title.setText(String.valueOf(title.get(position)));
        holder.description.setText(String.valueOf(description.get(position)));
        holder.stock.setText(String.valueOf(stock.get(position)));


        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(act, UpdateItems.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("title", String.valueOf(title.get(position)));
                intent.putExtra("description", String.valueOf(description.get(position)));
                intent.putExtra("stock", String.valueOf(stock.get(position)));
                act.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Id, title, description, stock;
        ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Id = itemView.findViewById(R.id.idRow0);
            title = itemView.findViewById(R.id.titleRow1);
            description = itemView.findViewById(R.id.descriptionRow2);
            stock = itemView.findViewById(R.id.stock);

            mainLayout = itemView.findViewById(R.id.mainLayout);

        }
    }
}
