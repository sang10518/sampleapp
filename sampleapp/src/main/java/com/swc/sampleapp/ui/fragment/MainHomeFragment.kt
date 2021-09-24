package com.swc.sampleapp.ui.fragment

import android.view.View
import com.swc.common.ui.fragment.BaseFragment
import com.swc.common.util.*
import com.swc.sampleapp.R
import com.swc.sampleapp.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_main_home3.*


/**
Created by sangwn.choi on2020-07-08

 **/
class MainHomeFragment : BaseFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_main_home3

    override fun setLayout() {
        loadingView = (getBaseActivity() as? MainActivity)?.findViewById(R.id.lvMain)

        //TODO: 최근 공지사항, 감염병정보를 리스트에 그리기 위해 API 호출.
        //TODO: 여기서는 ALL 대신 게시 영역 별로 조회를 해야 한다. (공지사항, 감염병 정보)
        //호출 실패 시 어떻게 처리할지...Error popup?
        //Check args
        arguments?.run {
            LOG.e("bundle arg $this")
        } ?: run {
            loadFresh()
        }

        context?.let {
            rvMainMenu?.addItemDecoration(MiddleDividerItemDecoration(it, MiddleDividerItemDecoration.ALL))
        }

    }

    //top announce, new post API zip 을 새로 호출한다.
    private fun loadFresh() {
        val context = context ?: return

        //TODO: load API call



    }

    override fun setToolbar() {
        getBaseActivity()?.getToolbar()?.run {
            setSToolbarTitle(getString(R.string.app_name))
        }
    }

    override fun onPause() {
//        hideLoading()
        super.onPause()
    }

    override fun onDestroyView() {
//        hideLoading()
        super.onDestroyView()
    }

    override fun onClick(v: View?) {

        v?.run {
            when (id) {

                else -> {

                }
            }
        }
    }

    companion object {

    }
}