package com.zpproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zpproject.R;

/**
 * 作者：Terry.Chen on 2016/5/191524.
 * 邮箱：herewinner@163.com
 * 描述：#TODO
 */
public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
    }

    public void HYOnClick(View view) {
        startActivity(new Intent(this, HYGroupActivity.class));
    }

    public void JavaOnClick(View view) {
        startActivity(new Intent(this, JavaSocketActivity.class));
    }

    public void YoutubeOnClick(View view) {
        startActivity(new Intent(this, YoutubeActivity.class));
    }
}
