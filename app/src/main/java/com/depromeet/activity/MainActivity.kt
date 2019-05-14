package com.depromeet.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.depromeet.R
import com.depromeet.adapter.PoemListAdapter
import com.depromeet.data.Poem
import com.depromeet.network.RetrofitBuilder
import com.depromeet.util.BackPressCloseHandler
import com.depromeet.util.LoginManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_main.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.textColorResource
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, Callback<List<Poem>> {
    private val START_GAME = 1
    private val poems = ArrayList<Poem>()
    private lateinit var adapter: PoemListAdapter
    private lateinit var manager: LoginManager
    private lateinit var selectedSortBtn: Button
    private var selectedRankBtn: Int = 0
    private lateinit var rankBtns: ArrayList<ImageButton>
    private lateinit var rankTexts: ArrayList<TextView>
    private lateinit var closeHandler: BackPressCloseHandler
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = LoginManager(this)
        selectedSortBtn = btn_main_latest
        rankBtns = arrayListOf(ib_main_1st, ib_main_2nd, ib_main_3rd)
        rankTexts = arrayListOf(tv_rank_1st, tv_rank_2nd, tv_rank_3rd)
        initView()

        closeHandler = BackPressCloseHandler(this)
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
        ib_main_1st.setOnClickListener(this)
        ib_main_2nd.setOnClickListener(this)
        ib_main_3rd.setOnClickListener(this)
    }

    private fun requestPoems(state: Button?) {
        val userId = manager.userId
        val service = RetrofitBuilder.getInstance()
        adapter.clear()

        when (state) {
            btn_main_latest ->
                service.getByDate(page, userId).enqueue(this)
            btn_main_favorite ->
                service.getByLike(page, userId).enqueue(this)
            else ->
                service.getMyPoems(userId, page).enqueue(this)
        }
    }

    override fun onResponse(call: Call<List<Poem>>, response: Response<List<Poem>>) {
        if (response.body() != null) {
            poems.addAll(response.body()!!)
            showNoItemMsg(poems.size == 0)

            adapter.poems = poems
            adapter.notifyDataSetChanged()
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
        fb_main_start.isClickable = false
    }

    private fun hideProgress() {
        progress_main!!.visibility = View.INVISIBLE
        fb_main_start.isClickable = true
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fb_main_start -> {
                val startGame = Intent(this@MainActivity, GameActivity::class.java)
                startActivityForResult(startGame, START_GAME)
            }
            R.id.btn_main_latest -> {
                changeSortBtnRes(btn_main_latest)
                requestPoems(selectedSortBtn)
                page = 0
            }
            R.id.btn_main_favorite -> {
                changeSortBtnRes(btn_main_favorite)
                requestPoems(btn_main_favorite)
                page = 0
            }
            R.id.btn_main_mine -> {
                changeSortBtnRes(btn_main_mine)
                requestPoems(btn_main_mine)
                page = 0
            }
            R.id.ib_main_1st -> {
                toggleRankBtnRes(0)
            }
            R.id.ib_main_2nd -> {
                toggleRankBtnRes(1)
            }
            R.id.ib_main_3rd -> {
                toggleRankBtnRes(2)
            }
        }
    }

    private fun toggleRankBtnRes(index: Int) {
        if (index != selectedRankBtn) {
            rankBtns[selectedRankBtn].imageResource = getRankBarResId(selectedRankBtn, false)
            rankTexts[selectedRankBtn].visibility = View.INVISIBLE
            selectedRankBtn = index
            rankBtns[index].imageResource = getRankBarResId(index, true)
            rankTexts[index].visibility = View.VISIBLE
        }
    }

    private fun getRankBarResId(index: Int, isSelected: Boolean): Int = when (index) {
        0 -> if (isSelected) R.drawable.ic_rank_1st_select else R.drawable.ic_rank_1st
        1 -> if (isSelected) R.drawable.ic_rank_2nd_select else R.drawable.ic_rank_2nd
        else -> if (isSelected) R.drawable.ic_rank_3rd_select else R.drawable.ic_rank_3rd
    }

    private fun changeSortBtnRes(state: Button) {
        selectedSortBtn.setBackgroundResource(R.drawable.back_stoke_green2)
        selectedSortBtn.textColorResource = R.color.colorDarkerGray
        selectedSortBtn = state
        selectedSortBtn.setBackgroundResource(R.drawable.back_fill_red)
        selectedSortBtn.textColorResource = R.color.colorWhite
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        requestPoems(selectedSortBtn)
    }

    override fun onBackPressed() = closeHandler.onBackPressed()
}