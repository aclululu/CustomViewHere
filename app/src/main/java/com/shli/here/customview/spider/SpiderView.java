package com.shli.here.customview.spider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * 雷达图
 * Created by shli on 2016-10-14.
 */
public class SpiderView extends View {
    //设置View的默认大小
    private final static int DEFAULTSIZE = 400;
    private float mDefaultMaxR;
    private float mMaxR;  //最长的线段  最大值
    private int mCount = 6;  //多边形层数
    private float mR ;   //根据mMaxR 和 mCount计算   多边形之间间距
    private float mSizeCount; //多边形N
    private float mAngle;  //计算角度
    private float mMaxNumber = 300; //mMaxR代表的线段的长度   用来计算各个连线的比例
    private int mCenterW;  //中心位置
    private int mCenterH;
    private Paint mSpiderPaint;  //蛛网画笔
    private Paint mDataPaint;  //数据区域画笔
    private Paint mTextPaint;  //文字画笔
    private List<Spider> spiders;




    public SpiderView(Context context) {
        super(context);
        init();
    }

    public SpiderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    //初始化画笔  正式使用的时候可以在这里获取一些 用户自定义的attrs参数
    private void init() {
        mSpiderPaint = new Paint();
        mSpiderPaint.setAntiAlias(true);
        mSpiderPaint.setColor(Color.GRAY);
        mSpiderPaint.setStyle(Paint.Style.STROKE);

        mDataPaint = new Paint();
        mDataPaint.setAntiAlias(true);
        mDataPaint.setColor(Color.BLUE);
        mDataPaint.setStyle(Paint.Style.FILL_AND_STROKE);


        mTextPaint = new Paint();
        mTextPaint.setTextSize(20);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(Color.BLACK);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //可以在这里处理View自身的测量情况  wrap
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode ==MeasureSpec.AT_MOST){
            heightSpecSize = widthSpecSize = DEFAULTSIZE;
        }else{
            heightSpecSize = widthSpecSize = Math.min(heightSpecSize,widthSpecSize);
        }
        setMeasuredDimension(widthSpecSize,heightSpecSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterW = w/2;
        mCenterH = h/2;
        mDefaultMaxR = Math.min(w,h)/2f-30;//随便计算一下 最大值
        Log.e("SpiderView",String.format("控件默认的最大的R为%f",mDefaultMaxR));
        //防止用户设置的值 突破天际
        if(mMaxR >0f){
            mMaxR = Math.min(mDefaultMaxR,mMaxR);
        }else{
            mMaxR = mDefaultMaxR;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(spiders==null||spiders.size()<3) return; //没有值或者值小于3 直接不画
        mSizeCount = spiders.size();
        mR = mMaxR / (mCount-1);
        mAngle = (float) Math.PI*2/mSizeCount;
        canvas.translate(mCenterW,mCenterH);  //将原点移动至中心
        //分别绘制
        drawSpiderBase(canvas);
        drawSpiderSupport(canvas);
        drawSpiderData(canvas);
        drawSpiderText(canvas);
    }

    /**
     * 绘制数据区域
     * @param canvas
     */
    private void drawSpiderData(Canvas canvas){
        //防止用户设置的值太小  数据区域大于雷达区
        for(Spider spider:spiders){
            mMaxNumber = Math.max(spider.val,mMaxNumber);
        }
        Path path = new Path();
        //连接整个数据区域
        for(int i=0; i<mSizeCount; i++){
            float curR = spiders.get(i).val/mMaxNumber*mMaxR ;
            float x = (float) (curR*Math.cos(mAngle*i));
            float y = (float) (curR*Math.sin(mAngle*i));
            if(i==0){
                path.moveTo(curR,0);
            }else{
                path.lineTo(x,y);
            }
            //绘制小圆点
            canvas.drawCircle(x,y,2,mDataPaint);
        }
        path.close();
        mDataPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mDataPaint);
        mDataPaint.setAlpha(127);
        mDataPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path,mDataPaint);
    }

    /**
     * 为每一个点增加名字说明
     * @param canvas
     */
    private void drawSpiderText(Canvas canvas){
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float fontHeight = fontMetrics.descent - fontMetrics.ascent;
        mTextPaint.setTextSize(10);
        for(int i=0;i<mSizeCount;i++){
            //计算说明文字的坐标
            float x = (float)( (mMaxR + fontHeight /2)*Math.cos(mAngle*i));
            float y = (float) ((mMaxR + fontHeight /2)*Math.sin(mAngle*i));
            //记录画布位置
            canvas.save();
            canvas.translate(x,y);
            float curAngle = mAngle * i;
            //Log.e("SpiderView","curAngle"+curAngle);
            //旋转角度  摆正说明文字的位置
            canvas.rotate(-curAngle);
            float dis = mTextPaint.measureText(spiders.get(i).name);//文本长度

            //在第三象限    android上面的坐标系是顺时针计算角度和弧度。  数学的坐标系是逆时针计算角度和弧度
            if(Math.PI/2f < curAngle && curAngle < Math.PI){
                canvas.drawText(spiders.get(i).name,-dis,0,mTextPaint);

            //在第二象限
            }else if(Math.PI < curAngle &&curAngle < 3f / 2f* Math.PI){
                canvas.drawText(spiders.get(i).name,-dis,0,mTextPaint);
            }else{
                canvas.drawText(spiders.get(i).name,0,0,mTextPaint);
            }
            //恢复画布
            canvas.restore();
        }
    }


    /**
     * 链接蛛网的线
     * @param canvas
     */
    private void drawSpiderSupport(Canvas canvas) {
        for(int i=0;i<mSizeCount;i++){
            canvas.drawLine(0f,0f,(float)( mMaxR*Math.cos(mAngle*i)),(float) (mMaxR*Math.sin(mAngle*i)),mSpiderPaint);
        }
    }

    /**
     * 画出蛛网
     * @param canvas
     */
    private void drawSpiderBase(Canvas canvas) {
        Path path = new Path();
        for (int i = 1;i<mCount;i++){  //最中心的点不用画出
            path.reset();
            for(int j=0;j<mSizeCount;j++){
                if(j==0){
                    path.moveTo(mR*i,0);
                }else{
                    //利用三角函数计算 坐标x, y的值
                    path.lineTo((float) (mR*i*Math.cos(mAngle*j)),(float) (mR*i*Math.sin(mAngle*j)));
                    //Log.e("SpiderView",String.format("x,y的坐标分别为：%f,%f",(float) (mR*i*Math.cos(mAngel*j)),(float) (mR*i*Math.sin(mAngel*j))));
                }
            }
            path.close();
            canvas.drawPath(path,mSpiderPaint);
        }
    }

    /**
     * 设置蜘蛛网的层级
     */
    public void setSpiderCount(int spiderCount){
        if (spiderCount<2) return;
        mCount = spiderCount;
        invalidate();
    }

    /**
     * 设置蜘蛛网的最长半径
     */
    public void setSpiderMaxR(float spiderMaxR){
        if(mDefaultMaxR > 0f){
            mMaxR = Math.min(mDefaultMaxR,spiderMaxR);
        }else{
            mMaxR = spiderMaxR;
        }
        invalidate();
    }

    /**
     * 设置数据
     * @param spiders
     */
    public void setDate(List<Spider> spiders){
        this.spiders = spiders;
        invalidate();
    }

    /**
     * 设置R最长代表的值，用于计算实际占用比例
     * @param mMaxNumber   默认100
     */
    public void setMaxNumber(float mMaxNumber){
        this.mMaxNumber = mMaxNumber;
        invalidate();
    }

}

