package com.example.zdm.yourdaily.base;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.example.zdm.yourdaily.Constant.Constant;
import com.example.zdm.yourdaily.db.DbManager;
import com.example.zdm.yourdaily.db.GreenDaoOpenHelper;
import com.example.zdm.yourdaily.db.dao.DaoMaster;
import com.example.zdm.yourdaily.db.dao.DaoSession;
import com.example.zdm.yourdaily.ui.pinlock.PinlockActivity;
import com.example.zdm.yourdaily.util.MyActivityLifecycleCallbacks;
import com.example.zdm.yourdaily.util.SPUtils;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import java.io.File;

/**
 * BaseApplication
 * Created by ZDM on 2019/12/12.
 */
public class BaseApplication extends Application {

    private static Context mContext;

    public static String account;

    public static final boolean ENCRYPTED = true;

    public DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Logger.init("your-diary")
                .methodCount(3)
                .hideThreadInfo()
                .logLevel(LogLevel.FULL)
                .methodOffset(2);
        initActivityLifecycle();
        initDataBase();
    }

    public static Context getContext() {
        return mContext;
    }

    private void initActivityLifecycle() {
        SPUtils spPin = new SPUtils(this, Constant.SP_PIN);
        MyActivityLifecycleCallbacks callbacks = new MyActivityLifecycleCallbacks();
        this.registerActivityLifecycleCallbacks(callbacks);
        callbacks.setCallback(new MyActivityLifecycleCallbacks.Callback() {
            @Override
            public void backToForeground() {
                boolean isPinOpen = spPin.getBoolean(Constant.SP_PIN_STATUS, false);
                if (isPinOpen) {
                    PinlockActivity.startPinlockActivity(getApplicationContext(), PinlockActivity.ACTION_VERIFY);
                }
            }

            @Override
            public void foreToBackground() {

            }
        });
    }

    private void initDataBase() {
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "yd-db-encrypted" : "yd-db");
//        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("yd-pwd") : helper.getWritableDb();
//        Database db = helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();
        File path = new File(Environment.getExternalStorageDirectory(), "/YourDiary/DB/" + "yd-db");
        path.getParentFile().mkdirs();
        GreenDaoOpenHelper helper = null;
        if (path.getParentFile().exists()) {
            helper = new GreenDaoOpenHelper(this, path.getAbsolutePath(), null);
        } else {
            helper = new GreenDaoOpenHelper(this, "yd-db", null);
        }
//        GreenDaoOpenHelper helper = new GreenDaoOpenHelper(this, "yd-db", null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        DbManager.init(this);
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
