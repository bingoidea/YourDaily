package com.example.zdm.yourdaily.widget;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.zdm.yourdaily.R;

/**
 * 提示框
 * Created by ZDM on 2019/12/12.
 */
public class MyAlertDialog extends DialogFragment {

    private static String mTitle = "标题";
    private static String mContent = "内容";
    private static String mAffirm = "确定";
    private static View.OnClickListener mListener = null;

    private TextView mTvTitle;
    private TextView mTvContent;
    private TextView mTvAffirm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_dialog_alert, container);
        mTvTitle = (TextView) view.findViewById(R.id.tv_dialog_title);
        mTvContent = (TextView) view.findViewById(R.id.tv_dialog_content);
        mTvAffirm = (TextView) view.findViewById(R.id.tv_dialog_affirm);
        mTvTitle.setText(mTitle);
        mTvContent.setText(mContent);
        mTvAffirm.setText(mAffirm);
        mTvAffirm.setOnClickListener(mListener);
        return view;
    }

    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public void setContent(String content) {
        mTvContent.setText(content);
    }

    public void setAffirm(String affirm) {
        mTvAffirm.setText(affirm);
    }

    public void setListener(View.OnClickListener listener) {
        mTvAffirm.setOnClickListener(mListener);
    }

    public static class Builder {

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setContent(String content) {
            mContent = content;
            return this;
        }

        public Builder setAffirm(String affirm) {
            mAffirm = affirm;
            return this;
        }

        public Builder setListener(View.OnClickListener listener) {
            mListener = listener;
            return this;
        }

        public MyAlertDialog build() {
            MyAlertDialog dialog = new MyAlertDialog();
            return dialog;
        }

    }

}
