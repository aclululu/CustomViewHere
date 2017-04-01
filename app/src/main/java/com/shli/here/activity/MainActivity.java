package com.shli.here.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * spiderView
     * 打开蛛网自定义视图界面
     * @param view
     */
    public void spiderView(View view){
        Intent intent = new Intent(this,SpiderActivity.class);
        startActivity(intent);

    }

    /**
     * slipView
     * 打开图片滑动浏览视图界面
     * @param view
     */
    public void slipView(View view){
        Intent intent = new Intent(this,SlipActivity.class);
        startActivity(intent);
    }


}
