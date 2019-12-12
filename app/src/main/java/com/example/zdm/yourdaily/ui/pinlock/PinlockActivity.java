package com.example.zdm.yourdaily.ui.pinlock;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.example.zdm.yourdaily.R;
import com.example.zdm.yourdaily.base.BaseActivity;
import com.example.zdm.yourdaily.event.PinlockCancelEvent;
import com.example.zdm.yourdaily.util.RxBus;

import butterknife.BindView;

/**
 * Created by ZDM on 2019/12/12.
 */
public class PinlockActivity extends BaseActivity<PinlockPresenter> implements PinlockContract.View {

    @BindView(R.id.tv_pinlock_name)
    TextView tvText;
    @BindView(R.id.indicator_pinlock)
    IndicatorDots mIndicatorDots;
    @BindView(R.id.view_pinlock)
    PinLockView mPinLockView;

    public static final String ACTION_SET = "pinlock_set";
    public static final String ACTION_VERIFY = "pinlock_verify";

    private String startAction;
    private boolean isSet;

    @Override
    protected PinlockPresenter createPresenter() {
        return new PinlockPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_pinlock;
    }

    @Override
    protected void initView() {
        startAction = getIntent().getAction();
        isSet = startAction.equals(ACTION_SET) ? true : false;

        if (isSet) {
            tvText.setText("请设置密码");
        } else {
            mPresenter.verifyFingerprint();
            tvText.setText("请输入密码");
        }

        mPinLockView.setPinLockListener(new PinLockListener() {
            @Override
            public void onComplete(String pin) {
                if (isSet) {
                    mPresenter.savePin(pin);
                    showToast("设置成功");
                    finish();
                } else {
                    mPresenter.verifyPin(pin);
                }
            }

            @Override
            public void onEmpty() {
            }

            @Override
            public void onPinChange(int pinLength, String intermediatePin) {
            }
        });
        mPinLockView.attachIndicatorDots(mIndicatorDots);
    }

    @Override
    protected void initData() {

    }

    public static void startPinlockActivity(Context context, String action) {
        Intent intent = new Intent(context, PinlockActivity.class);
        intent.setAction(action);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void verifySuccess() {
        finish();
    }

    @Override
    public void verifyFail() {
        mPinLockView.resetPinLockView();
        showToast("密码错误");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            RxBus.getInstance().post(new PinlockCancelEvent());
        }
        return super.onKeyDown(keyCode, event);
    }
}
