package com.example.zdm.yourdaily.ui.diary.list;

import com.example.zdm.yourdaily.base.BaseActivity;
import com.example.zdm.yourdaily.base.BasePresenter;
import com.example.zdm.yourdaily.db.DbManager;
import com.example.zdm.yourdaily.db.entity.DiaryEntity;

import java.util.List;

/**
 * Created by ZDM on 2019/12/12.
 */
public class DiaryListPresenter extends BasePresenter<DiaryListContract.View> implements DiaryListContract.Presenter {

    protected DiaryListPresenter(BaseActivity activity) {
        super(activity);
    }

    @Override
    public void getDiaryList(Long diaryId) {
        List<DiaryEntity> list = DbManager.queryDiaryListByItemId(diaryId);
        mView.showDiaryList(list);
    }

    @Override
    public void getDiaryList(Long diaryId, int page) {
        List<DiaryEntity> list = DbManager.queryDiaryListByItemIdPage(diaryId, page);
        mView.showMoreDiaryList(list);
    }

    @Override
    public void deleteDary(Long id, Long diaryId) {
        DbManager.deleteDiaryById(id);
        editItemEntityCount(diaryId);
        getDiaryList(diaryId);
    }

    private void editItemEntityCount(Long diaryId) {
        DbManager.updateItemCountByItemId(diaryId);
    }

}
