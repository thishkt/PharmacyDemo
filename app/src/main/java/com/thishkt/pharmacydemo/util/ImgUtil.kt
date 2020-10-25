package com.thishkt.pharmacydemo.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


object ImgUtil {
    fun getBitmapDescriptor(
        context: Context,
        id: Int,
        width: Int = 0,
        height: Int = 0
    ): BitmapDescriptor? {
        val vectorDrawable: Drawable? =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                context.getDrawable(id)
            } else {
                ContextCompat.getDrawable(context, id)
            }
        return if (vectorDrawable != null) {
            if (width == 0) vectorDrawable.intrinsicWidth
            if (height == 0) vectorDrawable.intrinsicHeight
            vectorDrawable.setBounds(0, 0, width, height)
            val bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            val canvas = Canvas(bm);
            vectorDrawable.draw(canvas);
            BitmapDescriptorFactory.fromBitmap(bm);
        } else {
            null
        }
    }

    val Int.dp: Int
        get() = (this / Resources.getSystem().displayMetrics.density).toInt()

    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()
}
