package com.example.myapplication.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.myapplication.ui.authentication.AuthenticationActivity;
import com.example.myapplication.ui.home.MainActivity;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity implements SplashContract.View {

    private SplashContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        presenter = new SplashPresenter(this);
        presenter.checkNextActivity();
        Log.d("Return","this");
    }


    @Override
    public void showLoading() {

    }


    @Override
    public void showLoginScreen() {
        Log.d("RETURN","authentication");
        startActivity(new Intent(this, AuthenticationActivity.class));

    }

    @Override
    public void showMainScreen() {
        Log.d("RETURN","mainscreen");
        startActivity(new Intent(this, MainActivity.class));
    }





}
