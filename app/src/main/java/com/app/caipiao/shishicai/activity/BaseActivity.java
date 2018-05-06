package com.app.caipiao.shishicai.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/1/27.
 */

public class BaseActivity extends AppCompatActivity {

    public Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler(getMainLooper());
    }

    public void showMesg(String msg) {
        Toast.makeText(this, msg==null?"":msg, Toast.LENGTH_SHORT).show();
    }
}
