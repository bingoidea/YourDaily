package com.example.zdm.yourdaily.ui.memo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zdm.yourdaily.R;
import com.example.zdm.yourdaily.base.BaseActivity;
import com.example.zdm.yourdaily.db.entity.MemoEntity;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

import butterknife.BindView;

/**
 * Created by ZDM on 2019/12/12.
 */
public class MemoActivity extends BaseActivity<MemoPresenter> implements MemoContract.View {

    @BindView(R.id.tv_memo_title)
    TextView tvTitle;
    @BindView(R.id.iv_memo_edit)
    ImageView ivEdit;
    @BindView(R.id.tv_memo_save)
    TextView tvSave;
    @BindView(R.id.et_memo_new)
    EditText etNew;
    @BindView(R.id.rv_memo)
    RecyclerView rvMemo;

    private MemoAdapter mAdapter;

    private boolean isEdit = false;

    private Long memoId;
    private List<MemoEntity> mList;

    @Override
    protected MemoPresenter createPresenter() {
        return new MemoPresenter(this);
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_memo;
    }

    @Override
    protected void initView() {
        RxView.clicks(tvSave)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(aVoid -> {
                    if (TextUtils.isEmpty(etNew.getText().toString())) {
                        showToast("请输入有效字符");
                        return;
                    }
                    mPresenter.addItem(memoId, etNew.getText().toString());
                    etNew.setText("");
                });
        rvMemo.setLayoutManager(new LinearLayoutManager(this));
        RxView.clicks(ivEdit)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(aVoid -> {
                    if (isEdit) {
                        showList();
                    } else {
                        showListEdit();
                    }
                    isEdit = !isEdit;
                });
    }

    @Override
    protected void initData() {
        tvTitle.setText(getIntent().getStringExtra("title"));
        memoId = getIntent().getLongExtra("id", 0);
        mPresenter.getItemList(memoId);
    }

    public static void startMemoActivity(Context context, Long id, String title) {
        Intent intent = new Intent(context, MemoActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    public void showList(List<MemoEntity> list) {
        mList = list;
        showList();
    }

    private void showList() {
        findViewById(R.id.layout_memo_add).setVisibility(View.VISIBLE);
        if (mList != null && mList.size() > 0) {
            mAdapter = new MemoAdapter(this, mList, false, mPresenter);
            rvMemo.setAdapter(mAdapter);
        }
    }

    private void showListEdit() {
        findViewById(R.id.layout_memo_add).setVisibility(View.GONE);
        if (mList != null && mList.size() > 0) {
            mAdapter = new MemoAdapter(this, mList, true, mPresenter);
            rvMemo.setAdapter(mAdapter);
        }
    }

}
