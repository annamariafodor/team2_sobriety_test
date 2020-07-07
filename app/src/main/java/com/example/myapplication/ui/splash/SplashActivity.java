package com.example.myapplication.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.ui.authentication.AuthenticationActivity;
import com.example.myapplication.ui.home.MainActivity;
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

    }

    @Override
    public void showLoginScreen() {
        startActivity(new Intent(this, AuthenticationActivity.class));

    }
}