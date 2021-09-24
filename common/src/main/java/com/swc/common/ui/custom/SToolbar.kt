package com.swc.common.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import com.swc.common.R
import kotlinx.android.synthetic.main.drawer_toolbar.view.*


/**
Created by sangwn.choi on2020-07-09

 **/
class SToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    init {
        id = R.id.toolbar_id
        removeAllViews()
        addView(LayoutInflater.from(context).inflate(R.layout.drawer_toolbar, null))
    }

    fun setSToolbarTitle(charSequence: CharSequence) {
        tvToolbarTitle?.text = charSequence
    }

    fun setSToolbarImage(resId: Int) {
        ivMenu?.setImageResource(resId)
    }

    fun setToolbarAction(action: (() -> Unit)?) {
        ivMenu?.setOnClickListener {
            action?.invoke()
        }
    }
}