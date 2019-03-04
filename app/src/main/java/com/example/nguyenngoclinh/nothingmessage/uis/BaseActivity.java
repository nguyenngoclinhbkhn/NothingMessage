package com.example.nguyenngoclinh.nothingmessage.uis;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(injectLayout());

        initView();

        initVariable();
    }

    public abstract int injectLayout();

    public abstract void initView();

    public abstract void initVariable();
}
