package com.swc.common.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.text.HtmlCompat
import androidx.fragment.app.FragmentManager
import com.swc.common.R
import com.swc.common.ui.fragment.PopupDialogFragment
import com.swc.common.ui.fragment.ScrollingPopupDialogFragment
import com.swc.common.ui.fragment.ZoomImagePopupDialogFragment


/**
Created by sangwn.choi on2020-07-22

 **/
object PopupDialogUtil {

    fun showImagePopup(context: Context?, fragmentManager: FragmentManager, resId: Int, okAction: (() -> Unit)? = null) {
        context?.run {
            ZoomImagePopupDialogFragment.ZoomImagePopupDialogBuilder()
                .setImageResId(resId)
                .setOkAction { okAction?.invoke() }
                .setCancelText(null)
                .build()
                .show(fragmentManager, null)
        }
    }

    fun showImagePopup(context: Context?, fragmentManager: FragmentManager, drawable: Drawable, okAction: (() -> Unit)? = null) {
        context?.run {
            ZoomImagePopupDialogFragment.ZoomImagePopupDialogBuilder()
                .setImageDrawable(drawable)
                .setOkAction { okAction?.invoke() }
                .setCancelText(null)
                .build()
                .show(fragmentManager, null)
        }
    }

    fun showOSLPopup(context: Context?, fragmentManager: FragmentManager, okAction: (() -> Unit)? = null) {
        context?.run {
            ScrollingPopupDialogFragment.ScrollingPopupDialogBuilder()
                .setTitle(getString(R.string.open_source_license_title))
                .setDesc(getString(R.string.open_source_license_content))
                .setOkAction { okAction?.invoke() }
                .setCancelText(null)
                .build()
                .show(fragmentManager, null)
        }
    }

    fun showScreenLockPopup(context: Context?, fragmentManager: FragmentManager, okAction: (() -> Unit)? = null) {
        context?.run {
            PopupDialogFragment.PopupDialogBuilder()
                .setTitle(getString(R.string.action_exit))
                .setContent(getString(R.string.inactive_exit_msg))
                .setOkAction { okAction?.invoke() }
                .setCancelText(null)
                .build()
                .show(fragmentManager, null)
        }
    }

    fun showSessionTimeoutPopup(context: Context?, fragmentManager: FragmentManager, okAction: (() -> Unit)? = null) {
        context?.run {
            PopupDialogFragment.PopupDialogBuilder()
                .setTitle("Session Timeout")
                .setContent("Timeout. Exiting")
                .setOkAction { okAction?.invoke() }
                .setCancelText(null)
                .build()
                .show(fragmentManager, null)
        }
    }

    fun showApiTimeoutPopup(context: Context?, fragmentManager: FragmentManager, okAction: (() -> Unit)? = null) {
        context?.run {
            PopupDialogFragment.PopupDialogBuilder()
                .setTitle("Timeout")
                .setContent("Timeout. Exiting")
                .setOkAction { okAction?.invoke() }
                .setCancelText(null)
                .build()
                .show(fragmentManager, null)
        }
    }

    fun showNoNetworkPopup(context: Context?, fragmentManager: FragmentManager, okAction: (() -> Unit)?= null) {
        context?.run {
            PopupDialogFragment.PopupDialogBuilder()
                .setTitle(null)
                .setContent(getString(R.string.msg_network_none))
                .setCancelText(null)
                .setOkAction { okAction?.invoke()} // offline 시 바로 종료 or 재시도?
                .build()
                .show(fragmentManager, null)
        }
    }

    fun showErrorPopup(context: Context?, fragmentManager: FragmentManager, okAction: (() -> Unit)? = null) {
        context?.run {
            PopupDialogFragment.PopupDialogBuilder()
                .setTitle(getString(R.string.error_popup_title))
                .setContent(getString(R.string.error_popup_content))
                .setOkText(getString(R.string.action_retry))
                .setOkAction { okAction?.invoke() }
                .build()
                .show(fragmentManager, null)
        }
    }

    fun showWebviewErrorPopup(context: Context?, fragmentManager: FragmentManager, description: String? = null, okAction: (() -> Unit)? = null) {
        context?.run {
            val content: String = description?.run {
                "${getString(R.string.error_popup_content)}\n$description"
            }?: run {
                getString(R.string.error_popup_content)
            }
            PopupDialogFragment.PopupDialogBuilder()
                .setTitle(getString(R.string.error_popup_title))
                .setContent(content)
                .setOkAction { okAction?.invoke() }
                .setCancelText(null)
                .build()
                .show(fragmentManager, null)
        }
    }

    fun showJsAlert(context: Context?, fragmentManager: FragmentManager, description: String? = null, okAction: (() -> Unit)? = null) {
        context?.run {
            PopupDialogFragment.PopupDialogBuilder()
                .setTitle(null)
                .setContent(description)
                .setOkAction { okAction?.invoke() }
                .setCancelText(null)
                .build()
                .show(fragmentManager, null)
        }
    }


    fun showUpdateErrorPopup(context: Context?, fragmentManager: FragmentManager, okAction: (() -> Unit)? = null) {
        context?.run {
            PopupDialogFragment.PopupDialogBuilder()
                .setTitle(getString(R.string.error_popup_title))
                .setContent(getString(R.string.msg_app_update_error))
                .setOkText(getString(R.string.action_exit))
                .setOkAction { okAction?.invoke() }
                .setCancelText(null)
                .build()
                .show(fragmentManager, null)
        }
    }
}