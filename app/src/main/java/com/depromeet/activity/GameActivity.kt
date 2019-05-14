package com.depromeet.activity

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import com.depromeet.R
import com.depromeet.customView.GameResultDialog
import com.depromeet.data.BasicResponse
import com.depromeet.data.PoemRequest
import com.depromeet.data.WordResponse
import com.depromeet.network.RetrofitBuilder
import com.depromeet.network.ServiceApi
import com.depromeet.util.LoginManager
import kotlinx.android.synthetic.main.activity_game.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GameActivity : AppCompatActivity() {
    private val interval = 1000L
    private var waiting = 3L
    private var limitTime = 60L
    private var word = "삼행시"
    private lateinit var manager: LoginManager
    private lateinit var service: ServiceApi
    private lateinit var initTimer: CountDownTimer
    private lateinit var gameTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        manager = LoginManager(this)
        service = RetrofitBuilder.getInstance()
        requestRandomWord()

        initView()
        initTimers()
        initTimer.start()
    }

    private fun initView() {
        tv_game_board.text = waiting.toString()
        tv_game_timer.text = limitTime.toString()
        fb_game_submit.setOnClickListener {
            if (TextUtils.isEmpty(et_game_word1.text)
                    || TextUtils.isEmpty(et_game_word2.text)
                    || TextUtils.isEmpty(et_game_word3.text)) {
                toast(R.string.game_not_input)
            } else {
                requestSavePoem()
            }
        }
    }

    private fun initTimers() {
        gameTimer = object : CountDownTimer(limitTime * interval, interval) {
            override fun onTick(millisUntilFinished: Long) {
                limitTime--
                tv_game_timer.text = limitTime.toString()
            }

            override fun onFinish() {
                showBadDialog()
            }
        }

        initTimer = object : CountDownTimer(waiting * interval, interval) {
            override fun onTick(millisUntilFinished: Long) {
                waiting--
                tv_game_board.text = waiting.toString()
            }

            override fun onFinish() {
                tv_game_board.text = word
                tv_game_word1.text = word.substring(0, 1)
                tv_game_word2.text = word.substring(1, 2)
                tv_game_word3.text = word.substring(2)
                et_game_word1.isEnabled = true
                et_game_word2.isEnabled = true
                et_game_word3.isEnabled = true

                // 첫번째 행에 키보드 포커스 할당
                et_game_word1.requestFocus()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(et_game_word1, 0)

                gameTimer.start()
            }
        }
    }

    private fun showGoodDialog() {
        val dialog = GameResultDialog(this, true)
        dialog.show()
    }

    private fun showBadDialog() {
        fb_game_submit.isClickable = false
        et_game_word1.isEnabled = false
        et_game_word2.isEnabled = false
        et_game_word3.isEnabled = false
        val dialog = GameResultDialog(this, false)
        dialog.show()
    }

    private fun requestRandomWord() {
        service.getRandomWord().enqueue(object : Callback<WordResponse> {
            override fun onResponse(call: Call<WordResponse>, response: Response<WordResponse>) {
                word = response.body()!!.word.hangshi
            }

            override fun onFailure(call: Call<WordResponse>, t: Throwable) {
                toast(R.string.all_err)
            }
        })
    }

    private fun requestSavePoem() {
        val poem = PoemRequest(manager.userName, et_game_word1.text.toString(),
                et_game_word2.text.toString(), et_game_word3.text.toString())

        service.savePoem(poem).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.code() == 200) {
                    toast(R.string.game_finish)
                    showGoodDialog()
                } else {
                    toast(R.string.all_err)
                    finish()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                toast(R.string.all_err)
            }
        })
    }

    override fun onBackPressed() {
        if (limitTime > 0) {
            alert(R.string.game_back) {
                yesButton { super.onBackPressed() }
                noButton {  }
            }.show()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            initTimer.cancel()
            gameTimer.cancel()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
