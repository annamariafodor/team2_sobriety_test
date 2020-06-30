package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ListScreen extends AppCompatActivity {

    RecyclerView myRecyclerView;
    MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_screen);


        myRecyclerView = findViewById(R.id.recyclerView);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter = new MyAdapter(this,getMyList());
        myRecyclerView.setAdapter(myAdapter);
    }

    private ArrayList<Model> getMyList(){

        ArrayList<Model> models = new ArrayList<>();

        Model m = new Model();
        m.setQuantity("200 ml");
        m.setDegree("5 %");
        m.setDate("2020.06.20");
        m.setTime("23:00");
        models.add(m);

        m = new Model();
        m.setQuantity("500 ml");
        m.setDegree("4 %");
        m.setDate("2020.06.20");
        m.setTime("23:15");
        models.add(m);

        return  models;
    }
}