package com.swc.sampleapp.ui.activity

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.swc.common.ui.activity.BaseSplashActivity
import com.swc.common.ui.custom.SToolbar
import com.swc.common.util.LOG
import com.swc.sampleapp.R
import com.swc.sampleapp.databinding.ActivitySplashBinding
import com.swc.sampleapp.databinding.FragmentMainHome3Binding


/**
Created by sangwn.choi on2020-07-03

 **/
class SplashActivity : BaseSplashActivity<ActivitySplashBinding>() {
    override val myClass: Class<*>?
        get() = MainActivity::class.java
//    override val mLayoutId: Int
//        get() = R.layout.activity_splash

    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding = {
            inflater -> ActivitySplashBinding.inflate(inflater)
    }

    override fun setLayout() {


    }

    //모든 인증단계 통과 후, 실서비스에서 사용할 API값을 여기서 미리 조회한다.
    //1. 메인홈 상단에 보여줄 3개 공지사항은 미리 스플래시에서 가져온뒤, 번들로 전달한다.
    //2. 메인홈 진입 시 신규 게시물이 있는지 조회 후, 번들로 전달한다.
    override fun moveToNextScreen() {

        startActivity(Intent(this, myClass).apply {

        })
        finish()

    }

    override fun getToolbar(): SToolbar? {
        return null
    }
}