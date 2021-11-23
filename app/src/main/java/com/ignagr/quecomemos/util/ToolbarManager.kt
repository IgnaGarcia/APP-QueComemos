package com.ignagr.quecomemos.util

import android.app.Activity
import com.ignagr.quecomemos.databinding.LayoutToolbarBinding

class ToolbarManager {

    fun setToolbar(activity: Activity, toolbar: LayoutToolbarBinding, title: String? = null) {
        toolbar.apply {
            tvTitle.text = title
            btnIconAction.setOnClickListener { activity.onBackPressed() }
        }
    }
}