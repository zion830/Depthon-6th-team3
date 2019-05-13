package com.depromeet.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import com.depromeet.R
import com.depromeet.adapter.PoemListAdapter
import com.depromeet.data.Poem
import com.depromeet.network.RetrofitBuilder
import com.depromeet.util.LoginManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.textColor
import org.jetbrains.anko.textColorResource
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, Callback<List<Poem>> {
    private var adapter: PoemListAdapter? = null
    private var manager: LoginManager? = null
    private val poems = ArrayList<Poem>()
    private var page = 0
    private var selectedBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = LoginManager(this)
        selectedBtn = btn_main_latest
        initView()
    }

    private fun initView() {
        val header = layoutInflater.inflate(R.layout.header_main, null, false)
        adapter = PoemListAdapter()
        rv_main_poem.adapter = adapter

        fb_main_start.setOnClickListener(this)
        showProgress()
        requestPoems(btn_main_latest)

        btn_main_latest.setOnClickListener(this)
        btn_main_favorite.setOnClickListener(this)
        btn_main_mine.setOnClickListener(this)
    }

    private fun requestPoems(state: Button?) {
        val userId = manager!!.userId
        val service = RetrofitBuilder.getInstance()
        adapter!!.clear()

        when (state) {
            btn_main_latest ->
                service.getByDate(page, userId).enqueue(this)
            btn_main_favorite ->
                service.getByLike(page, userId).enqueue(this)
            else ->
                service.getMyPoems(userId).enqueue(this)
        }
    }

    override fun onResponse(call: Call<List<Poem>>, response: Response<List<Poem>>) {
        if (response.body() != null) {
            poems.addAll(response.body()!!)
            showNoItemMsg(poems.size == 0)

            adapter!!.poems = poems
            adapter!!.notifyDataSetChanged()
        }
        hideProgress()
    }

    override fun onFailure(call: Call<List<Poem>>, t: Throwable) {
        toast(R.string.all_err)
        hideProgress()
    }

    private fun showNoItemMsg(isEmpty: Boolean) {
        tv_main_no_item.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    private fun showProgress() {
        progress_main!!.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progress_main!!.visibility = View.INVISIBLE
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fb_main_start -> {
                val startGame = Intent(this@MainActivity, GameActivity::class.java)
                startActivity(startGame)
            }

            R.id.btn_main_latest -> {
                changeSortBtnRes(btn_main_latest)
                requestPoems(selectedBtn)
            }
            R.id.btn_main_favorite -> {
                changeSortBtnRes(btn_main_favorite)
                requestPoems(btn_main_favorite)
            }
            R.id.btn_main_mine -> {
                changeSortBtnRes(btn_main_mine)
                requestPoems(btn_main_mine)
            }
        }
    }

    private fun changeSortBtnRes(state: Button?) {
        selectedBtn!!.setBackgroundResource(R.drawable.back_stoke_green2)
        selectedBtn!!.textColorResource = R.color.colorDarkerGray
        selectedBtn = state
        selectedBtn!!.setBackgroundResource(R.drawable.back_fill_red)
        selectedBtn!!.textColorResource = R.color.colorWhite
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}