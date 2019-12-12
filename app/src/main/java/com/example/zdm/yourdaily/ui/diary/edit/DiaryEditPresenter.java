package com.example.zdm.yourdaily.ui.diary.edit;

import com.example.zdm.yourdaily.base.BaseActivity;
import com.example.zdm.yourdaily.base.BasePresenter;
import com.example.zdm.yourdaily.db.DbManager;
import com.example.zdm.yourdaily.db.entity.DiaryEntity;
import com.example.zdm.yourdaily.db.entity.TagEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by ZDM on 2019/12/12.
 */
public class DiaryEditPresenter extends BasePresenter<DiaryEditContract.View> implements DiaryEditContract.Presenter {

    protected DiaryEditPresenter(BaseActivity activity) {
        super(activity);
    }

    @Override
    public void save(Long diaryId, DiaryEntity entity, List<String> tags) {
        DbManager.addDiary(entity);
        DbManager.updateItemCountByItemId(diaryId);
        mView.saveDone();
        Observable.from(tags)
                .subscribe(s -> {
                    TagEntity tag = new TagEntity();
                    tag.setIdItem(diaryId);
                    tag.setIdDiary(entity.getId());
                    tag.setTag(s);
                    DbManager.addTag(tag);
                });
    }

}
