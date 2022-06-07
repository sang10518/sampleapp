package com.swc.common.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.swc.common.R
import com.swc.common.databinding.DrawerToolbarBinding
import com.swc.common.util.LOG

//import kotlinx.android.synthetic.main.drawer_toolbar.view.*


/**
Created by sangwn.choi on2020-07-09

 **/
class SToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {
    val binding: DrawerToolbarBinding = DrawerToolbarBinding.inflate(LayoutInflater.from(context), this, false)

    init {
        id = R.id.toolbar_id
        removeAllViews()
//        binding =
        addView(binding.root)
//        addView(LayoutInflater.from(context).inflate(R.layout.drawer_toolbar, null))
    }

    fun setSToolbarTitle(charSequence: CharSequence) {
        LOG.e("SToolbar setSToolbarTitle from: ${binding.tvToolbarTitle.text} , to: $charSequence")

        binding.tvToolbarTitle.text = charSequence
    }

    fun setSToolbarImage(resId: Int) {
        binding.ivMenu.setImageResource(resId)
    }

    fun setToolbarAction(action: (() -> Unit)?) {
        binding.ivMenu.setOnClickListener {
            action?.invoke()
        }
    }


}