package com.example.firebasetest.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;

public abstract class BaseDataBindingActivity<B extends ViewDataBinding, V extends AndroidViewModel>
        extends AppCompatActivity {

    protected B mBinding;

    protected V mViewModel;

    protected abstract int getContentViewLayoutId();

    public Context context() {
        return this;
    }

    protected abstract void initListeners();

    protected abstract void initData();

    protected abstract void subscribeToViewModel();

    private int mCurrentViewIdSelected;
    private boolean mIsKeyboardShowing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getContentViewLayoutId());
        initListeners();
        initData();
        subscribeToViewModel();

        //        mBinding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(() -> {
        //
        //            Rect r = new Rect();
        //            mBinding.getRoot().getWindowVisibleDisplayFrame(r);
        //            int screenHeight = mBinding.getRoot().getRootView().getHeight();
        //
        //            // r.bottom is the position above soft keypad or device button.
        //            // if keypad is shown, the r.bottom is smaller than that before.
        //            int keypadHeight = screenHeight - r.bottom;
        //
        //            // 0.15 ratio is perhaps enough to determine keypad height.
        //            mIsKeyboardShowing = keypadHeight > screenHeight * 0.15;
        //        });
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {
        // all touch events close the keyboard before they are processed except EditText instances.
        // if focus is an EditText we need to check, if the touchevent was inside the focus editTexts
        final View currentFocus = getCurrentFocus();
        if (currentFocus == null) return super.dispatchTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mCurrentViewIdSelected = currentFocus.getId();
        }
        if (ev.getAction() == MotionEvent.ACTION_UP
                && mCurrentViewIdSelected == getCurrentFocus().getId()
                && mIsKeyboardShowing) {
            if (!(currentFocus instanceof EditText || currentFocus instanceof ImageButton)
                    || !isTouchInsideView(ev, currentFocus)) {

                hideKeyboard(this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * determine if the given motionevent is inside the given view.
     *
     * @param ev the given view
     * @param currentFocus the motion event.
     * @return if the given motionevent is inside the given view
     */
    private boolean isTouchInsideView(final MotionEvent ev, final View currentFocus) {
        final int[] loc = new int[2];
        currentFocus.getLocationOnScreen(loc);
        return ev.getRawX() > loc[0] && ev.getRawY() > loc[1] && ev.getRawX() < (loc[0]
                + currentFocus.getWidth()) && ev.getRawY() < (loc[1] + currentFocus.getHeight());
    }

    private void hideKeyboard(@NonNull Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) return;
        if (view.isClickable() && view.isEnabled()) {
            view.performClick();
        }
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) return;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}