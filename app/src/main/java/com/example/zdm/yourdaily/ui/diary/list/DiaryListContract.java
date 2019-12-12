package com.example.zdm.yourdaily.ui.diary.list;

import com.example.zdm.yourdaily.base.IPresenter;
import com.example.zdm.yourdaily.base.IView;
import com.example.zdm.yourdaily.db.entity.DiaryEntity;

import java.util.List;

/**
 * Created by ZDM on 2019/12/12.
 */
public interface DiaryListContract {

    interface View extends IView {

        void showDiaryList(List<DiaryEntity> list);

        void showMoreDiaryList(List<DiaryEntity> list);

    }

    interface Presenter extends IPresenter<View> {

        void getDiaryList(Long diaryId);

        void getDiaryList(Long diaryId, int page);

        void deleteDary(Long id, Long diaryId);
    }

}
