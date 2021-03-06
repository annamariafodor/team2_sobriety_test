package com.example.myapplication.ui.home.result.fragment;

import android.view.View;

import androidx.navigation.NavController;

import com.example.myapplication.mvp.BasePresenter;
import com.example.myapplication.mvp.BaseView;

import java.util.Date;

public interface ResultContract{

    interface View extends BaseView {

        void showResult(double res);

        void initializeSeekBar(Date elsoDatum, long hours, double res);

        void showTaxiButton(NavController navController);
    }

    abstract class Presenter extends BasePresenter<ResultContract.View> {

        public Presenter(ResultContract.View view) {
            super(view);
        }



        public abstract void getPersonalInformation();

        public abstract void getDrinks();

        public abstract void removeDrinks();
    }
}
