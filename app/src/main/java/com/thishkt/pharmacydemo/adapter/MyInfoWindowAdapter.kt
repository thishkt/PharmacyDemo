package com.thishkt.pharmacydemo.adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.thishkt.pharmacydemo.R

class MyInfoWindowAdapter(context: Context) : GoogleMap.InfoWindowAdapter {

    var mWindow: View = (context as Activity).layoutInflater.inflate(R.layout.info_window, null)

    private fun render(marker: Marker, view: View) {

        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvAdultAmount = view.findViewById<TextView>(R.id.tv_adult_amount)
        val tvChildAmount = view.findViewById<TextView>(R.id.tv_child_amount)

        val mask = marker.snippet.toString().split(",")

        tvName.text = marker.title
        tvAdultAmount.text = mask[0]
        tvChildAmount.text = mask[1]

    }

    override fun getInfoContents(marker: Marker): View {
        render(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }
}