package com.swc.common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Base64
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream


/**
Created by sangwn.choi on2020-07-13

 **/
class ImageLoader {
    fun loadImage() {

    }

    fun encodeDrawable(context: Context?, resId: Int): String {
        val context = context?: return ""
        val selectedImage = BitmapFactory.decodeResource(context.resources, resId)
//        val selectedImage = (ContextCompat.getDrawable(context, resId) as? BitmapDrawable)?.bitmap
        val stream = ByteArrayOutputStream()
        selectedImage?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray: ByteArray = stream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun encodeImage(filePath: String): String {
        val selectedImage = BitmapFactory.decodeFile(filePath)
        val stream = ByteArrayOutputStream()
        selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray: ByteArray = stream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    fun decodeImage(b64Img: String): Bitmap? {
        val byteArray = Base64.decode(b64Img, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}