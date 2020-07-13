package com.example.myapplication.ui.home.taxi;

import java.util.List;

public interface IFireBaseLoadDone {

    void onFirebaseLoadSuccess(List<City> cities);
    void onFirebaseLoadFailed(String message );
}
