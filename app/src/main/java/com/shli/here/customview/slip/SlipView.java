package com.shli.here.customview.slip;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;


import com.shli.here.activity.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 平滑
 * Created by shli on 2016-09-02.
 */
public class SlipView extends FrameLayout {
    private SlipImage galleryImage;
    private SlipText galleryText;
    DisplayMetrics dm = new DisplayMetrics();


    public SlipView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.gallery_view, this);
        galleryImage = (SlipImage) findViewById(R.id.gallery_view);
        galleryText = (SlipText) findViewById(R.id.title_view);
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        x_limit = dm.widthPixels / 4f;
        y_limit = dm.heightPixels / 4f;
    }


    /**
     * 设置数据
     * @param list
     */
    public void setData(List<BaseEntry> list){
        init(list);
    }

    /**
     * 分发数据
     * @param list
     */
    private void  init(List<BaseEntry> list){
        if (list.size()==0) return;
        List<String> urls = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for(BaseEntry entry : list){
            urls.add(entry.pathurl);
            titles.add(entry.title);
        }
        galleryImage.setData(urls);
        galleryText.setData(titles);
    }

    public void slipTo(SlipTo to){
        galleryText.setSlip(to);
        galleryImage.setSlip(to);
        galleryImage.slip();
        galleryText.slip();
    }




    /** 点击时候Y的坐标*/
    private int downY = 0;
    /** 手指抬起是Y的坐标*/
    private int upY = 0;
    private int downX = 0;
    private int upX = 0;
    /** 屏幕宽高的 1/4 用来判断手势的方向*/
    float x_limit ;
    float y_limit ;

    /**
     * 通过滑动事件  控制图片加载方向
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downY = (int) event.getRawY();
                downX = (int) event.getRawX();
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                upY = (int) event.getRawY();
                upX = (int) event.getRawX();
                if(Math.abs(downY-upY)>y_limit){
                    if((downY - upY) >0)
                        slipTo(SlipTo.UP);
                    else
                        slipTo(SlipTo.DOWN);
                }
                if(Math.abs(downX-upX)>x_limit){
                    if((downX-upX) >0)
                        slipTo(SlipTo.LEFT);
                    else
                        slipTo(SlipTo.RIGHT);
                }
                break;
        }
        return false;
    }
}
