package com.example.zdm.yourdaily.ui.diary.calendar;

import com.example.zdm.yourdaily.base.BaseActivity;
import com.example.zdm.yourdaily.base.BasePresenter;
import com.example.zdm.yourdaily.db.DbManager;
import com.example.zdm.yourdaily.db.entity.DiaryEntity;

import java.util.Calendar;
import java.util.List;

/**
 * Created by ZDM on 2019/12/12.
 */
public class DiaryCalendarPresenter extends BasePresenter<DiaryCalendarContract.View> implements DiaryCalendarContract.Presenter {

    private Calendar mCalendar;

    protected DiaryCalendarPresenter(BaseActivity activity) {
        super(activity);
    }

    @Override
    public void getDiaryList(Long diaryId, Calendar calendar) {
        mCalendar = calendar;
        List<DiaryEntity> list = DbManager.queryDiaryListByDate(diaryId, calendar);
        mView.showDiaryList(list);
    }

    @Override
    public void deleteDary(Long id, Long diaryId) {
        DbManager.deleteDiaryById(id);
        editItemEntityCount(diaryId);
        getDiaryList(diaryId, mCalendar);
    }

    private void editItemEntityCount(Long diaryId) {
        DbManager.updateItemCountByItemId(diaryId);
    }
}
