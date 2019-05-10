package com.depromeet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.depromeet.R;
import com.depromeet.data.LoginResponse;
import com.depromeet.data.User;
import com.depromeet.network.RetrofitBuilder;
import com.depromeet.network.ServiceApi;
import com.depromeet.util.LoginManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText mUserNameEdit;
    private ProgressBar mLoginProgress;
    private Button mLoginFinishBtn;
    private LoginManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        manager = new LoginManager(this);
        initView();

        if (manager.isLoggedIn())
            startMainActivity();
    }

    private void initView() {
        mUserNameEdit = (EditText) findViewById(R.id.et_login_name);
        mLoginProgress = (ProgressBar) findViewById(R.id.progress_login);
        mLoginFinishBtn = (Button) findViewById(R.id.btn_login_finish);

        mLoginFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mUserNameEdit.getText().toString();

                if (userName.isEmpty()) {
                    Toast.makeText(LoginActivity.this, R.string.login_input_name, Toast.LENGTH_SHORT).show();
                } else {
                    showProgress();
                    requestUserLogin(userName);
                }
            }
        });
    }

    private void showProgress() {
        mLoginProgress.setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        mLoginProgress.setVisibility(View.INVISIBLE);
    }

    private void requestUserLogin(String name) {
        ServiceApi service = RetrofitBuilder.INSTANCE.getInstance();
        service.userLogin(new User(name)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse res = response.body();
                int status = res.getStatus();
                int userId = res.getUser().getId();

                if (status == 200) {
                    manager.userLogin(res.getUser().getName());
                    manager.setUserId(userId);
                    Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
                    startMainActivity();
                } else if (status == 406) {
                    Toast.makeText(LoginActivity.this, R.string.login_already_used, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, R.string.all_err, Toast.LENGTH_SHORT).show();
                }

                hideProgress();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, R.string.all_err, Toast.LENGTH_SHORT).show();
                hideProgress();
            }
        });
    }

    public void startMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}