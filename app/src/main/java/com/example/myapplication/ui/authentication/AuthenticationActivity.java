package com.example.myapplication.ui.authentication;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;
import com.example.myapplication.ui.authentication.login.LoginFragment;

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

    @Override
    public void onDataRequested() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}