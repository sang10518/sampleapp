package com.swc.sampleapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swc.common.ui.adapter.BaseAdapter
import com.swc.common.ui.adapter.BaseViewHolder
import com.swc.sampleapp.R
import kotlinx.android.synthetic.main.item_list_drawer.view.*

class DrawerEntryAdapter @JvmOverloads constructor(headerCount: Int = 0, footerCount: Int = 0, clickListener: View.OnClickListener? = null) :
    BaseAdapter<DrawerItem>(headerCount, footerCount, clickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DrawerEntryViewHolder(inflater.inflate(R.layout.item_list_drawer, parent, false)).apply {
            with(containerView) {
                clDrawer?.setOnClickListener(clickListener)
            }
        }
    }

    inner class DrawerEntryViewHolder(containerView: View) : BaseViewHolder(containerView) {
        override fun bindVH(position: Int) {
            with(containerView) {
                val item = getItem(position)
                item?.run {
                    tvRowTitle?.text = title
                    ivRowIcon?.setImageResource(resId)
                }
            }
        }
    }
}

data class DrawerItem(
    val title: String?,
    val resId: Int
)