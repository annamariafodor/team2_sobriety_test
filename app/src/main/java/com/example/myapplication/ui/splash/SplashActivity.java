package com.example.myapplication.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.ui.home.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.ui.authentication.AuthenticationActivity;
import com.example.myapplication.ui.authentication.login.LoginFragment;

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
        Log.d("Test","Show main");
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void showLoginScreen() {

        Log.d("Test","Show login");
        Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
        startActivity(intent);
    }

    @Override
    public void showRegistScreen() {

        Log.d("Test","Show register");
        Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
        startActivity(intent);
    }
}