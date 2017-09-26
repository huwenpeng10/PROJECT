package huwenpeng.project.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import huwenpeng.project.R;


/**
 * Created by Administrator on 2017/1/6 0006.
 */
public class PagerActivity extends Activity {
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_main);
        init();
    }

    private void init() {
        viewPager =(ViewPager) findViewById(R.id.viewpager);

        LayoutInflater mLi = LayoutInflater.from(this);
        View view1 = mLi.inflate(R.layout.pagerfirst, null);
        View view2 = mLi.inflate(R.layout.pagersecond, null);
        View view3 = mLi.inflate(R.layout.pagerthree, null);
        View view4 = mLi.inflate(R.layout.pagerfour,null);

        final ArrayList<View> views = new ArrayList<View>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);

        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override//进行比较
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
            @Override//确定页面的个数
            public int getCount() {
                return views.size();
            }

            @Override//确定需要加载的页面
            public Object instantiateItem(ViewGroup container, int position) {
                ((ViewPager)container).addView(views.get(position));
                return views.get(position);
            }

            @Override//确定要删除的页面
            public void destroyItem(ViewGroup container, int position, Object object) {
                ((ViewPager)container).removeView(views.get(position));
            }
        };

        viewPager.setAdapter(pagerAdapter);
    }

    public void open (View v){
        //启动动画界面
        Intent intent = new Intent(this,DoorActivity.class);
        startActivity(intent);
        finish();
    }
}
