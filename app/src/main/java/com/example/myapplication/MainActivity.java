package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final String TAG = MainActivity.class.getCanonicalName();

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        presenter.requestBackendData();
    }

    @Override
    public void onDataRequested() {
        Log.i("kcisikutya", "onDataRequested() called");
    }

    @Override
    public void showLoading() {
        //todo

    }
}