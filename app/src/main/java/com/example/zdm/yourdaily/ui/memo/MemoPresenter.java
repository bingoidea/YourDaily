package com.example.zdm.yourdaily.ui.memo;

import com.example.zdm.yourdaily.base.BaseActivity;
import com.example.zdm.yourdaily.base.BaseApplication;
import com.example.zdm.yourdaily.base.BasePresenter;
import com.example.zdm.yourdaily.db.dao.DaoSession;
import com.example.zdm.yourdaily.db.dao.ItemEntityDao;
import com.example.zdm.yourdaily.db.dao.MemoEntityDao;
import com.example.zdm.yourdaily.db.entity.ItemEntity;
import com.example.zdm.yourdaily.db.entity.MemoEntity;
import com.example.zdm.yourdaily.event.MainRefreshEvent;
import com.example.zdm.yourdaily.util.RxBus;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * Created by ZDM on 2019/12/12.
 */
public class MemoPresenter extends BasePresenter<MemoContract.View> implements MemoContract.Presenter {

    private MemoEntityDao entityDao;
    private ItemEntityDao entityItemDao;

    protected MemoPresenter(BaseActivity activity) {
        super(activity);
        DaoSession daoSession = ((BaseApplication) activity.getApplication()).getDaoSession();
        entityDao = daoSession.getMemoEntityDao();
        entityItemDao = daoSession.getItemEntityDao();
    }

    @Override
    public void addItem(Long memoId, String string) {
        MemoEntity entity = new MemoEntity();
        entity.setMemoId(memoId);
        entity.setMemo(string);
        entityDao.insert(entity);
        //找到ItemEntity 将count++
        editItemEntityCount(memoId, true);
        getItemList(memoId);
    }

    @Override
    public void getItemList(Long memoId) {
        Query query = entityDao.queryBuilder()
                .where(MemoEntityDao.Properties.MemoId.eq(memoId))
                .orderDesc(MemoEntityDao.Properties.Id)
                .build();
        List<MemoEntity> list = query.list();
        mView.showList(list);
    }

    @Override
    public void deleteItem(Long id, Long memoId) {
        entityDao.deleteByKey(id);
        editItemEntityCount(memoId, false);
    }

    @Override
    public void setLine(boolean addLine, MemoEntity entity) {
        entity.setLine(addLine);
        entityDao.update(entity);
    }

    private void editItemEntityCount(Long memoId, boolean plus) {
        ItemEntity entity = entityItemDao.load(memoId);
        int count = entity.getItemCount();
        if (plus) {
            count++;
        } else {
            count--;
        }
        entity.setItemCount(count);
        entityItemDao.update(entity);
        RxBus.getInstance()
                .post(new MainRefreshEvent());
    }

}
