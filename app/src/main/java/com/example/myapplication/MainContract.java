package com.example.myapplication;

import com.example.myapplication.mvp.BasePresenter;
import com.example.myapplication.mvp.BaseView;

/**
 * Class comment here
 *
 * @author Arnold Baroti
 * @since 06/22/2020
 */
public interface MainContract {

    interface View extends BaseView {

        void onDataRequested();
    }

    abstract class Presenter extends BasePresenter<MainContract.View> {

        public Presenter(View view) {
            super(view);
        }

        public abstract void requestBackendData();
    }

}
