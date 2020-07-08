package com.example.myapplication.ui.home.taxi;

public class Taxi {

    private String city, name,number;

    @Override
    public String toString() {
        return name + " - " + number ;
    }


    public String toString1() {
        return "Taxi{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }

    public Taxi(String city, String name, String number) {
        this.city = city;
        this.name = name;
        this.number = number;
    }

    public Taxi(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public Taxi() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
