package com.example.myapplication;

public class Model {
    private String quantity,degree,date,time;

    public Model() {
    }

    public Model(String quantity, String degree, String date, String time) {
        this.quantity = quantity;
        this.degree = degree;
        this.date = date;
        this.time = time;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Model{" +
                "quantity='" + quantity + '\'' +
                ", degree='" + degree + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
