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

import com.android.volley.VolleyError;
import com.depromeet.R;
import com.depromeet.network.RequestURL;
import com.depromeet.network.VolleyResponseListener;
import com.depromeet.network.VolleySingleton;
import com.depromeet.util.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements VolleyResponseListener {
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
        VolleySingleton helper = VolleySingleton.getInstance(this);
        JSONObject userData = new JSONObject();
        try {
            userData.put("name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        helper.post(RequestURL.LOGIN, userData, this);
    }

    public void startMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccess(JSONObject response) {
        String userName = mUserNameEdit.getText().toString();
        int state = 0;

        try {
            state = response.getInt("status");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (state == 200) {
            manager.userLogin(userName);
            Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show();
            startMainActivity();
        } else if (state == 406) {
            Toast.makeText(this, R.string.login_already_used, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.all_err, Toast.LENGTH_SHORT).show();
        }

        hideProgress();
    }

    @Override
    public void onError(VolleyError error) {
        Toast.makeText(this, R.string.all_err, Toast.LENGTH_SHORT).show();

        Log.d("err", error.toString());
        hideProgress();
    }
}