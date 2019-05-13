package com.depromeet.util

import android.app.Activity
import android.widget.Toast
import com.depromeet.R

class BackPressCloseHandler(private val activity: Activity) {
    private var backKeyPressedTime: Long = 0
    private var watingTime = 2000
    private var toast: Toast? = null

    fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + watingTime) {
            backKeyPressedTime = System.currentTimeMillis()
            showGuide()
            return
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + watingTime) {
            activity.finish()
            toast!!.cancel()
        }
    }

    private fun showGuide() {
        toast = Toast.makeText(activity, R.string.all_exit, Toast.LENGTH_SHORT)
        toast!!.show()
    }
}
