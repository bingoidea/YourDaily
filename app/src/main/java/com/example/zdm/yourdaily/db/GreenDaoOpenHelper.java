package com.example.zdm.yourdaily.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.zdm.yourdaily.db.dao.DaoMaster;
import com.example.zdm.yourdaily.db.dao.DiaryEntityDao;
import com.example.zdm.yourdaily.db.dao.ItemEntityDao;
import com.example.zdm.yourdaily.db.dao.MemoEntityDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by ZDM on 2019/12/12.
 */
public class GreenDaoOpenHelper extends DaoMaster.OpenHelper {

    public GreenDaoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        MigrationHelper.getInstance().migrate(db,
                ItemEntityDao.class,
                MemoEntityDao.class,
                DiaryEntityDao.class);
    }

}
