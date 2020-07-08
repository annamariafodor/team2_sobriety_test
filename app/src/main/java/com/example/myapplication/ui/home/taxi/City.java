package com.example.myapplication.ui.home.taxi;

import java.util.ArrayList;
import java.util.List;

public class City {

    private List<Taxi>  taxiList = new ArrayList<>();
    private String name;

    public List<Taxi> getTaxiList() {
        return taxiList;
    }


    public void setTaxiList(List<Taxi> taxiList) {
        this.taxiList = taxiList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return
                "taxiList=" + taxiList +
                ", name='" + name + '\'' +
                '}';
    }

    public City(String name) {
        this.name = name;
    }


    public void addTaxi (Taxi taxi){
        taxiList.add(taxi);
    }
}
