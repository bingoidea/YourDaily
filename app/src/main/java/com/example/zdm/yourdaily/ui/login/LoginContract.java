package com.example.zdm.yourdaily.ui.login;

import com.example.zdm.yourdaily.base.IPresenter;
import com.example.zdm.yourdaily.base.IView;

/**
 * Created by ZDM on 2019/12/12.
 */
public interface LoginContract {

    interface View extends IView {

        void gotoMain();

        void loginFailed();

    }

    interface Presenter extends IPresenter<View> {

        void login(String account, String password);

        void addAccount(String account, String password);

    }

}
