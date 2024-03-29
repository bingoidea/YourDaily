package com.example.zdm.yourdaily.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ZDM on 2019/12/12.
 */
public abstract class BaseActivity<T extends IPresenter> extends RxAppCompatActivity implements IView {

    protected T mPresenter;
    protected Unbinder mUnbinder;

    private ProgressDialog dialogLoading;
    private AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); // 设置之后可以重力感应进行横竖屏切换
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onCreate(savedInstanceState);
        setContentView(setContentViewId());
        mUnbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    protected abstract T createPresenter();

    protected abstract int setContentViewId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
        if (mUnbinder != null) mUnbinder.unbind();
    }

    /**
     * 显示Toast信息
     * @param msg
     */
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示正在加载
     */
    public void showLoading() {
        if (dialogLoading == null) {
            dialogLoading = new ProgressDialog(this);
        }
        dialogLoading.show();
    }

    /**
     * 显示弹窗
     * @param msg
     */
    public void showDialog(String msg) {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this)
                    .setPositiveButton("确定", null)
                    .create();
        }
        dialog.setMessage(msg);
        dialog.show();
    }

    /**
     * 接口方式显示弹窗
     * @param msg
     * @param listener
     */
    public void showDialog(String msg, DialogInterface.OnClickListener listener) {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(this)
                    .setPositiveButton("确定", listener)
                    .setNegativeButton("取消", null)
                    .create();
        }
        dialog.setMessage(msg);
        dialog.show();
    }

    /**
     * 弹窗消失
     */
    public void dismissDialog() {
        if (dialogLoading != null) {
            dialogLoading.dismiss();
        }
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 隐藏虚拟键盘
     */
    public void hideKeyboard() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
