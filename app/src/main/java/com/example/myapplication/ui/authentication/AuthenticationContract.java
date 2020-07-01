package com.example.myapplication.ui.authentication;

import com.example.myapplication.mvp.BasePresenter;
import com.example.myapplication.mvp.BaseView;
import com.example.myapplication.ui.splash.SplashContract;

public interface AuthenticationContract {

    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<AuthenticationContract.View> {

        public Presenter(View view) {
            super(view);
        }

        //public abstract void checkNextActivity();

    }
}
