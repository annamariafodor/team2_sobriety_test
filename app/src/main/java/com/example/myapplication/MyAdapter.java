package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.home.MainContract;
import com.example.myapplication.ui.home.listScreen.ListScreenFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row,null);

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
                listener.onItemClick(models.get(position),holder.getItemId());
            }
        });
    }


    public interface OnItemClickListener {
        void onItemClick(Model model, long id);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}


