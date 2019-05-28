package com.depromeet.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.bumptech.glide.Glide
import com.depromeet.R
import com.depromeet.adapter.PoemListAdapter
import com.depromeet.data.BasicResponse
import com.depromeet.data.LikeRequest
import com.depromeet.data.Poem
import com.depromeet.network.RetrofitBuilder
import com.depromeet.network.ServiceApi
import com.depromeet.util.BackPressCloseHandler
import com.depromeet.util.LoginManager
import com.depromeet.util.PoemDetailDialog
import com.depromeet.util.RecyclerItemClickListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_show_poem.*
import kotlinx.android.synthetic.main.header_main.*
import kotlinx.android.synthetic.main.header_main_btns.*
import kotlinx.android.synthetic.main.layout_main_rank_poem.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.textColorResource
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, Callback<List<Poem>> {
    private val START_GAME = 1
    private lateinit var adapter: PoemListAdapter
    private lateinit var manager: LoginManager
    private lateinit var selectedSortBtn: Button
    private var selectedRank: Int = 0
    private lateinit var rankBtns: ArrayList<ImageButton>
    private lateinit var rankTexts: ArrayList<TextView>
    private lateinit var closeHandler: BackPressCloseHandler
    private var page = 0
    private lateinit var service: ServiceApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        manager = LoginManager(this)
        service = RetrofitBuilder.getInstance()
        selectedSortBtn = btn_main_latest
        rankBtns = arrayListOf(ib_main_1st, ib_main_2nd, ib_main_3rd)
        rankTexts = arrayListOf(tv_rank_1st, tv_rank_2nd, tv_rank_3rd)
        Thread().run { initImg() }
        initView()
        closeHandler = BackPressCloseHandler(this)
    }

    private fun initImg() {
        Glide.with(this).load(R.drawable.img_main_boy).into(iv_main_boy)
    }

    private fun initView() {
        adapter = PoemListAdapter(this)
        rv_main_poem.adapter = adapter

        fb_main_start.setOnClickListener(this)
        showProgress()
        requestPoems(btn_main_latest)
        getPrizedPoem(selectedRank)

        rv_main_poem.addOnItemTouchListener(
                RecyclerItemClickListener(this, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) = showPoemDetailDialog(position)
                })
        )

        rv_main_poem.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastPosition = (recyclerView.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                val total = recyclerView.adapter!!.itemCount

                if (lastPosition >= total - 1) {
                    page++
                    requestPoems(btn_main_latest)
                }
            }
        })

        btn_main_latest.setOnClickListener(this)
        btn_main_favorite.setOnClickListener(this)
        btn_main_mine.setOnClickListener(this)
        ib_main_1st.setOnClickListener(this)
        ib_main_2nd.setOnClickListener(this)
        ib_main_3rd.setOnClickListener(this)
    }

    private fun setPoemToBoard(name: String, poem: Poem) {
        tv_main_rank_name.text = name + "님"
        tv_main_rank_like.text = poem.likeCount.toString()

        tv_rank_title1.text = poem.wordFirst.substring(0, 1)
        tv_rank_title2.text = poem.wordSecond.substring(0, 1)
        tv_rank_title3.text = poem.wordThird.substring(0, 1)
        tv_rank_content1.text = poem.wordFirst
        tv_rank_content2.text = poem.wordSecond
        tv_rank_content3.text = poem.wordThird
    }

    private fun showNoItemMsg(isEmpty: Boolean) {
        tv_main_no_item.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    private fun showPoemDetailDialog(position: Int) {
        val heart = getDrawable(R.drawable.ic_heart_white)
        val filledHeart = getDrawable(R.drawable.ic_fill_heart)
        val item = adapter.getItem(position)
        val dialog = PoemDetailDialog(this, manager.userId, item)
        dialog.show()

        dialog.ib_poem_like.setOnClickListener {
            var likeCount = Integer.valueOf(tv_poem_like.text.toString())
            if (!item.isLike) {
                service.increaseLike(LikeRequest(item.id, manager.userId)).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                        item.likeCount = (likeCount + 1)
                        item.isLike = true
                        dialog.tv_poem_like.text = item.likeCount.toString()
                        dialog.ib_poem_like.setImageDrawable(filledHeart)
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        toast(R.string.all_err)
                    }
                })
            } else {
                service.decreaseLike(LikeRequest(item.id, manager.userId)).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                        item.likeCount = (likeCount - 1)
                        item.isLike = false
                        dialog.tv_poem_like.text = item.likeCount.toString()
                        dialog.ib_poem_like.setImageDrawable(heart)
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        toast(R.string.all_err)
                    }
                })
            }

            getPrizedPoem(selectedRank)
            adapter.setPoem(position, item)
        }
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
            R.id.btn_main_latest -> changeSortBtnRes(btn_main_latest)
            R.id.btn_main_favorite -> changeSortBtnRes(btn_main_favorite)
            R.id.btn_main_mine -> changeSortBtnRes(btn_main_mine)
            R.id.ib_main_1st -> toggleRankBtnRes(0)
            R.id.ib_main_2nd -> toggleRankBtnRes(1)
            R.id.ib_main_3rd -> toggleRankBtnRes(2)
        }
    }

    private fun toggleRankBtnRes(index: Int) { // 1-3위 랭킹 버튼 이미지 리소스 변경
        if (index != selectedRank) {
            rankBtns[selectedRank].imageResource = getRankBarResId(selectedRank, false)
            rankTexts[selectedRank].visibility = View.INVISIBLE
            selectedRank = index
            rankBtns[index].imageResource = getRankBarResId(index, true)
            rankTexts[index].visibility = View.VISIBLE
            getPrizedPoem(selectedRank)
        }
    }

    private fun getRankBarResId(index: Int, isSelected: Boolean): Int = when (index) {
        0 -> if (isSelected) R.drawable.ic_rank_1st_select else R.drawable.ic_rank_1st
        1 -> if (isSelected) R.drawable.ic_rank_2nd_select else R.drawable.ic_rank_2nd
        else -> if (isSelected) R.drawable.ic_rank_3rd_select else R.drawable.ic_rank_3rd
    }

    private fun changeSortBtnRes(state: Button) {
        requestPoems(state)
        page = 0

        selectedSortBtn.setBackgroundResource(R.drawable.back_stoke_green2)
        selectedSortBtn.textColorResource = R.color.colorDarkerGray
        selectedSortBtn = state
        selectedSortBtn.setBackgroundResource(R.drawable.back_fill_red)
        selectedSortBtn.textColorResource = R.color.colorWhite
    }

    private fun requestPoems(state: Button?) {
        val userId = manager.userId
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

    private fun getPrizedPoem(rank: Int) {
        if (selectedSortBtn == btn_main_favorite) {
            setPoemToBoard(adapter.getItem(rank).userName, adapter.getItem(rank))
        } else {
            service.getByLike(0, manager.userId).enqueue(object : Callback<List<Poem>> {
                override fun onResponse(call: Call<List<Poem>>, response: Response<List<Poem>>) {
                    if (response.body() != null) {
                        val result = response.body()!!
                        setPoemToBoard(result[rank].userName, result[rank])
                    }
                }

                override fun onFailure(call: Call<List<Poem>>, t: Throwable) {
                    toast(R.string.all_rank_err)
                }
            })
        }
    }

    override fun onResponse(call: Call<List<Poem>>, response: Response<List<Poem>>) {
        if (response.body() != null) {
            val result: ArrayList<Poem> = ArrayList(response.body()!!)
            showNoItemMsg(result.isEmpty())

            adapter.addPoems(result)
            adapter.notifyDataSetChanged()
        }
        hideProgress()
    }

    override fun onFailure(call: Call<List<Poem>>, t: Throwable) {
        toast(R.string.all_err)
        hideProgress()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        requestPoems(selectedSortBtn)
    }

    override fun onBackPressed() = closeHandler.onBackPressed()
}