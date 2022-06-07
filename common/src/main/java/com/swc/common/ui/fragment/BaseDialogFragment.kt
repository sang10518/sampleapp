package com.swc.common.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding


/**
Created by sangwn.choi on2020-06-29

 **/
abstract class BaseDialogFragment<VB: ViewBinding>: DialogFragment() {
    protected var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val binding: VB
        get() = _binding as VB

//    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)?.run {
            this
        }?: run {
            _binding = bindingInflater.invoke(inflater, container, false)
            (_binding as VB).root
//            inflater.inflate(layoutId, container, false)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLayout()
    }

    abstract fun setLayout()
}