package com.example.myapplication.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    private SplashContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        presenter = new SplashPresenter(this);

        presenter.checkNextActivity();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void showMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void showLoginScreen() {

    }
}