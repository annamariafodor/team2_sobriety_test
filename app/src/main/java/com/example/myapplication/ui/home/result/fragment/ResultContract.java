package com.example.myapplication.ui.home.result.fragment;

import com.example.myapplication.mvp.BasePresenter;
import com.example.myapplication.mvp.BaseView;

public interface ResultContract{

    interface View extends BaseView {


        void showResult(double res);
    }

    abstract class Presenter extends BasePresenter<ResultContract.View> {

        public Presenter(ResultContract.View view) {
            super(view);
        }


        public abstract void getPersonalInformation();

        public abstract void getDrinks();
    }
}
