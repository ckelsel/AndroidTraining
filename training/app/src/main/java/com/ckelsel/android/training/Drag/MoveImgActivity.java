package com.ckelsel.android.training.Drag;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ckelsel.android.training.R;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MoveImgActivity extends Activity {
    ImageView iv;
    private int containerWidth;
    private int containerHeight;
    float lastX, lastY;
    RelativeLayout rl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_img);

        rl = (RelativeLayout) findViewById(R.id.rl);


        iv = (ImageView) findViewById(R.id.iv);
        iv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getActionMasked()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //  不要直接用getX和getY,这两个获取的数据已经是经过处理的,容易出现图片抖动的情况
                        float distanceX = lastX - event.getRawX();
                        float distanceY = lastY - event.getRawY();

                        float nextY = iv.getY() - distanceY;
                        float nextX = iv.getX() - distanceX;

                        // 不能移出屏幕
                        if (nextY < 0) {
                            nextY = 0;
                        } else if (nextY > containerHeight - iv.getHeight()) {
                            nextY = containerHeight - iv.getHeight();
                        }
                        if (nextX < 0)
                            nextX = 0;
                        else if (nextX > containerWidth - iv.getWidth())
                            nextX = containerWidth - iv.getWidth();

                        // 属性动画移动
                        ObjectAnimator y = ObjectAnimator.ofFloat(iv, "y", iv.getY(), nextY);
                        ObjectAnimator x = ObjectAnimator.ofFloat(iv, "x", iv.getX(), nextX);

                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(x, y);
                        animatorSet.setDuration(0);
                        animatorSet.start();

                        lastX = event.getRawX();
                        lastY = event.getRawY();
                }
                return false;
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        // 这里来获取容器的宽和高
        if (hasFocus) {
            containerHeight = rl.getHeight();
            containerWidth = rl.getWidth();
        }
    }
}