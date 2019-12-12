package com.example.zdm.yourdaily.ui.diary.calendar;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zdm.yourdaily.Constant.Constant;
import com.example.zdm.yourdaily.R;
import com.example.zdm.yourdaily.base.BaseActivity;
import com.example.zdm.yourdaily.db.entity.DiaryEntity;
import com.example.zdm.yourdaily.util.TimeUtils;
import com.example.zdm.yourdaily.widget.MyDiaryDialog;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZDM on 2019/12/12.
 */
public class DiaryCalendarAdapter extends RecyclerView.Adapter<DiaryCalendarAdapter.DiaryListViewHolder> {

    private Context mContext;
    private List<DiaryEntity> mList;
    private DiaryCalendarPresenter mPresenter;

    public DiaryCalendarAdapter(Context context, List<DiaryEntity> list, DiaryCalendarPresenter presenter) {
        mContext = context;
        mList = list;
        mPresenter = presenter;
    }

    @Override
    public DiaryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DiaryListViewHolder holder =
                new DiaryListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_diary_list, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(DiaryListViewHolder holder, int position) {
        final DiaryEntity entity = mList.get(position);
        String date = TimeUtils.date2String(entity.getDate(), new SimpleDateFormat("dd"));
        String week = TimeUtils.getWeek(entity.getDate());
        String time = TimeUtils.date2String(entity.getDate(), new SimpleDateFormat("HH:mm"));
        holder.tvDate.setVisibility(View.GONE);
        holder.tvItemDate.setText(date);
        holder.tvItemWeek.setText(week);
        holder.tvItemTime.setText(time);
        holder.tvItemTitle.setText(entity.getTitle());
        holder.tvItemSubhead.setText(entity.getSubHead());
        holder.cardItem.setOnClickListener(v -> showDiary(entity));
        holder.ivWeather.setBackgroundResource(Constant.IC_WEATHER[entity.getWeather()]);
        holder.ivMood.setBackgroundResource(Constant.IC_MOOD[entity.getMood()]);
    }

    private void showDiary(DiaryEntity entity) {
        String date = TimeUtils.date2String(entity.getDate(), new SimpleDateFormat("dd"));
        String week = TimeUtils.date2String(entity.getDate(), new SimpleDateFormat("EEE"));
        String time = TimeUtils.date2String(entity.getDate(), new SimpleDateFormat("HH:mm"));
        String month = TimeUtils.date2String(entity.getDate(), new SimpleDateFormat("MM")) + "月";
        Bundle bundle = new Bundle();
        bundle.putString("month", month);
        bundle.putString("date", date);
        bundle.putString("time", time);
        bundle.putString("week", week);
        bundle.putString("title", entity.getTitle());
        bundle.putString("subhead", entity.getSubHead());
        bundle.putString("content", entity.getContent());
        bundle.putInt("mood", entity.getMood());
        bundle.putInt("weather", entity.getWeather());
        MyDiaryDialog dialog = MyDiaryDialog.newInstance(bundle);
        dialog.setDeleteListener(v -> {
            ((BaseActivity) mContext).showDialog("删除本篇日记？", (dialog1, which) -> {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    mPresenter.deleteDary(entity.getId(), entity.getDiaryId());
                    dialog.getDialog().dismiss();
                }
            });
        });
        dialog.show(((BaseActivity) mContext).getSupportFragmentManager(), "");
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class DiaryListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_diray_list_date)
        TextView tvDate;
        @BindView(R.id.cv_diary_list)
        CardView cardItem;
        @BindView(R.id.tv_item_diary_list_date)
        TextView tvItemDate;
        @BindView(R.id.tv_item_diary_list_week)
        TextView tvItemWeek;
        @BindView(R.id.tv_item_diary_list_time)
        TextView tvItemTime;
        @BindView(R.id.tv_item_diary_list_title)
        TextView tvItemTitle;
        @BindView(R.id.tv_item_diary_list_sunhead)
        TextView tvItemSubhead;
        @BindView(R.id.iv_item_diary_list_weather)
        ImageView ivWeather;
        @BindView(R.id.iv_item_diary_list_mood)
        ImageView ivMood;

        public DiaryListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
