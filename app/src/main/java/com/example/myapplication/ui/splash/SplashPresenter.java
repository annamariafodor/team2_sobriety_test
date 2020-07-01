package com.example.myapplication.ui.splash;

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
                //TODO: if the user is not logged in
                if (true) {
                    //view.showLoginScreen();
                    view.showRegistScreen();

                } //TODO: if the user is  logged in
                 else {
                    view.showMainScreen();
                }
            }
        }.start();
    }
}
