package huwenpeng.project.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import huwenpeng.project.R;


/**
 * Created by Administrator on 2017/1/7 0007.
 */
public class DoorActivity extends Activity {

    private ImageView w_left;
    private ImageView w_right;
    private TextView w_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dooractivity_main);
        init();
    }

    private void init() {
        w_left = (ImageView)findViewById(R.id.w_left);
        w_right = (ImageView)findViewById(R.id.w_right);
        w_text = (TextView)findViewById(R.id.w_text);

        AnimationSet set = new AnimationSet (true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.2f);
//        ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 2.0f, 0.5f, 2.0f, Animation.RELATIVE_TO_PARENT,
//                0.8f, Animation.RELATIVE_TO_PARENT, 0.8f);
        set.addAnimation(alphaAnimation);
        //set.addAnimation(scaleAnimation);
        set.setDuration(2000);

        w_text.startAnimation(set);

        Animation animation = new TranslateAnimation(0,
                -getWindowManager().getDefaultDisplay().getWidth(), 0, 0);//向左移动的动画效果
        animation.setDuration(2000);//设置动画过程时间
        animation.setStartOffset(1000);//设置动画延时时间//一秒后开

        Animation animation1 = new TranslateAnimation(0,
                (float) (getWindowManager().getDefaultDisplay().getWidth()),0 ,0);//向右移动的动画效果
        animation1.setStartOffset(1000);
        animation1.setDuration(2000);

        w_left.startAnimation(animation);
        w_right.startAnimation(animation1);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(DoorActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
