package com.example.zdm.yourdaily.ui.memo;

import com.example.zdm.yourdaily.base.IPresenter;
import com.example.zdm.yourdaily.base.IView;
import com.example.zdm.yourdaily.db.entity.MemoEntity;

import java.util.List;

/**
 * Created by ZDM on 2019/12/12.
 */
public interface MemoContract {

    interface View extends IView {
        void showList(List<MemoEntity> list);
    }

    interface Presenter extends IPresenter<View> {

        void addItem(Long memoId, String string);

        void getItemList(Long memoId);

        void deleteItem(Long id, Long memoId);

        void setLine(boolean addLine, MemoEntity entity);
    }

}
