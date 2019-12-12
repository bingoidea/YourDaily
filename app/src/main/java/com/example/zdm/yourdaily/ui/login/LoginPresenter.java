package com.example.zdm.yourdaily.ui.login;

import com.example.zdm.yourdaily.Constant.Constant;
import com.example.zdm.yourdaily.base.BaseActivity;
import com.example.zdm.yourdaily.base.BaseApplication;
import com.example.zdm.yourdaily.base.BasePresenter;
import com.example.zdm.yourdaily.util.EncryptUtils;
import com.example.zdm.yourdaily.util.SPUtils;

/**
 * Created by ZDM on 2019/12/12.
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {

    private SPUtils sp;

    protected LoginPresenter(BaseActivity activity) {
        super(activity);
        sp = new SPUtils(activity, Constant.SP_ACCOUNT);
    }

    @Override
    public void login(String account, String password) {
        if (sp.getString(Constant.SP_ACCOUNT_NUMBER) != null
                && sp.getString(Constant.SP_ACCOUNT_PASSWORD) != null
                && sp.getString(Constant.SP_ACCOUNT_NUMBER).equals(account)
                && sp.getString(Constant.SP_ACCOUNT_PASSWORD).equals(password)) {
            BaseApplication.account = account;
            mView.gotoMain();
        } else {
            mView.loginFailed();
        }
    }

    @Override
    public void addAccount(String account, String password) {
        String encryptPwd = EncryptUtils.encryptMD5ToString(password);
        sp.putString(Constant.SP_ACCOUNT_NUMBER, account);
        sp.putString(Constant.SP_ACCOUNT_PASSWORD, encryptPwd);
        login(account, encryptPwd);
    }
}
