package com.example.nguyenngoclinh.nothingmessage.uis.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.nguyenngoclinh.nothingmessage.R;
import com.example.nguyenngoclinh.nothingmessage.custom.CustomTextView;
import com.example.nguyenngoclinh.nothingmessage.uis.BaseActivity;

public class ActivityFirst extends BaseActivity {
    private CustomTextView customTextView;
    private ImageView imageView;
    private Animation animation;
    private SharedPreferences preferences;
    private CustomTextView textView2;
    private String ID;
    private boolean isLogin;
    private String pass;

    @Override
    public int injectLayout() {
        return R.layout.activity_first;
    }

    @Override
    public void initView() {
        customTextView = findViewById(R.id.textViewFirst1);
        imageView = findViewById(R.id.imageViewFirst);
        textView2 = findViewById(R.id.textViewFirst2);


    }

    @Override
    public void initVariable() {
        preferences = getSharedPreferences("Account", Context.MODE_PRIVATE);
        isLogin = false;
        isLogin = preferences.getBoolean("isLogin", false);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                animation = AnimationUtils.loadAnimation(ActivityFirst.this, R.anim.slide_down);
                animation.setDuration(10 * 200);
                imageView.startAnimation(animation);
                customTextView.setText("");
                customTextView.setCharacterDelay(200);
                customTextView.animateText("Nothing");
                textView2.setText("");
                textView2.setCharacterDelay(200);
                textView2.animateText("Messenger");
                try {
                    Thread.sleep(17 * 160);
                    if (isLogin == true) {
                        Intent intent = new Intent(ActivityFirst.this, ActivityFriend.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(ActivityFirst.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();


//        finish();


    }
}
