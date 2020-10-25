package com.thishkt.pharmacydemo.activity

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.thishkt.pharmacydemo.R
import com.thishkt.pharmacydemo.REQUEST_LOCATION_PERMISSION
import com.thishkt.pharmacydemo.util.ImgUtil
import com.thishkt.pharmacydemo.util.ImgUtil.dp
import com.thishkt.pharmacydemo.util.ImgUtil.getBitmapDescriptor
import com.thishkt.pharmacydemo.util.ImgUtil.px

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null
    private var currLocationMarker: Marker? = null

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        mContext = this

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun getCurrentLocation() {
        //檢查權限
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //已獲取到權限
            //todo 獲取經緯度
            Toast.makeText(this, "已獲取到位置權限，可以準備開始獲取經緯度", Toast.LENGTH_SHORT).show()

            val mLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

            val locationRequest = LocationRequest()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            //更新頻率
            locationRequest.interval = 1000
            //更新次數，若沒設定，會持續更新
            //locationRequest.numUpdates = 1
            mLocationProviderClient.requestLocationUpdates(
                locationRequest,
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult?) {
                        locationResult ?: return
                        Log.d(
                            "HKT",
                            "緯度:${locationResult.lastLocation.latitude} , 經度:${locationResult.lastLocation.longitude} "
                        )

                        val currentLocation =
                            LatLng(
                                locationResult.lastLocation.latitude,
                                locationResult.lastLocation.longitude
                            )


                        //googleMap?.clear()
                        currLocationMarker?.remove()


                        currLocationMarker =googleMap?.addMarker(
                            MarkerOptions().position(currentLocation).title("現在位置")
                        )

                        googleMap?.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                currentLocation, 15f
                            )
                        )
                    }
                },
                null
            )
        } else {
            //詢問要求獲取權限
            requestLocationPermission()
        }
    }


    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            AlertDialog.Builder(this)
                .setMessage("需要位置權限")
                .setPositiveButton("確定") { _, _ ->
                    ActivityCompat.requestPermissions(
                        this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_LOCATION_PERMISSION
                    )
                }
                .setNegativeButton("取消") { _, _ -> requestLocationPermission() }
                .show()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty()) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //權限已成功獲取
                        getCurrentLocation()
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        ) {
                            //權限被永久拒絕
                            Toast.makeText(this, "位置權限被永久拒絕", Toast.LENGTH_SHORT).show()

                            //todo 前往設定頁
                            //todo 顯示對話視窗
                        } else {
                            //權限被拒絕
                            Toast.makeText(this, "位置權限被拒絕", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        getCurrentLocation()
    }
}