package com.example.myapplication.ui.home.listScreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {


    Context c;
    ArrayList<Model> models;
    String key;
    private OnItemClickListener listener;

    public MyAdapter(Context c, ArrayList<Model> models) {
        this.c = c;
        this.models = models;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_row,null);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.quantity.setText(models.get(position).getQuantity());
        holder.degree.setText(models.get(position).getDegree());
        holder.date.setText(models.get(position).getDate());
        holder.time.setText(models.get(position).getTime());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.editItem(models.get(holder.getAdapterPosition()),holder.getAdapterPosition());

            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.deleteItem(models.get(holder.getAdapterPosition()),holder.getAdapterPosition());

            }
        });
    }


    public interface OnItemClickListener {
        void deleteItem(Model model, int position);
        void editItem(Model model, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public void remove(int position){
        models.remove(models.get(position));
    }

}


