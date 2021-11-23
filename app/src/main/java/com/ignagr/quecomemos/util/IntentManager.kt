package com.ignagr.quecomemos.util

import android.app.Activity
import android.content.Intent
import androidx.core.app.ActivityCompat
import com.ignagr.quecomemos.ui.main.MainActivity

class IntentManager(private val activity: Activity) {

    fun goToMain(finish: Boolean) {
        val i = Intent(activity, MainActivity::class.java)
        if(finish) ActivityCompat.finishAffinity(activity)
        activity.startActivity(i)
    }
}