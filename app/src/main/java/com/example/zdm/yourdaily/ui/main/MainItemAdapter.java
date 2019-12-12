package com.example.zdm.yourdaily.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zdm.yourdaily.R;
import com.example.zdm.yourdaily.db.entity.ItemEntity;
import com.example.zdm.yourdaily.ui.diary.DiaryActivity;
import com.example.zdm.yourdaily.ui.memo.MemoActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * MainItem适配器(recyclerview要使用)
 * Created by ZDM on 2019/12/12.
 */
public class MainItemAdapter extends RecyclerView.Adapter<MainItemAdapter.ItemViewHolder> {

    private Context mContext;
    private List<ItemEntity> mList;

    public MainItemAdapter(Context mContext, List<ItemEntity> list) {
        this.mContext = mContext;
        this.mList = list;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder holder = new ItemViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_main, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final ItemEntity entity = mList.get(position);
        holder.tvItemName.setText(entity.getItemTitle());
        holder.tvItemCount.setText(entity.getItemCount() + "");
        switch (entity.getItemType()) {
            case 0:
                holder.ivItemType.setBackgroundResource(R.drawable.ic_plus_contact);
                break;
            case 1:
                holder.ivItemType.setBackgroundResource(R.drawable.ic_plus_diary);
                break;
            case 2:
                holder.ivItemType.setBackgroundResource(R.drawable.ic_plus_alert);
                break;
        }
        holder.layouItemMain.setOnClickListener(view -> {
            switch (entity.getItemType()) {
                case 0:
                    break;
                case 1:
                    DiaryActivity.startDiaryActivity(mContext, entity.getId());
                    break;
                case 2:
                    MemoActivity.startMemoActivity(mContext, entity.getId(), entity.getItemTitle());
                    break;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_item_main)
        RelativeLayout layouItemMain;
        @BindView(R.id.tv_item_main_title)
        TextView tvItemName;
        @BindView(R.id.tv_item_main_count)
        TextView tvItemCount;
        @BindView(R.id.iv_item_main_type)
        ImageView ivItemType;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
