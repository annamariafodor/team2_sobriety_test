package com.example.myapplication.ui.splash;

import android.os.CountDownTimer;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;

public class SplashPresenter extends SplashContract.Presenter {

    public SplashPresenter(SplashContract.View view) {
        super(view);
    }

    @Override
    public void checkNextActivity() {
        new CountDownTimer(2000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }



            @Override
            public void onFinish() {
                if (view == null) {
                    return;
                }
                if (true) {
                    view.showLoginScreen();
                } else {
                    view.showMainScreen();
                }
            }
        }.start();
    }
}
