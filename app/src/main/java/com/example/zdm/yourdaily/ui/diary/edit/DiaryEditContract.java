package com.example.zdm.yourdaily.ui.diary.edit;

import com.example.zdm.yourdaily.base.IPresenter;
import com.example.zdm.yourdaily.base.IView;
import com.example.zdm.yourdaily.db.entity.DiaryEntity;

import java.util.List;

/**
 * Created by ZDM on 2019/12/12.
 */
public interface DiaryEditContract {

    interface View extends IView {
        void saveDone();
    }

    interface Presenter extends IPresenter<View> {
        void save(Long diaryId, DiaryEntity entity, List<String> tags);
    }

}
