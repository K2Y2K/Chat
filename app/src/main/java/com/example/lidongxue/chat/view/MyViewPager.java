package com.example.lidongxue.chat.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lidongxue on 17-9-28.
 */

public class MyViewPager extends ViewPager implements ViewPager.OnPageChangeListener {
    //描述：禁止了ViewPager的滑动 true 代表不能滑动 //false 代表能滑动
    private boolean noScroll = true;
    public MyViewPager(Context context) {
        super(context);
    }
    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        super.addOnPageChangeListener(listener);
    }
    @Override
    public void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
    }

    public void onPageSelected(int position) {

    }




    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        /* return false;//super.onTouchEvent(arg0); */
        /*if (noScroll)
            return false;
        else
            return super.onTouchEvent(arg0);*/
        return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        /*if (noScroll)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);*/
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,false);//表示切换的时候，不需要切换时间。
    }
}
