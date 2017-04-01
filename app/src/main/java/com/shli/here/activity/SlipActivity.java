package com.shli.here.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.shli.here.customview.slip.BaseEntry;
import com.shli.here.customview.slip.SlipTo;
import com.shli.here.customview.slip.SlipView;
import java.util.ArrayList;
import java.util.List;

public class SlipActivity extends AppCompatActivity {
    SlipView slipViewImage;

    private String[] mImgs = new String[]{"http://www.3dmgame.com/uploads/allimg/170329/153_170329131611_1.jpg",
            "http://i2.hdslb.com/bfs/archive/0112bcb0002fbfdc986d7c438cec0ca2d8991ff1.jpg",
            "http://n.sinaimg.cn/translate/20170328/4mgT-fyctevp9023105.jpg",
            "http://i-3.yxdown.com/2015/10/30/0e84cbc6-f07f-4b1b-9619-b374727df673.jpg"};


    private String[] mTitles = new String[]{"一个美女",
            "还是一个美女",
            "是丶机器人么",
            "清明节快乐~"};

    private List<BaseEntry> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slip);
        slipViewImage = (SlipView) findViewById(R.id.slip);
        for (int j = 0; j < mImgs.length; j++) {
            BaseEntry entity = new BaseEntry();
            entity.pathurl = mImgs[j];
            entity.title = mTitles[j];
            data.add(entity);
        }
        slipViewImage.setData(data);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slipViewImage.slipTo(SlipTo.DOWN);
            }
        });
    }


}
