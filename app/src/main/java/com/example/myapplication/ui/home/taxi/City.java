package com.example.myapplication.ui.home.taxi;

import java.util.ArrayList;
import java.util.List;

public class City {

    private List<Taxi>  taxiList = new ArrayList<>();
    private String name;

    public City(String name) {
        this.name = name;
    }

    public void addTaxi (Taxi taxi){
        taxiList.add(taxi);
    }
}
