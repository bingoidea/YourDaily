package com.example.zdm.yourdaily.ui.setting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

import com.example.zdm.yourdaily.Constant.Constant;
import com.example.zdm.yourdaily.R;
import com.example.zdm.yourdaily.base.BaseActivity;
import com.example.zdm.yourdaily.base.IPresenter;
import com.example.zdm.yourdaily.event.PinlockCancelEvent;
import com.example.zdm.yourdaily.ui.pinlock.PinlockActivity;
import com.example.zdm.yourdaily.util.RxBus;
import com.example.zdm.yourdaily.util.SPUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import butterknife.BindView;

/**
 * Created by ZDM on 2019/12/12.
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.switch_setting_switch_pin)
    Switch switchPin;

    private boolean isPinOpen;

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        SPUtils spPin = new SPUtils(this, Constant.SP_PIN);
        isPinOpen = spPin.getBoolean(Constant.SP_PIN_STATUS, false);
        switchPin.setChecked(isPinOpen);
        switchPin.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (spPin.getString(Constant.SP_PIN_PASSWORD) == null) {
                    PinlockActivity.startPinlockActivity(SettingActivity.this, PinlockActivity.ACTION_SET);
                }
            }
            switchPin.setChecked(isChecked);
            spPin.putBoolean(Constant.SP_PIN_STATUS, isChecked);
        });

        RxBus.getInstance().onEvent(PinlockCancelEvent.class)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(pinlockCancelEvent -> {
                    switchPin.setChecked(false);
                });
    }

    @Override
    protected void initData() {

    }

}
