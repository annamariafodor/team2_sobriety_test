package com.example.myapplication;

/**
 * Class comment here
 *
 * @author Arnold Baroti
 * @since 06/22/2020
 */
public class MainPresenter extends MainContract.Presenter {

    public MainPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    public void requestBackendData() {
        if (view != null) {
            view.onDataRequested();
        }
    }

}
