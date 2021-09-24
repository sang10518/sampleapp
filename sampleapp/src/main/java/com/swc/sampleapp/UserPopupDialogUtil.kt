package com.swc.sampleapp

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.swc.common.ui.fragment.PopupDialogFragment

/**
Created by sangwn.choi on2020-07-30

 **/

object UserPopupDialogUtil {

    fun showNewPostPopup(context: Context?, fragmentManager: FragmentManager, totalCount: Int, msg: String? = null, okAction: (() -> Unit)? = null) {
        context?.run {
            PopupDialogFragment.PopupDialogBuilder()
                .setTitle(getString(R.string.new_post_noti_title))
                .setContent(getString(R.string.new_post_noti_content, totalCount.toString()))
                .apply {msg?.let {setDesc(it)}}
                .setOkAction { okAction?.invoke() }
                .setCancelText(null)
                .build()
                .show(fragmentManager, null)
        }
    }
}
