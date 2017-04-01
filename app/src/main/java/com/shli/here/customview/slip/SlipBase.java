package com.shli.here.customview.slip;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * 滑动控件基类
 * Created by shli on 2016-09-02.
 */
public abstract class SlipBase extends ViewGroup {
    protected List<String> list = new ArrayList<>();
    /**
     * 是否处于滑动状态
     */
    public boolean isSlip ;
    /**
     * 容器里面各个控件的MarginTop
     */
    protected int mMarginTopHeight = 0;
    /**
     * 动画时间
     */
    private int mDuration = 1000;

    protected  int mWidth,mHeight;

    protected int mRepeatCount = 0;

    /**
     * 滑动方向
     */
    protected SlipTo slipTo = SlipTo.UP;



    public SlipBase(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlipBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mMarginTopHeight = -h;
        initView();
    }

    /**
     * 初始化子控件
     */
    protected abstract void initView();

    /**
     * 滑动的方法
     */
    public void slip(){
        if (isSlip){
            return;
        }
        isSlip = true;
        ValueAnimator valueAnimator = null;
        switch (slipTo){
            case DOWN:
            case UP:
                valueAnimator =  ValueAnimator.ofFloat(-mHeight,0)
                        .setDuration(mDuration);
                valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float margintop = (float)valueAnimator.getAnimatedValue();
                        mMarginTopHeight = (int)margintop;
                        if(margintop == 0){
                            postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mMarginTopHeight = - mHeight;
                                    mRepeatCount++;
                                    //动画执行完毕  更新状态
                                    doAnimFinish();
                                    isSlip = false;
                                }
                            },50);
                        }else{
                            //动画执行中 不断更新状态
                            doAnim();
                        }
                    }
                });
             break;

            case LEFT:
            case RIGHT:
                valueAnimator =  ValueAnimator.ofFloat(-mWidth,0)
                        .setDuration(mDuration);
                valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float margintop = (float)valueAnimator.getAnimatedValue();
                        mMarginTopHeight = (int)margintop;
                        if(margintop == 0){
                            postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mMarginTopHeight = - mWidth;
                                    mRepeatCount++;
                                    doAnimFinish();
                                    isSlip = false;
                                }
                            },50);
                        }else{
                            doAnim();
                        }
                    }
                });
            break;
        }
        valueAnimator.start();
    }

    /**
     * 动画结束后
     */
    protected abstract void doAnimFinish();


    /**
     * 动画中  保持刷新界面
     */
    protected abstract void doAnim();


    /**
     * 是否是偶数圈
     * @return true 偶数圈   从第0圈算起0为偶数
     */
    protected boolean isEvenRepeat(){
        return mRepeatCount % 2 == 0;
    }

    /**
     * 设置数据
     * @param list
     */
    public void setData(List<String> list){
        this.list = list;
        initView();
    }

    /**
     * 根据当前值 获取当前对应的结果值
     * @param flag
     * @return
     */
    protected String getCurrentPath(int flag){
        if (list.size()==0) return "";
        return list.get(flag % list.size());
    }

    public void setSlip(SlipTo slip){
        if(isSlip) return;
        this.slipTo = slip;
    }
}
