package com.shli.here.customview.slip;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * 实现图片的平滑循环替换
 * Created by shli on 2016-09-02.
 */
public class SlipImage extends SlipBase {
    ImageView[] imageViews = new ImageView[2];
    public SlipImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlipImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {
        if(list.size()==0) return;
        removeAllViews();
        MarginLayoutParams marginLayoutParams = new MarginLayoutParams(mWidth,mHeight);

        for(int i=0; i<imageViews.length; i++){
            imageViews[i] = new ImageView(getContext());
            //加载图片
            Glide.with(getContext()).load(getCurrentPath(i)).dontTransform().dontAnimate().centerCrop().into(imageViews[i]);
            addView(imageViews[i],-1,marginLayoutParams);
        }
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundColor(Color.parseColor("#90000000"));
        imageView.setAlpha(0f);
        addView(imageView,-1,marginLayoutParams);
    }

    @Override
    protected void doAnimFinish() {
        //根据循环的次数加载图片 实现循环加载
        if(isEvenRepeat()){
            Glide.with(getContext()).load(getCurrentPath(mRepeatCount+1)).dontTransform().centerCrop().dontAnimate().into(imageViews[1]);
        }else{
            Glide.with(getContext()).load(getCurrentPath(mRepeatCount+1)).dontTransform().centerCrop().dontAnimate().into(imageViews[0]);
        }
        //去除阴影
        getChildAt(2).setAlpha(0f);
    }

    @Override
    protected void doAnim() {
        switch (slipTo){
            case DOWN:
            case UP:
                getChildAt(2).setAlpha(1-(-mMarginTopHeight/(float)mHeight));
                break;
            case LEFT:
            case RIGHT:
                getChildAt(2).setAlpha(1-(-mMarginTopHeight/(float)mWidth));
                break;
        }
        requestLayout();
    }

    @Override
    protected void onLayout(boolean changeed, int l, int t, int r, int b) {
        if(getChildCount()<3) return;
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
                    if(i==2){
                        ct = mMarginTopHeight + mHeight;
                    }
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
                            ct = -mHeight-mMarginTopHeight;
                        }else if(i==1){
                            ct = -mMarginTopHeight;
                        }
                    }else{
                        if(i==0){
                            ct = -mMarginTopHeight;
                        }else if(i==1){
                            ct = -mMarginTopHeight - mHeight;
                        }
                    }
                    if(i==2){
                        ct = -mMarginTopHeight - mHeight;
                    }
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
                    if(i==2){
                        cl = mMarginTopHeight+mWidth;
                    }
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
                            cl =  -mMarginTopHeight;
                        }else if(i==1){
                            cl = -mMarginTopHeight-mWidth;
                        }
                    }
                    if(i==2){
                        cl = -mMarginTopHeight-mWidth;
                    }
                    cr = cl + mWidth;
                    childView.layout(cl,ct,cr,cb);
                }
                break;


        }

    }
}
