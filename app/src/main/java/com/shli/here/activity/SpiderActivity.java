package com.shli.here.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.shli.here.customview.spider.Spider;
import com.shli.here.customview.spider.SpiderView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpiderActivity extends AppCompatActivity {
    List<Spider> list = new ArrayList<>();
    Random random = new Random();
    private SpiderView spiderView;
    private float baseMaxNumber = 200;
    private int spiderCount = 1;
    private int spiderR = 80;
    private int dataSize = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spider);
        spiderView = (SpiderView)findViewById(R.id.spider);
        romdomSpider(7);
        spiderView.setSpiderCount(6);
        //spiderView.setSpiderMaxR(spiderR)
        spiderView.setMaxNumber(baseMaxNumber);
        spiderView.setDate(list);
    }


    /**
     * 线长  对应R对应的数值
     * @param view
     */
    public void setMaxNumber(View view){
        baseMaxNumber++;
        spiderView.setMaxNumber(baseMaxNumber);
    }

    /**
     * 蛛网的层数
     * @param view
     */
    public void setSpiderCount(View view){
        spiderCount++;
        spiderView.setSpiderCount(spiderCount);
    }


    /**
     * 数据  决定蛛网是几边形
     * @param view
     */
    public void setData(View view){
        dataSize++;
        romdomSpider(dataSize);
        spiderView.setDate(list);
    }

    /**
     * 半径  决定蛛网的大小
     * @param view
     */
    public void setSpiderR(View view){
        spiderR += 10;
        spiderView.setSpiderMaxR(spiderR);
    }


    private void romdomSpider(int num){
        list.clear();
        for(int i=0;i<num;i++){
            list.add(new Spider("名字"+i,random.nextInt(200)));
        }
    }
}
