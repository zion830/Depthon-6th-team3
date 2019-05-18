package com.depromeet.util


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.MotionEvent
import android.view.Window
import com.depromeet.R
import com.depromeet.data.Poem
import kotlinx.android.synthetic.main.dialog_show_poem.*
import kotlinx.android.synthetic.main.layout_show_poem.*


class PoemDetailDialog(context: Context, private val poem: Poem)
    : Dialog(context, R.style.PoemDetailDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_show_poem)
        window?.setBackgroundDrawableResource(R.color.colorDialog)

        initView()
    }

    private fun initView() {
        tv_poem_name.text = poem.userName
        tv_poem_like.text = poem.likeCount.toString()
        ib_poem_like.setImageDrawable(
                getActivity(context).getDrawable(
                        if (poem.isLike) R.drawable.ic_fill_heart else R.drawable.ic_heart_white))

        tv_poem_title1.text = poem.wordFirst.substring(0, 1)
        tv_poem_content1.text = poem.wordFirst
        tv_poem_title2.text = poem.wordSecond.substring(0, 1)
        tv_poem_content2.text = poem.wordSecond
        tv_poem_title3.text = poem.wordThird.substring(0, 1)
        tv_poem_content3.text = poem.wordThird

        ib_poem_like.setOnClickListener {
        }

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