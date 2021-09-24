package com.swc.common.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned

object SpanUtil {
    fun getTextWithCenteredImageSuffix(context: Context, txt: CharSequence, resId: Int) : CharSequence {
        return SpannableStringBuilder().apply {
            append(txt)
            append(" ")
            setSpan(CenteredImageSpan(context, resId), txt.length, txt.length+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

    fun getTextWithCenteredImagePrefix(context: Context, txt: CharSequence, resId: Int, paddingDp: Int = 0) : CharSequence {
        return SpannableStringBuilder().apply {
            append(" ")
            setSpan(
                CenteredImageSpan(
                    context,
                    resId,
                    paddingDp
                ), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            append(txt)
        }
    }

    fun getTextWithCenteredImagePrefixNewLine(context: Context, txt: CharSequence, resId: Int) : CharSequence {
        return SpannableStringBuilder().apply {
            append("\n")
            setSpan(CenteredImageSpan(context, resId), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            append(txt)
        }
    }
}