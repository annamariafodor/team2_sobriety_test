package com.example.myapplication.ui.splash;

import com.example.myapplication.mvp.BasePresenter;
import com.example.myapplication.mvp.BaseView;

public interface SplashContract {

    interface View extends BaseView {
        void showMainScreen();
        void showLoginScreen();
        void showRegistScreen();




    }

    abstract class Presenter extends BasePresenter<SplashContract.View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void checkNextActivity();

    }
}
