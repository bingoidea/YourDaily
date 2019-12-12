package com.example.zdm.yourdaily.widget;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zdm.yourdaily.Constant.Constant;
import com.example.zdm.yourdaily.R;
import com.example.zdm.yourdaily.base.BaseActivity;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.android.ActivityEvent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZDM on 2019/12/12.
 */
public class MyDiaryDialog extends DialogFragment {

    @BindView(R.id.tv_diary_dialog_month)
    TextView tvMonth;
    @BindView(R.id.tv_diary_dialog_date)
    TextView tvDate;
    @BindView(R.id.tv_diary_dialog_time)
    TextView tvTime;
    @BindView(R.id.iv_diary_dialog_close)
    ImageView ivClose;
    @BindView(R.id.tv_diary_dialog_title)
    TextView tvTitle;
    @BindView(R.id.tv_diary_dialog_subhead)
    TextView tvSubhead;
    @BindView(R.id.et_diary_dialog_content)
    RichEditTextView etContent;
    @BindView(R.id.iv_diary_dialog_weather)
    ImageView ivWeather;
    @BindView(R.id.iv_diary_dialog_mood)
    ImageView ivMood;
    @BindView(R.id.iv_diary_dialog_more)
    RelativeLayout ivMore;
    @BindView(R.id.iv_diary_dialog_location)
    RelativeLayout ivLocation;
    @BindView(R.id.iv_diary_dialog_camera)
    RelativeLayout ivCamera;
    @BindView(R.id.iv_diary_dialog_music)
    RelativeLayout ivMusic;
    @BindView(R.id.iv_diary_dialog_recycle)
    RelativeLayout ivDelete;

    private String mMonth;
    private String mDate;
    private String mTime;
    private String mWeek;
    private String mTitle;
    private String mSubhead;
    private String mContent;
    private int mMood;
    private int mWeather;

    private View.OnClickListener mListener;

    public static MyDiaryDialog newInstance(Bundle bundle) {
        MyDiaryDialog dialog = new MyDiaryDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mMonth = getArguments().getString("month");
        mDate = getArguments().getString("date");
        mTime = getArguments().getString("time");
        mWeek = getArguments().getString("week");
        mTitle = getArguments().getString("title");
        mSubhead = getArguments().getString("subhead");
        mContent = getArguments().getString("content");
        mMood = getArguments().getInt("mood");
        mWeather = getArguments().getInt("weather");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_dialog_diary, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        tvMonth.setText(mMonth);
        tvDate.setText(mDate);
        tvTime.setText(mTime + "   " + mWeek);
        tvTitle.setText(mTitle);
        tvSubhead.setText(mSubhead);
        etContent.setRichText(mContent);
        ivWeather.setBackgroundResource(Constant.IC_WEATHER[mWeather]);
        ivMood.setBackgroundResource(Constant.IC_MOOD[mMood]);
        RxView.clicks(ivClose)
                .compose(((BaseActivity) getActivity()).bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(aVoid -> {
                    this.dismiss();
                });
        ivDelete.setOnClickListener(mListener);
    }

    public void setDeleteListener(View.OnClickListener listener) {
        mListener = listener;
    }

}
