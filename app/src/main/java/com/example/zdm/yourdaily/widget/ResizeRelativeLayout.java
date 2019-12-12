package com.example.zdm.yourdaily.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by ZDM on 2019/12/12.
 */
public class ResizeRelativeLayout extends RelativeLayout {

    private static final int SOFTKEYPAD_MIN_HEIGHT = 50;

    public ResizeRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (oldh - h > SOFTKEYPAD_MIN_HEIGHT) {
            keyBordStateListener.keyBoardIsShowing(true);
        } else {
            if (keyBordStateListener != null) {
                keyBordStateListener.keyBoardIsShowing(false);
            }
        }
    }

    private KeyBordStateListener keyBordStateListener;

    public void setKeyBordStateListener(KeyBordStateListener keyBordStateListener) {
        this.keyBordStateListener = keyBordStateListener;
    }

    public interface KeyBordStateListener {
        void keyBoardIsShowing(boolean state);
    }

}
