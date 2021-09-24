package com.swc.sampleapp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.swc.common.ui.activity.BaseActivity
import com.swc.common.ui.fragment.PopupDialogFragment
import com.swc.common.util.LOG
import com.swc.common.util.PopupDialogUtil
import com.swc.sampleapp.BuildConfig
import com.swc.sampleapp.R
import com.swc.sampleapp.extension.navigateSafely
import com.swc.sampleapp.ui.adapter.DrawerEntryAdapter
import com.swc.sampleapp.ui.adapter.DrawerItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_footer.*


/**
Created by sangwn.choi on2020-06-29

 **/
class MainActivity : BaseActivity(), View.OnClickListener {
    override val mLayoutId = R.layout.activity_main

    override fun setToolbar() {
        super.setToolbar()

        findViewById<ImageView>(R.id.ivMenu)?.setOnClickListener {
            dlMenu?.openDrawer(GravityCompat.START)
        }
    }

    override fun setLayout() {

        //drawer init
        rvNavMenu?.initialize(DrawerEntryAdapter(clickListener = this@MainActivity).apply {
            add(DrawerItem(getString(R.string.menu_home), R.drawable.ic_home_orange))
            add(DrawerItem(getString(R.string.menu_f), R.drawable.ic_announcement_orange))
            add(DrawerItem(getString(R.string.menu_e), R.drawable.ic_pandemic_info))
            add(DrawerItem(getString(R.string.menu_b), R.drawable.ic_emergency))
            add(DrawerItem(getString(R.string.menu_c), R.drawable.ic_selfcheck))
            add(DrawerItem(getString(R.string.menu_d), R.drawable.ic_selfcheck))
            add(DrawerItem(getString(R.string.menu_a), R.drawable.ic_faq))
        })

        tvOpenSource?.setOnClickListener(this)
        tvPrivacyPolicy?.setOnClickListener(this)

        tvAppVersion?.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)
        tvAppVersion?.setOnClickListener(this)

        //nav graph start
        findNavController(R.id.navHostFragment).run {
            setGraph(R.navigation.nav_graph, intent.extras)
        }

    }

    override fun onBackPressed() {
        LOG.e("Main onBackPressed")

        if (dlMenu?.isDrawerOpen(GravityCompat.START) == true) {
            dlMenu?.closeDrawer(GravityCompat.START)
        } else {

            findNavController(R.id.navHostFragment).run {
                //search nav destination
                if (currentDestination?.id == graph.startDestination) {
                    LOG.e(msg = "press back from start destination")
                    PopupDialogFragment.PopupDialogBuilder()
                        .setTitle(getString(R.string.action_exit))
                        .setContent(getString(R.string.popup_content_exit))
                        .setOkText(getString(R.string.action_exit))
                        .setOkAction {
                            super.onBackPressed()
                        }
                        .build()
                        .show(supportFragmentManager, null)
                } else {
                    //이전 화면이동시 hide loading
                    lvMain?.visibility = View.GONE
                    super.onBackPressed()
                }
            }


        }
    }

    private fun getForegroundFragmentArgs(): Bundle? {
        return (navHostFragment.childFragmentManager.fragments[0] ?: navHostFragment).arguments
    }

    override fun onClick(v: View?) {
        v?.run {
            when (id) {
                R.id.clDrawer -> {

                    LOG.e("swc", "clDrawer click")

                    //close drawer
                    if (dlMenu?.isDrawerOpen(GravityCompat.START) == true) {
                        dlMenu?.closeDrawer(GravityCompat.START)
                    }

                    //move to nav page..WIP

                    rvNavMenu?.run {
                        val pos = getChildAdapterPosition(v)
                        (adapter?.getItem(pos) as? DrawerItem)?.run {
                            LOG.e("item title $title")
                            LOG.e("get current args ${getForegroundFragmentArgs()}")

                            when (title) {
                                getString(R.string.menu_home) -> {
                                    navHostFragment.findNavController().navigateSafely(
                                        R.id.action_home,
                                        getForegroundFragmentArgs()
                                    )
                                }

                                else -> {

                                }
                            }

                        }

                    }

                }

                R.id.btnFloating -> {
                    //call
                    LOG.e("swc", "input num and move to call screen")
                }

                R.id.tvOpenSource -> {
                    // When the user selects an option to see the licenses:
                    OssLicensesMenuActivity.setActivityTitle(getString(R.string.open_source_license_title))

                    startActivity(Intent(this@MainActivity, OssLicensesMenuActivity::class.java))

//                    PopupDialogUtil.showOSLPopup(context, supportFragmentManager)
                }

                R.id.tvPrivacyPolicy -> {

                }

                R.id.tvAppVersion -> {

                }

                else -> {

                }
            }
        }
    }


}