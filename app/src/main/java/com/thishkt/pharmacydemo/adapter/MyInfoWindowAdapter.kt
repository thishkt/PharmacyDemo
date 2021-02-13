package com.thishkt.pharmacydemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.thishkt.pharmacydemo.databinding.InfoWindowBinding


class MyInfoWindowAdapter(_context: Context) : GoogleMap.InfoWindowAdapter {

    private val context = _context

    private fun render(marker: Marker, infoWindowBinding: InfoWindowBinding) {
        val mask = marker.snippet.toString().split(",")
        infoWindowBinding.tvName.text = marker.title
        infoWindowBinding.tvAdultAmount.text = mask[0]
        infoWindowBinding.tvChildAmount.text = mask[1]

    }

    override fun getInfoContents(marker: Marker): View {
        val infoWindowBinding =
            InfoWindowBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        render(marker, infoWindowBinding)
        return infoWindowBinding.root

    }

    override fun getInfoWindow(marker: Marker): View? {
        return null
    }
}