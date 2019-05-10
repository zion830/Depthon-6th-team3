package com.depromeet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.depromeet.R;
import com.depromeet.adapter.PoemListAdapter;
import com.depromeet.data.Poem;
import com.depromeet.network.RetrofitBuilder;
import com.depromeet.network.ServiceApi;
import com.depromeet.util.LoginManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton mGameStartBtn;
    private RecyclerView mPoemRecyclerView;
    private PoemListAdapter adapter;
    private LoginManager manager;
    private ProgressBar mMainProgress;
    private ArrayList<Poem> poems = new ArrayList<>();
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
        mPoemRecyclerView = (RecyclerView) findViewById(R.id.rv_main_poem);
        mMainProgress = (ProgressBar) findViewById(R.id.progress_main);

        adapter = new PoemListAdapter();
        mPoemRecyclerView.setAdapter(adapter);

        mGameStartBtn.setOnClickListener(this);
        showProgress();
        requestPoemsByDate();
    }

    private void requestPoemsByDate() {
        int userId = manager.getUserId();
        ServiceApi service = RetrofitBuilder.INSTANCE.getInstance();
        service.getByDate(page, userId).enqueue(new Callback<List<Poem>>() {
            @Override
            public void onResponse(Call<List<Poem>> call, Response<List<Poem>> response) {
                if (response.body() != null) {
                    poems.addAll(response.body());
                    adapter.setPoems(poems);
                    adapter.notifyDataSetChanged();
                }
                hideProgress();
            }

            @Override
            public void onFailure(Call<List<Poem>> call, Throwable t) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.clear();
        requestPoemsByDate();
    }
}