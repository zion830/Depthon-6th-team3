package com.depromeet.activity;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.depromeet.R;

public class GameActivity extends AppCompatActivity {
    private Handler mHandler;
    private Runnable mRunnable;
    private int mStartTime = 5;
    private int mQuizTime = 60;
    TextView mStartTimerText;
    TextView mQuizTimerText;
    TextView mQuizFirstWordText;
    TextView mQuizSecondWordText;
    TextView mQuizThirdWordText;
    EditText mQuizFirstAnswerEdit;
    EditText mQuizSecondAnswerEdit;
    EditText mQuizThirdAnswerEdit;
    FloatingActionButton mSubmitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mStartTimerText = findViewById(R.id.tv_game_beginCounter);
        mQuizTimerText = findViewById(R.id.tv_game_timer);

        mQuizFirstWordText = findViewById(R.id.tv_game_firstword);
        mQuizSecondWordText = findViewById(R.id.tv_game_secondword);
        mQuizThirdWordText = findViewById(R.id.tv_game_thirdword);

        mQuizFirstAnswerEdit = findViewById(R.id.et_game_firstword);
        mQuizSecondAnswerEdit = findViewById(R.id.et_game_secondword);
        mQuizThirdAnswerEdit = findViewById(R.id.et_game_thirdword);

        mSubmitBtn = findViewById(R.id.fb_game_submit);

        // 입력된 단어, 입력 안된 단어 색상변경
        if (mQuizFirstAnswerEdit.getText().toString().length() == 0) {
            mQuizFirstWordText.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.vacant_word_icon));
        } else {
            mQuizFirstWordText.setBackground(ContextCompat.getDrawable(GameActivity.this, R.drawable.word_icon));
        }

        // 5초 카운터
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {  //1초마다 변하는 이벤트
                mStartTimerText.setText(Integer.toString(--mStartTime));
            }

            @Override
            public void onFinish() {
                // 단어 셋팅
                mStartTimerText.setText("디프만");
                mQuizFirstWordText.setText(Character.toString(mStartTimerText.getText().charAt(0)));
                mQuizSecondWordText.setText(Character.toString(mStartTimerText.getText().charAt(1)));
                mQuizThirdWordText.setText(Character.toString(mStartTimerText.getText().charAt(2)));


                // 5초 지난 후, 단어 등장과 함께 1분 카운트 시작
                new CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        mQuizTimerText.setText(Integer.toString(--mQuizTime));
                    }

                    @Override
                    public void onFinish() { //타이머 종료시 이벤트
                        // "노력해요" 팝업창

                    }
                }.start();
            }
        }.start();

        // 제출하기 버튼 누르면
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 참!잘했어요 팝업창

                // 데이터 저장
//                Intent intent = new Intent(GameActivity.this, MainActivity.class);
//                intent.putExtra();
//                startActivityForResult(intent, 1);


            }
        });


    }
}
