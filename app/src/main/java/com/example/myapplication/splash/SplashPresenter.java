package com.example.myapplication.splash;

import android.os.CountDownTimer;

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
                    view.showMainScreen();
                } else {
                    view.showLoginScreen();
                }
            }
        }.start();
    }
}
