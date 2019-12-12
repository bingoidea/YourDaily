package com.example.zdm.yourdaily.ui.pinlock;

import android.os.Build;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

import com.example.zdm.yourdaily.Constant.Constant;
import com.example.zdm.yourdaily.base.BaseActivity;
import com.example.zdm.yourdaily.base.BasePresenter;
import com.example.zdm.yourdaily.util.SPUtils;

/**
 * Created by ZDM on 2019/12/12.
 */
public class PinlockPresenter extends BasePresenter<PinlockContract.View> implements PinlockContract.Presenter {

    private SPUtils sp;
    private FingerprintManagerCompat mFingerprintManagerCompat;

    protected PinlockPresenter(BaseActivity activity) {
        super(activity);
        sp = new SPUtils(activity, Constant.SP_PIN);
        mFingerprintManagerCompat = FingerprintManagerCompat.from(activity);
    }

    @Override
    public void verifyPin(String pin) {
        String pwd = sp.getString(Constant.SP_PIN_PASSWORD, "");
        if (pwd.equals(pin)) {
            mView.verifySuccess();
        } else {
            mView.verifyFail();
        }
    }

    @Override
    public void savePin(String pin) {
        sp.putString(Constant.SP_PIN_PASSWORD, pin);
    }

    @Override
    public void verifyFingerprint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mFingerprintManagerCompat.authenticate(null, 0, null, new FingerprintManagerCompat.AuthenticationCallback() {
                @Override
                public void onAuthenticationError(int errMsgId, CharSequence errString) {
                    super.onAuthenticationError(errMsgId, errString);
                    mView.verifyFail();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    mView.verifyFail();
                }

                @Override
                public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    mView.verifySuccess();
                }

                @Override
                public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                    super.onAuthenticationHelp(helpMsgId, helpString);
                }
            }, null);
        }
    }

}
