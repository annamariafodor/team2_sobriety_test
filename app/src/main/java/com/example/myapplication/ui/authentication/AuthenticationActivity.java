package com.example.myapplication.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.example.myapplication.ui.authentication.register.RegisterFragment;
import com.google.android.material.navigation.NavigationView;

public class AuthenticationActivity extends AppCompatActivity implements AuthenticationContract.View {

    private AuthenticationContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        Log.d("Test","Authentication Activity OnCreate");
    }

    @Override
    public void showLoading() {

    }




}
