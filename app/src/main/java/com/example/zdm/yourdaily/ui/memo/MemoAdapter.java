package com.example.zdm.yourdaily.ui.memo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zdm.yourdaily.R;
import com.example.zdm.yourdaily.db.entity.MemoEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * memo适配器
 * Created by ZDM on 2019/12/12.
 */
public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ItemViewHolder> {

    private Context mContext;
    private List<MemoEntity> mList;

    private boolean isEdit = false;
    private MemoPresenter mPresenter;

    public MemoAdapter(Context mContext, List<MemoEntity> list, boolean isEdit, MemoPresenter presenter) {
        this.mContext = mContext;
        this.mList = list;
        this.isEdit = isEdit;
        this.mPresenter = presenter;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder holder = new ItemViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_memo, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final MemoEntity entity = mList.get(position);
        holder.tvContent.setText(entity.getMemo());
        boolean hasLine = entity.getLine();
        if (hasLine) {
            holder.tvContent.setTextColor(Color.parseColor("#999999"));
            holder.tvContent.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
        } else {
            holder.tvContent.setTextColor(Color.parseColor("#333333"));
            holder.tvContent.getPaint().setFlags(Paint.ANTI_ALIAS_FLAG);
        }
        holder.tvContent.setOnClickListener(v -> {
            mPresenter.setLine(!hasLine, entity);
            notifyDataSetChanged();
        });
        if (isEdit) {
            holder.ivDelete.setVisibility(View.VISIBLE);
        } else {
            holder.ivDelete.setVisibility(View.GONE);
        }
        holder.ivDelete.setOnClickListener(view -> {
            mPresenter.deleteItem(entity.getId(), entity.getMemoId());
            mList.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_item_memo)
        RelativeLayout layouItemMain;
        @BindView(R.id.tv_memo_content)
        TextView tvContent;
        @BindView(R.id.iv_memo_dot)
        ImageView ivDot;
        @BindView(R.id.iv_memo_delete)
        ImageView ivDelete;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
