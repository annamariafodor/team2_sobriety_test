package com.example.myapplication.ui.splash;

import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashPresenter extends SplashContract.Presenter {
    public SplashPresenter(SplashContract.View view) {
        super(view);
    }
    public FirebaseAuth mAuth;

    @Override
    public void checkNextActivity() {
        mAuth = FirebaseAuth.getInstance();
        new CountDownTimer(2000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Log.d("Return","onfinish");
                if (view == null) {
                    Log.d("RETURN","return");
                    return;
                }
                FirebaseUser mUser = mAuth.getCurrentUser();
                if (mUser != null) {
                    Log.d("RETURN", "showMainScreen");
                    view.showMainScreen();
                } else {
                    Log.d("RETURN", "showLoginScreen");
                    view.showLoginScreen();
                }

            }
        }.start();
    }

}
