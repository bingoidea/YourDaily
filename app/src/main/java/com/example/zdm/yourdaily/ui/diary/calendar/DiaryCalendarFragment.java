package com.example.zdm.yourdaily.ui.diary.calendar;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.zdm.yourdaily.R;
import com.example.zdm.yourdaily.base.BaseActivity;
import com.example.zdm.yourdaily.base.BaseFragment;
import com.example.zdm.yourdaily.db.entity.DiaryEntity;
import com.example.zdm.yourdaily.widget.DiaryCalendarView;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.android.FragmentEvent;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ZDM on 2019/12/12.
 */
public class DiaryCalendarFragment extends BaseFragment<DiaryCalendarPresenter> implements DiaryCalendarContract.View {

    @BindView(R.id.layout_diary_calendar)
    FrameLayout mLayout;
    @BindView(R.id.iv_diary_calendar_arrow)
    ImageView ivArrow;
    @BindView(R.id.rv_diary_calendar)
    RecyclerView rvList;

    private DiaryCalendarAdapter mAdapter;

    private boolean showList = false;

    private Long diaryId;

    public static DiaryCalendarFragment newInstance(Long id) {
        DiaryCalendarFragment fragment = new DiaryCalendarFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected DiaryCalendarPresenter createPresenter() {
        return new DiaryCalendarPresenter((BaseActivity) getActivity());
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_diary_calendar;
    }

    @Override
    protected void initView() {
        diaryId = getArguments().getLong("id", 0);
        DiaryCalendarView calendarView = new DiaryCalendarView(getActivity(),
                calendar -> {
                    mPresenter.getDiaryList(diaryId, calendar);
                });
        mLayout.addView(calendarView);

        rvList.setLayoutManager(new LinearLayoutManager(mActivity));
        RxView.clicks(ivArrow)
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(aVoid -> {
                    if (showList) {
                        ivArrow.setImageResource(R.drawable.ic_arrow_down);
                        mLayout.setVisibility(View.VISIBLE);
                        rvList.setVisibility(View.GONE);
                    } else {
                        ivArrow.setImageResource(R.drawable.ic_arrow_up);
                        mLayout.setVisibility(View.GONE);
                        rvList.setVisibility(View.VISIBLE);
                    }
                    showList = !showList;
                });


    }

    @Override
    protected void initData() {
    }

    @Override
    public void showDiaryList(List<DiaryEntity> list) {
        mAdapter = new DiaryCalendarAdapter(getActivity(), list, mPresenter);
        rvList.setAdapter(mAdapter);
    }

}
