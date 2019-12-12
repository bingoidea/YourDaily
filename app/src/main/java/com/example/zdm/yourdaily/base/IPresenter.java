package com.example.zdm.yourdaily.base;

/**
 * Created by ZDM on 2019/12/12.
 */
public interface IPresenter<T extends IView> {
    void attachView(T view);
    void detachView();
}
