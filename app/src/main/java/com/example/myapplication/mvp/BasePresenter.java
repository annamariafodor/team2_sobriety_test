package com.example.myapplication.mvp;

/**
 * Class comment here
 *
 * @author Arnold Baroti
 * @since 06/22/2020
 */
public abstract class BasePresenter<V extends BaseView> {

    protected V view;

    public BasePresenter(V view) {
        this.view = view;
    }
}
