package com.swc.common.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swc.common.ui.activity.BaseActivity
import com.swc.common.ui.custom.LoadingView
import com.swc.common.util.LOG
import com.trello.rxlifecycle4.components.support.RxFragment


/**
Created by sangwn.choi on2020-06-29

 **/
abstract class BaseFragment : RxFragment(), View.OnClickListener {

    abstract val layoutId: Int
    private val className: String = this.javaClass.simpleName

    open var loadingView: LoadingView? = null

    var hasInitializedRootView = false
    private var rootView: View? = null

    private fun getPersistentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?, layout: Int): View? {
        if (rootView == null) {
            // Inflate the layout for this fragment
            rootView = inflater?.inflate(layout, container, false)
        } else {
            // Do not inflate the layout again.
            // The returned View of onCreateView will be added into the fragment.
            // However it is not allowed to be added twice even if the parent is same.
            // So we must remove rootView from the existing parent view group
            // (it will be added back).
            (rootView?.parent as? ViewGroup)?.removeView(rootView)
        }
        return rootView
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return getPersistentView(inflater, container, savedInstanceState, layoutId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        LOG.e(className, "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitializedRootView) {
            hasInitializedRootView = true
            LOG.e(className, "setLayout")

            setLayout()

            //로딩 중에는 Fragment 내 다른 영역 클릭 금지
            loadingView?.run {
                setOnClickListener(this@BaseFragment)
            }
        }

        //기존 뷰 재활용해도 툴바는 다시 설정 필요.
        setToolbar()
    }

    abstract fun setLayout()

    abstract fun setToolbar()

    fun getBaseActivity(): BaseActivity? {
        return (activity as? BaseActivity)
    }

    open fun loadData() {

    }

    open fun reloadData() {

    }

    open fun loadMoreData() {

    }

    open fun showLoading() {
        LOG.e(className, "showLoading")
        loadingView?.visibility = View.VISIBLE
    }

    open fun hideLoading() {
        LOG.e(className, "hideLoading")
        loadingView?.visibility = View.GONE
    }

    override fun onPause() {
        LOG.e(className, "onPause")
        super.onPause()
    }

    override fun onStop() {
        LOG.e(className, "onStop")
        super.onStop()
    }

    override fun onDestroy() {
        LOG.e(className, "onDestroy")
        super.onDestroy()
    }

    override fun onDestroyView() {
        LOG.e(className, "onDestroyView")
        super.onDestroyView()
    }
}