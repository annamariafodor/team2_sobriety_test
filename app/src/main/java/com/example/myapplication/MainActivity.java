package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final String TAG = MainActivity.class.getCanonicalName();

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        presenter = new MainPresenter(this);

        presenter.requestBackendData();
    }

    @Override
    public void onDataRequested() {
        Log.i("kcisikutya", "onDataRequested() called");
    }

    @Override
    public void showLoading() {
            //todo: add loading mechanism
        //todo

    }
}