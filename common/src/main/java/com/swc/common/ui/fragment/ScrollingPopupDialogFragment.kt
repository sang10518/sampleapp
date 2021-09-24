package com.swc.common.ui.fragment

import android.os.Bundle
import android.view.View
import com.swc.common.util.LOG
import com.swc.common.R
import kotlinx.android.synthetic.main.fragment_scrolling_popup_dialog.*


/**
Created by sangwn.choi on2020-08-03

 **/
class ScrollingPopupDialogFragment: PopupDialogFragment() {
    override val layoutId: Int = R.layout.fragment_scrolling_popup_dialog

    class ScrollingPopupDialogBuilder: PopupDialogBuilder() {

        override fun build(): ScrollingPopupDialogFragment {
            LOG.e("scroll popup build")
            return newInstance(
                mBundle
            ).apply {
                mBuilderOkAction?.let {
                    mOkAction = it
                }
                mBuilderCancelAction?.let {
                    mCancelAction = it
                }
                mBuilderDrawable?.let {
                    mImage = it
                }
            }
        }
    }

    override fun setImage(image: Any?) {
        super.setImage(image)

        image?.run {
            svPopupDesc?.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance(bundle: Bundle): ScrollingPopupDialogFragment {
            LOG.e("scroll new instance")

            return ScrollingPopupDialogFragment()
                .apply {
                    arguments = bundle
                }
        }
    }
}