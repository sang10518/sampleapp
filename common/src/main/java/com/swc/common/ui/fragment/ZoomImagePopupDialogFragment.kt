package com.swc.common.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swc.common.util.LOG
import com.swc.common.R
import com.swc.common.databinding.FragmentScrollingPopupDialogBinding
import com.swc.common.databinding.FragmentZoomingPopupDialogBinding

//import kotlinx.android.synthetic.main.fragment_scrolling_popup_dialog.*


/**
Created by sangwn.choi on2020-08-03

 **/
class ZoomImagePopupDialogFragment: PopupDialogFragment() {
//    override val layoutId: Int = R.layout.fragment_zooming_popup_dialog

    val zBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentZoomingPopupDialogBinding = {
            inflater, container, attachToParent -> FragmentZoomingPopupDialogBinding.inflate(inflater, container, attachToParent)
    }
    protected val zBinding: FragmentZoomingPopupDialogBinding
        get() = _binding as FragmentZoomingPopupDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.run {
            this
        }?: run {
            _binding = zBindingInflater.invoke(inflater, container, false)
            (_binding as FragmentScrollingPopupDialogBinding).root
        }
    }

    class ZoomImagePopupDialogBuilder: PopupDialogBuilder() {

        override fun build(): ZoomImagePopupDialogFragment {
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
            zBinding.svPopupDesc.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance(bundle: Bundle): ZoomImagePopupDialogFragment {
            LOG.e("scroll new instance")

            return ZoomImagePopupDialogFragment()
                .apply {
                    arguments = bundle
                }
        }
    }
}