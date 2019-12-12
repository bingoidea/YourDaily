package com.example.zdm.yourdaily.ui.main;

import com.example.zdm.yourdaily.base.BaseActivity;
import com.example.zdm.yourdaily.base.BaseApplication;
import com.example.zdm.yourdaily.base.BasePresenter;
import com.example.zdm.yourdaily.db.dao.DaoSession;
import com.example.zdm.yourdaily.db.dao.ItemEntityDao;
import com.example.zdm.yourdaily.db.entity.ItemEntity;

import org.greenrobot.greendao.query.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by ZDM on 2019/12/12.
 */
public class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {

    private ItemEntityDao entityDao;

    public MainPresenter(BaseActivity activity) {
        super(activity);
        DaoSession daoSession = ((BaseApplication) activity.getApplication()).getDaoSession();
        entityDao = daoSession.getItemEntityDao();
    }

    @Override
    public void getItemList(String account) {
        Query query = entityDao.queryBuilder()
                .where(ItemEntityDao.Properties.Account.eq(account))
                .build();
        List<ItemEntity> list = query.list();
        mView.showItemList(list);
    }

    @Override
    public void addItem(int type, String name) {
        ItemEntity entityContact = new ItemEntity();
        entityContact.setAccount(BaseApplication.account);
        entityContact.setDate(new Date());
        entityContact.setItemTitle(name);
        entityContact.setItemType(type);
        entityDao.save(entityContact);
        getItemList(BaseApplication.account);
    }

}
