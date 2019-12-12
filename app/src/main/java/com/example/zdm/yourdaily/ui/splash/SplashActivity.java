package com.example.zdm.yourdaily.ui.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.zdm.yourdaily.Constant.Constant;
import com.example.zdm.yourdaily.R;
import com.example.zdm.yourdaily.base.BaseActivity;
import com.example.zdm.yourdaily.base.BaseApplication;
import com.example.zdm.yourdaily.base.IPresenter;
import com.example.zdm.yourdaily.ui.login.LoginActivity;
import com.example.zdm.yourdaily.ui.main.MainActivity;
import com.example.zdm.yourdaily.util.SPUtils;
import com.example.zdm.yourdaily.util.TimeUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;

/**
 * 欢迎界面
 * Created by ZDM on 2019/12/12.
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.tv_splash_date)
    TextView tvData;

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 EEEE");
        tvData.setText(TimeUtils.getCurTimeString(sdf));
    }

    @Override
    protected void initData() {
        Intent intent = new Intent();
        SPUtils sp = new SPUtils(this, Constant.SP_ACCOUNT);
        if (sp.getString(Constant.SP_ACCOUNT_NUMBER) != null) {
            intent.setClass(this, MainActivity.class);
            BaseApplication.account = sp.getString(Constant.SP_ACCOUNT_NUMBER);
        } else {
            intent.setClass(this, LoginActivity.class);
        }
        // 持续两秒
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(aLong -> {
                    this.finish();
                    startActivity(intent);
                });
    }
}
