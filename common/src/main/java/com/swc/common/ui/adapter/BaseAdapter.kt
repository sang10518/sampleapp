package com.swc.common.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import kotlinx.android.extensions.LayoutContainer

/**
Created by sangwn.choi on2020-06-29

 **/
abstract class BaseAdapter<T>(
    private val headerCount: Int = 0,
    private val footerCount: Int = 0,
    val clickListener: View.OnClickListener? = null
) : RecyclerView.Adapter<BaseViewHolder>() {

    private var pageNo: Int = 0
    var canLoadMore = false

    var dataList: ArrayList<T> = ArrayList()
    var targetCount: Int = 0
    private var isNotifyOnChange = true

    fun setNotifyOnChange(isNotify: Boolean) {
        isNotifyOnChange = isNotify
    }

    fun addAll(collection: Collection<T>) {
        dataList.addAll(collection)
        if (isNotifyOnChange) {
            notifyDataSetChanged()
        }
    }

    fun clear() {
        dataList.clear()
        if (isNotifyOnChange) {
            notifyDataSetChanged()
        }
    }

    fun add(item: T) {
        dataList.add(item)
        if (isNotifyOnChange) {
            notifyDataSetChanged()
        }
    }

    fun addPageNo() {
        pageNo++
    }

    fun resetPageNo() {
        pageNo = 0
    }

    fun getPageNo(): Int {
        return pageNo
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindVH(position)
    }

    fun getDataCount(): Int {
        return dataList.size
    }

    fun getItem(position: Int): T? {
        return if (position == NO_POSITION || dataList.isEmpty()) {
            null
        } else {
            if (position >= headerCount) {
                dataList[position - headerCount]
            } else {
                null
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size + headerCount + footerCount
    }

    override fun getItemViewType(position: Int): Int {

        return when {
            position < headerCount -> {
                VIEW_TYPE_HEADER
            }
            position >= itemCount - footerCount -> {
                VIEW_TYPE_FOOTER
            }
            else -> {
                VIEW_TYPE_ITEM
            }
        }
    }

    companion object {
        const val VIEW_TYPE_HEADER = 0x1000
        const val VIEW_TYPE_FOOTER = 0x1001
        const val VIEW_TYPE_ITEM = 0x1002
    }
}

/**
 * ViewHolder 뷰 캐싱을 위해 LayoutContainer 익스텐션 적용.
 */
abstract class BaseViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {
    //perform binding.
    abstract fun bindVH(position: Int)
}