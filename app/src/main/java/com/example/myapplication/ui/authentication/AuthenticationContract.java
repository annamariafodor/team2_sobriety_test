package com.example.myapplication.ui.authentication;

import com.example.myapplication.mvp.BasePresenter;
import com.example.myapplication.mvp.BaseView;

public interface AuthenticationContract {
    public interface View extends BaseView {
    }

    public class Presenter extends BasePresenter<AuthenticationContract.View> {
        public Presenter(View view) {
            super(view);
        }
    }
}
