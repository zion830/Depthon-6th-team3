package com.depromeet.customView


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.Window
import com.depromeet.R
import kotlinx.android.synthetic.main.dialog_pass.*


class GameResultDialog(context: Context, private val isPassed: Boolean)
    : Dialog(context, R.style.ShareDialogTheme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_pass)
        window?.setBackgroundDrawableResource(R.color.colorDialog)

        initView()
    }

    private fun initView() {
        iv_game_pass.setImageResource(
                if (isPassed) R.drawable.img_pass else R.drawable.img_fail)

        layout_dialog_back.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, m: MotionEvent): Boolean {
                if (m.action == MotionEvent.ACTION_UP) {
                    dismiss()
                    (context as Activity).finish()
                }
                return true
            }
        })
    }
}