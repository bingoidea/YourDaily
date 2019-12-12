package com.example.zdm.yourdaily.ui.pinlock;

import com.example.zdm.yourdaily.base.IPresenter;
import com.example.zdm.yourdaily.base.IView;

/**
 * Created by ZDM on 2019/12/12.
 */
public interface PinlockContract {

    interface View extends IView {
        void verifySuccess();
        void verifyFail();
    }

    interface Presenter extends IPresenter<View> {
        void verifyPin(String pin);
        void savePin(String pin);
        void verifyFingerprint();
    }

}
