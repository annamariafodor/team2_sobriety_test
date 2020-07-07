package com.example.myapplication.ui.home.taxi;

import java.util.List;

public interface IFireBaseLoadDone {

    void onFirebaseLoadSuccess(List<Taxi> taxiList);
    void onFirebaseLoadFailed(String message );
}
