package com.example.zdm.yourdaily.base;

/**
 * Created by ZDM on 2019/12/12.
 */
public abstract class BasePresenter<T extends IView> implements IPresenter<T> {

    protected T mView;

    protected BaseActivity mActivity;

    protected BasePresenter(BaseActivity activity) {
        mActivity = activity;
    }

    /**
     * Presenter持有View的引用
     */
    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    /**
     * Presenter和View解绑
     */
    @Override
    public void detachView() {
        this.mView = null;
    }

}
