package com.depromeet.customView


import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.MotionEvent
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

        layout_dialog_back.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                dismiss()
                getActivity(context).finish()
            }
            true
        }
    }

    private fun getActivity(context: Context): Activity {
        return when (context) {
            is Activity -> context
            is ContextWrapper -> getActivity(context.baseContext)
            else -> error("Non Activity based context")
        }
    }

    override fun onBackPressed() {
        getActivity(context).finish()
    }
}