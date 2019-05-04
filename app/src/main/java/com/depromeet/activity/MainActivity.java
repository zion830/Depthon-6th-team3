package com.depromeet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.depromeet.R;
import com.depromeet.adapter.PoemGridAdapter;
import com.depromeet.data.PoemData;
import com.depromeet.network.RequestURL;
import com.depromeet.network.VolleyArrayResponseListener;
import com.depromeet.network.VolleySingleton;
import com.depromeet.util.LoginManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton mGameStartBtn;
    private GridViewWithHeaderAndFooter mPoemGridView;
    private PoemGridAdapter adapter;
    private LoginManager manager;
    private ProgressBar mMainProgress;
    private ArrayList<PoemData> poems = new ArrayList<>();
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new LoginManager(this);
        initView();
    }

    private void initView() {
        View header = getLayoutInflater().inflate(R.layout.header_main, null, false);
        mGameStartBtn = (FloatingActionButton) findViewById(R.id.fb_main_start);
        mPoemGridView = (GridViewWithHeaderAndFooter) findViewById(R.id.grid_main_poem);
        mMainProgress = (ProgressBar) findViewById(R.id.progress_main);

        adapter = new PoemGridAdapter(poems);
        mPoemGridView.addHeaderView(header); // 리스트 상단 뷰 추가
        mPoemGridView.setAdapter(adapter);
        mGameStartBtn.setOnClickListener(this);
        showProgress();
        requestPoemByDateList();
    }

    private void requestPoemByDateList() {
        int userId = manager.getUserId();
        VolleySingleton helper = VolleySingleton.getInstance(this);
        helper.get(RequestURL.getPoemsByDateUrl(page, userId), new VolleyArrayResponseListener() {
            @Override
            public void onSuccess(JSONArray response) {
                Gson gson = new Gson();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject poem = response.getJSONObject(i);
                        PoemData data = gson.fromJson(poem.toString(), PoemData.class);
                        adapter.addPoem(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();
                hideProgress();
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(MainActivity.this, R.string.all_err, Toast.LENGTH_SHORT).show();
                hideProgress();
            }
        });
    }

    private void showProgress() {
        mMainProgress.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        mMainProgress.setVisibility(View.INVISIBLE);
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