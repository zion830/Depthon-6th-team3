package com.depromeet.customView


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.depromeet.R
import kotlinx.android.synthetic.main.dialog_pass.*

class GameResultDialog(context: Context, val isPassed: Boolean)
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
                if (isPassed) R.drawable.img_pass else R.drawable.img_fail
        )
    }
}