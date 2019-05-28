package com.depromeet.util


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.Window
import com.depromeet.R
import com.depromeet.data.Poem
import kotlinx.android.synthetic.main.dialog_show_poem.*
import kotlinx.android.synthetic.main.layout_show_poem.*


class PoemDetailDialog(context: Context, private val userId: Int, private val poem: Poem)
    : Dialog(context, R.style.PoemDetailDialog) {
    lateinit var activity: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_show_poem)
        window?.setBackgroundDrawableResource(R.color.colorDialog)

        activity = getActivity(context)
        initView()
    }

    private fun initView() {
        val heart = activity.getDrawable(R.drawable.ic_heart_white)
        val filledHeart = activity.getDrawable(R.drawable.ic_fill_heart)
        tv_poem_name.text = poem.userName
        tv_poem_like.text = poem.likeCount.toString()
        ib_poem_like.setImageDrawable(if (poem.isLike) filledHeart else heart)

        tv_rank_title1.text = poem.wordFirst.substring(0, 1)
        tv_rank_content1.text = poem.wordFirst
        tv_rank_title2.text = poem.wordSecond.substring(0, 1)
        tv_rank_content2.text = poem.wordSecond
        tv_rank_title3.text = poem.wordThird.substring(0, 1)
        tv_rank_content3.text = poem.wordThird

        ib_back.setOnClickListener { dismiss() }
    }

    private fun getActivity(context: Context): Activity {
        return when (context) {
            is Activity -> context
            is ContextWrapper -> getActivity(context.baseContext)
            else -> error("Non Activity based context")
        }
    }
}