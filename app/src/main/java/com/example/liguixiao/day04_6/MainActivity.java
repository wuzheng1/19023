package com.example.liguixiao.day04_6;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 跳过
     */
    private Button mJump;
    private ImageView mImg;
    private TextView mTv;
    private TimerTask timerTask;
    private int i=5;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                if (i>=0){
                    mTv.setText(i+"");
                    i--;
                }else{
                    timerTask.cancel();
                    startActivity(new Intent(MainActivity.this,Main2Activity.class));
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mJump = (Button) findViewById(R.id.jump);
        mJump.setOnClickListener(this);
        mImg = (ImageView) findViewById(R.id.img);
        mTv = (TextView) findViewById(R.id.tv);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(mImg, "alpha", 0, 1);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(mImg, "translationX", 0, 100);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(mImg, "rotation", 0, 360);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mImg, "scaleX", 0, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mImg, "scaleY", 0, 1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alpha,translationX,rotation,scaleX,scaleY);
        animatorSet.setDuration(4000);
        animatorSet.start();

        Timer timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        };

        timer.schedule(timerTask,1000,1000);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.jump:
                startActivity(new Intent(this,Main2Activity.class));
                break;
        }
    }
}
