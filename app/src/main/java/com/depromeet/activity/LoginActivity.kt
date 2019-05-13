package com.depromeet.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.depromeet.R
import com.depromeet.data.LoginResponse
import com.depromeet.data.User
import com.depromeet.network.RetrofitBuilder
import com.depromeet.util.LoginManager
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    var manager: LoginManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        manager = LoginManager(this)
        initView()

        if (manager!!.isLoggedIn)
            startMainActivity()
    }

    private fun initView() {
        btn_login_finish.setOnClickListener {
            val userName = et_login_name.text.toString()

            if (userName.isEmpty()) {
                Toast.makeText(this@LoginActivity, R.string.login_input_name, Toast.LENGTH_SHORT).show()
            } else {
                showProgress()
                requestUserLogin(userName)
            }
        }
    }

    private fun showProgress() {
        progress_login.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress_login.visibility = View.INVISIBLE
    }

    private fun requestUserLogin(name: String) {
        val service = RetrofitBuilder.getInstance()
        service.userLogin(User(name)).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val res = response.body()

                if (response.code() == 200) {
                    manager!!.userLogin(res!!.user.id, res.user.name)
                    Toast.makeText(this@LoginActivity, R.string.login_success, Toast.LENGTH_SHORT).show()
                    startMainActivity()
                } else if (response.code() == 406) {
                    Toast.makeText(this@LoginActivity, R.string.login_already_used, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, R.string.all_err, Toast.LENGTH_SHORT).show()
                }

                hideProgress()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, R.string.all_err, Toast.LENGTH_SHORT).show()
                hideProgress()
            }
        })
    }

    fun startMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}