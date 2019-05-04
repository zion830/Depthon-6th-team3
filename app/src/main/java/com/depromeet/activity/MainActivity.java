package com.depromeet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

import com.depromeet.R;
import com.depromeet.util.LoginManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton mGameStartBtn;
    private GridView mPoemGridView;
    private LoginManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new LoginManager(this);
        initView();
    }

    private void initView() {
        mGameStartBtn = (FloatingActionButton) findViewById(R.id.fb_main_start);
        mPoemGridView = (GridView) findViewById(R.id.grid_main_poem);

        mGameStartBtn.setOnClickListener(this);
    }

    private void requestPoemList() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fb_main_start:
                Intent startGame = new Intent(MainActivity.this, GameActivity.class);
                startActivity(startGame);
                break;
        }
    }
}