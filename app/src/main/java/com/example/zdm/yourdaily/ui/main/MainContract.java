package com.example.zdm.yourdaily.ui.main;

import com.example.zdm.yourdaily.base.IPresenter;
import com.example.zdm.yourdaily.base.IView;
import com.example.zdm.yourdaily.db.entity.ItemEntity;

import java.util.List;

/**
 * Created by ZDM on 2019/12/12.
 */
public interface MainContract {

    interface View extends IView {
        void showItemList(List<ItemEntity> list);
    }

    interface Presenter extends IPresenter<View> {
        void getItemList(String account);
        void addItem(int type,String name);
    }

}
