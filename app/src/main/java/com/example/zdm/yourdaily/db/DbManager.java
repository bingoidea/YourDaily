package com.example.zdm.yourdaily.db;

import android.content.Context;

import com.example.zdm.yourdaily.base.BaseApplication;
import com.example.zdm.yourdaily.db.dao.DaoSession;
import com.example.zdm.yourdaily.db.dao.DiaryEntityDao;
import com.example.zdm.yourdaily.db.dao.ItemEntityDao;
import com.example.zdm.yourdaily.db.dao.TagEntityDao;
import com.example.zdm.yourdaily.db.entity.DiaryEntity;
import com.example.zdm.yourdaily.db.entity.ItemEntity;
import com.example.zdm.yourdaily.db.entity.TagEntity;
import com.example.zdm.yourdaily.event.MainRefreshEvent;
import com.example.zdm.yourdaily.util.RxBus;
import com.example.zdm.yourdaily.util.TimeUtils;

import org.greenrobot.greendao.query.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ZDM on 2019/12/12.
 */
public class DbManager {

    private static DbManager manager;
    private static DaoSession daoSession;

    private static DiaryEntityDao mDiaryEntityDao;
    private static ItemEntityDao mItemEntityDao;
    private static TagEntityDao mTagEntityDao;

    private DbManager(Context context) {
        if (daoSession == null) {
            daoSession = ((BaseApplication) context).getDaoSession();
            mDiaryEntityDao = daoSession.getDiaryEntityDao();
            mItemEntityDao = daoSession.getItemEntityDao();
            mTagEntityDao = daoSession.getTagEntityDao();
        }
    }

    public static void init(Context context) {
        if (manager == null) {
            manager = new DbManager(context);
        }
    }

    public static void addDiary(DiaryEntity entity) {
        mDiaryEntityDao.save(entity);
    }

    /**
     * 获取符合id条件所有diary
     *
     * @param id
     * @return
     */
    public static List<DiaryEntity> queryDiaryListByItemId(Long id) {
        return queryDiaryListByItemIdPage(id, 0);
    }

    /**
     * 获取符合id条件所有diary
     *
     * @param id
     * @return
     */
    public static List<DiaryEntity> queryDiaryListByItemIdPage(Long id, int page) {
        Query query = mDiaryEntityDao.queryBuilder()
                .where(DiaryEntityDao.Properties.DiaryId.eq(id))
                .orderDesc(DiaryEntityDao.Properties.Date)
                .offset(page * 10)
                .limit(10)
                .build();
        List<DiaryEntity> list = updateDiaryListByDate(query.list());
        return list;
    }

    /**
     * 获取符合id calendar条件所有diary
     *
     * @param id
     * @param calendar
     * @return
     */
    public static List<DiaryEntity> queryDiaryListByDate(Long id, Calendar calendar) {
        Query query = mDiaryEntityDao.queryBuilder()
                .where(DiaryEntityDao.Properties.DiaryId.eq(id))
                .orderDesc(DiaryEntityDao.Properties.Date)
                .build();
        List<DiaryEntity> list = getDiaryListByDate(query.list(), calendar);
        return list;
    }

    private static List<DiaryEntity> getDiaryListByDate(List<DiaryEntity> list, Calendar calendar) {
        List<DiaryEntity> listRes = new ArrayList<>();
        for (DiaryEntity entity : list) {
            Calendar c = Calendar.getInstance();
            c.setTime(entity.getDate());
            if (c.get(Calendar.DATE) == calendar.get(Calendar.DATE)) {
                listRes.add(entity);
            }
        }
        return listRes;
    }

    private static List<DiaryEntity> updateDiaryListByDate(List<DiaryEntity> list) {
        List<String> dateList = new ArrayList<>();
        List<DiaryEntity> listNew = new ArrayList<>();
        for (DiaryEntity entity : list) {
            String date = TimeUtils.date2String(entity.getDate(), new SimpleDateFormat("yyyy-MM-dd"));
            if (!dateList.contains(date)) {
                dateList.add(date);
                entity.setShowDate(true);
                mDiaryEntityDao.update(entity);
            } else {
                entity.setShowDate(false);
                mDiaryEntityDao.update(entity);
            }
            listNew.add(entity);
        }
        return listNew;
    }

    /**
     * 根据id删除diary
     *
     * @param id
     */
    public static void deleteDiaryById(Long id) {
        mDiaryEntityDao.deleteByKey(id);
    }

    /**
     * 更新item count数值
     *
     * @param id
     */
    public static void updateItemCountByItemId(Long id) {
        Query query = mDiaryEntityDao.queryBuilder()
                .where(DiaryEntityDao.Properties.DiaryId.eq(id))
                .orderDesc(DiaryEntityDao.Properties.Date)
                .build();
        ItemEntity entity = mItemEntityDao.load(id);
        entity.setItemCount(query.list().size());
        mItemEntityDao.update(entity);
        RxBus.getInstance()
                .post(new MainRefreshEvent());
    }

    /**
     * 保存标签
     *
     * @param entity
     */
    public static void addTag(TagEntity entity) {
        mTagEntityDao.save(entity);
    }

}
