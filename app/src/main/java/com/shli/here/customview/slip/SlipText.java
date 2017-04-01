package com.shli.here.customview.slip;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * 滑动标题
 * Created by shli on 2016-09-02.
 */
public class SlipText extends SlipBase {
    TextView[] mTexts  = new TextView[2];
    public SlipText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlipText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        if(list.size()==0) return;
        removeAllViews();

        MarginLayoutParams marginLayoutParams = new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mTexts.length; i++) {
            mTexts[i] = new TextView(getContext());
            mTexts[i].setText(getCurrentPath(i));
            mTexts[i].setLines(2);
            mTexts[i].setEllipsize(TextUtils.TruncateAt.END);
            mTexts[i].setTextSize(16);
            mTexts[i].setTextColor(Color.parseColor("#FF3000"));
            mTexts[i].setGravity(Gravity.CENTER);
            addViewInLayout(mTexts[i], -1, marginLayoutParams, true);
        }
    }

    @Override
    protected void doAnimFinish() {
        if(isEvenRepeat()){
            mTexts[1].setText(getCurrentPath(mRepeatCount+1));
        }else{
            mTexts[0].setText(getCurrentPath(mRepeatCount+1));
        }
    }

    @Override
    protected void doAnim() {
        switch (slipTo){
            case DOWN:
            case UP:
                if(isEvenRepeat()){
                    getChildAt(0).setAlpha((-mMarginTopHeight/(float)mHeight));
                    getChildAt(1).setAlpha(1-(-mMarginTopHeight/(float)mHeight));
                }else{
                    getChildAt(1).setAlpha((-mMarginTopHeight/(float)mHeight));
                    getChildAt(0).setAlpha(1-(-mMarginTopHeight/(float)mHeight));
                }
                break;
            case LEFT:
            case RIGHT:
                if(isEvenRepeat()){
                    getChildAt(0).setAlpha((-mMarginTopHeight/(float)mWidth));
                    getChildAt(1).setAlpha(1-(-mMarginTopHeight/(float)mWidth));
                }else{
                    getChildAt(1).setAlpha((-mMarginTopHeight/(float)mWidth));
                    getChildAt(0).setAlpha(1-(-mMarginTopHeight/(float)mWidth));
                }
                break;
        }
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(getChildCount()<2) return;
        MarginLayoutParams marginLayoutParams;
        switch (slipTo){
            case DOWN:
                for (int i=0;i<getChildCount(); i++){
                    View childView = getChildAt(i);
                    marginLayoutParams = (MarginLayoutParams) childView.getLayoutParams();
                    int cl= marginLayoutParams.leftMargin, ct = 0, cr = cl + mWidth, cb;
                    if(isEvenRepeat()){
                        if(i==0) {
                            ct = mMarginTopHeight + mHeight;
                        }else if(i==1){
                            ct = mMarginTopHeight;
                        }
                    }else{
                        if(i==0){
                            ct = mMarginTopHeight;
                        }else if(i==1){
                            ct = mMarginTopHeight + mHeight;
                        }
                    }
                    ct = ct +20;
                    cb = ct + mHeight;
                    childView.layout(cl,ct,cr,cb);
                 }
                break;
            case UP:
                for (int i=0;i<getChildCount(); i++){
                    View childView = getChildAt(i);
                    marginLayoutParams = (MarginLayoutParams) childView.getLayoutParams();
                    int cl= marginLayoutParams.leftMargin, ct = 0, cr = cl + mWidth, cb;
                    if(isEvenRepeat()){
                        if(i==0) {
                            ct = -mMarginTopHeight - mHeight;
                        }else if(i==1){
                            ct = -mMarginTopHeight;
                        }
                    }else{
                        if(i==0){
                            ct = -mMarginTopHeight;
                        }else if(i==1){
                            ct = -mMarginTopHeight  -mHeight;
                        }
                    }
                    ct = ct +20;
                    cb = ct + mHeight;
                    childView.layout(cl,ct,cr,cb);
                }
                break;
            case RIGHT:
                for (int i=0;i<getChildCount(); i++){
                    View childView = getChildAt(i);
                    marginLayoutParams = (MarginLayoutParams) childView.getLayoutParams();
                    int cl= 0, ct = marginLayoutParams.topMargin, cr , cb = ct + mHeight;
                    if(isEvenRepeat()){
                        if(i==0) {
                            cl = mMarginTopHeight+mWidth;
                        }else if(i==1){
                            cl = mMarginTopHeight;
                        }
                    }else{
                        if(i==0){
                            cl = mMarginTopHeight;
                        }else if(i==1){
                            cl =mMarginTopHeight+mWidth;
                        }
                    }
                    ct = ct +20;
                    cr = cl + mWidth;
                    childView.layout(cl,ct,cr,cb);
                }
                break;
            case LEFT:
                for (int i=0;i<getChildCount(); i++){
                    View childView = getChildAt(i);
                    marginLayoutParams = (MarginLayoutParams) childView.getLayoutParams();
                    int cl= 0, ct = marginLayoutParams.topMargin, cr , cb = ct + mHeight;
                    if(isEvenRepeat()){
                        if(i==0) {
                            cl = -mMarginTopHeight-mWidth;
                        }else if(i==1){
                            cl = -mMarginTopHeight;
                        }
                    }else{
                        if(i==0){
                            cl = -mMarginTopHeight;
                        }else if(i==1){
                            cl =-mMarginTopHeight-mWidth;
                        }
                    }
                    ct = ct +20;
                    cr = cl + mWidth;
                    childView.layout(cl,ct,cr,cb);
                }
                break;


        }
    }
}
