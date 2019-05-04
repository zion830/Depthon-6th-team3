package com.depromeet.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.depromeet.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton mGameStartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initView() {
        mGameStartBtn = (FloatingActionButton) findViewById(R.id.fb_main_start);

        mGameStartBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_main_start:
                break;
        }
    }
}