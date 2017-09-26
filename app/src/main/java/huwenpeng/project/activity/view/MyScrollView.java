package huwenpeng.project.activity.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by Administrator on 2017/1/9 0009.
 */
public class MyScrollView extends HorizontalScrollView{
    //父容器
    private LinearLayout linearLayout;
    //菜单
    private ViewGroup viewGroup;
    //内容
    private ViewGroup mMainView;
    //屏幕的宽度
    private int mSrieenWidth ;
    //菜单的右边距
    private int mMenuRightPadding;
    //菜单的kuand
    private int mMenuWidth;
    //避免多调用测量的标置
    private boolean flag = false;
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取屏幕的宽高
        WindowManager wn =
                (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wn.getDefaultDisplay().getMetrics(metrics);
        //屏幕的款
        mSrieenWidth = metrics.widthPixels;

        mMenuRightPadding = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,
                context.getResources().getDisplayMetrics());
    }

    @Override//用来测量宽高 菜单和主界面
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if(!flag){
            linearLayout = (LinearLayout) getChildAt(0);
            viewGroup = (ViewGroup) linearLayout.getChildAt(0);
            mMainView = (ViewGroup) linearLayout.getChildAt(1);
            //菜单的宽度  = 屏幕-右边距
            mMenuWidth = viewGroup.getLayoutParams().
                    width = mSrieenWidth - mMenuRightPadding;
            //内容的宽度  = 屏幕的宽度
            mMainView.getLayoutParams().width = mSrieenWidth;

            flag = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override//设置view的位置
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //刚载入界面是影藏菜单
        if(changed){
            this.scrollTo(mMenuWidth,0);//正数表示向左滑动
        }
    }

    @Override//设置偏移量来隐藏菜单
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
                //左边隐藏的x轴区域
                int scrollx = getScrollX();
                //如果因残区域超过菜单宽度的一半 不能显示隐藏区域
                if(scrollx > mMenuWidth/2){
                    this.smoothScrollTo(mMenuWidth,0);
                }else {
                    this.smoothScrollTo(0,0);
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f / mMenuWidth;
        float rightScale = 0.7f+0.3f * scale;
        float leftScale = 1.0f - scale * 0.3f;
        float leftAlpha = 0.6f + 0.4f * (1-scale);

        ViewHelper.setTranslationX(viewGroup, mMenuWidth * scale * 0.8f);
        //设置缩放的位置
        ViewHelper.setPivotX(mMainView, 0);
        ViewHelper.setPivotY(mMainView, mMainView.getHeight() / 2);

        ViewHelper.setScaleX(viewGroup, leftScale);
        ViewHelper.setScaleY(viewGroup, leftScale);
        //设置透明度
        ViewHelper.setAlpha(viewGroup,leftAlpha);

        ViewHelper.setScaleX(mMainView,rightScale);
        ViewHelper.setScaleY(mMainView,rightScale);
    }
}
