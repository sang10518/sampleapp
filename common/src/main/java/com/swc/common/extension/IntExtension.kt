package com.swc.common.extension

import android.content.Context
import android.util.DisplayMetrics


/**
Created by sangwn.choi on2020-07-08

 **/

fun Int.convertDpToPixel(context: Context): Float {
    return this * (context.resources
        .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}