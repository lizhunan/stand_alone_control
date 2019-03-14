/*
 * Copyright (c) 2017.
 *  山东冠世信息工程有限公司 版权所有
 *  创建者 李竹楠
 */

package com.guanshinfo.win7.stand_alone_control.ui.indexui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 这里是自定义View
 *
 * @author dlq
 */
public class QuickIndexView extends View {
    private float itemWidth;
    private float itemHeight;
    // private float wordWidth;
    // private float wordHeight;
    private String[] indexArr = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};
    private Paint paint;

    public QuickIndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(16);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        itemWidth = this.getMeasuredWidth();
        itemHeight = this.getMeasuredHeight() / 26f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //当每次触发重绘的时候，就把26个字母循环一遍
        for (int i = 0; i < indexArr.length; i++) {
            String word = indexArr[i];
            // 设置文字的颜色
            if (i == touchIndex) {
                //这里设置被点击的字母变化：颜色变灰色、字体变25sp
                paint.setColor(Color.GRAY);
                paint.setTextSize(25);
            } else {
                //其他没被点击的字母，保持原有状态：设置颜色、字体大小为18sp
                paint.setColor(Color.BLACK);
                paint.setTextSize(18);
            }
            // 得到word的宽高
            Rect bounds = new Rect();
            paint.getTextBounds(word, 0, word.length(), bounds);
            //得到字体的宽
            int wordWidth = bounds.width();
            //得到字体的高
            int wordHeight = bounds.height();
            // 计算word的左上角的坐标：字母所在的X坐标、Y坐标
            float x = itemWidth / 2 - wordWidth / 2;
            float y = itemHeight / 2 + wordHeight / 2 + i * itemHeight;
            // 绘制word
            canvas.drawText(word, x, y, paint);
        }
    }

    // ///////////////////////////////////////////////////////////////////////
    private int touchIndex = -1;// 触摸的字母的下标

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 得到事件坐标
        float eventY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                // 计算下标
                int index = (int) (eventY / itemHeight);
                if (index > 25) {
                    index = 25;
                }
                if (index < 0) {
                    index = 0;
                }
                // 如果下标有改变, 强制重绘
                if (index != touchIndex) {
                    // 更新touchIndex
                    touchIndex = index;
                    // 强制重绘
                    invalidate();
                    // 通知Activity更新TextView
                    if (onIndexChangedListener != null) {
                        onIndexChangedListener.onIndexChanged(indexArr[index]);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                touchIndex = -1;
                // 强制重绘
                invalidate();
                // 通知Activity更新TextView
                if (onIndexChangedListener != null) {
                    onIndexChangedListener.onUp();
                }
                break;
            default:
                break;
        }
        return true;// 所有的事件都由当前视图消费
    }

    private OnIndexChangedListener onIndexChangedListener;

    /*
     * 设置监听对象的方法 这个方法一般是Activity调用
     */
    public void setOnIndexChangedListener(
            OnIndexChangedListener onIndexChangedListener) {
        this.onIndexChangedListener = onIndexChangedListener;
    }

    public interface OnIndexChangedListener {
        // 当操作的下标改变时自动调用
        public void onIndexChanged(String word);

        // 当up时调用
        public void onUp();
    }
}