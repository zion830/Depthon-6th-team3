package com.depromeet.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.depromeet.R
import com.depromeet.customView.GameResultDialog
import com.depromeet.data.Poem
import com.depromeet.data.Word
import com.depromeet.data.WordResponse
import com.depromeet.network.RetrofitBuilder
import com.depromeet.network.ServiceApi
import com.depromeet.util.LoginManager
import kotlinx.android.synthetic.main.activity_game.*
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameActivity : AppCompatActivity() {
    private var mStartTime = 3
    private var mQuizTime = 60
    private var word = ""
    private var manager: LoginManager? = null
    private var service: ServiceApi? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        manager = LoginManager(this)
        service = RetrofitBuilder.getInstance()
        requestRandomWord()

        fb_game_submit.setOnClickListener {
            if (TextUtils.isEmpty(et_game_word1.text) || TextUtils.isEmpty(et_game_word2.text)
                    || TextUtils.isEmpty(et_game_word3.text)) {
                toast(R.string.game_not_input)
            } else {

            }
        }
    }

    private fun showGoodDialog() {
        val dialog = GameResultDialog(this, true)
        dialog.show()
    }

    private fun showBadDialog() {
        val dialog = GameResultDialog(this, false)
        dialog.show()
    }

    private fun requestRandomWord() {
        service!!.getRandomWord().enqueue(object : Callback<WordResponse> {
            override fun onResponse(call: Call<WordResponse>, response: Response<WordResponse>) {
                word = response.body()!!.word.hangshi
            }

            override fun onFailure(call: Call<WordResponse>, t: Throwable) {
                toast(R.string.all_err)
            }
        })
    }

    fun requestSavePoem() {
        val poem = Poem(manager!!.userId, manager!!.userName, et_game_word1.text.toString(),
                et_game_word2.text.toString(), et_game_word3.text.toString(), 0, false)

        service!!.savePoem(poem).enqueue(object : Callback<com.depromeet.data.BasicResponse> {
            override fun onResponse(call: Call<com.depromeet.data.BasicResponse>, response: Response<com.depromeet.data.BasicResponse>) {
            }

            override fun onFailure(call: Call<com.depromeet.data.BasicResponse>, t: Throwable) {
                toast(R.string.all_err)
            }
        })
    }
}
