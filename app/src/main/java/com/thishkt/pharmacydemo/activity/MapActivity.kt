package com.thishkt.pharmacydemo.activity

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.thishkt.pharmacydemo.PHARMACIES_DATA_URL
import com.thishkt.pharmacydemo.R
import com.thishkt.pharmacydemo.REQUEST_ENABLE_GPS
import com.thishkt.pharmacydemo.REQUEST_LOCATION_PERMISSION
import com.thishkt.pharmacydemo.adapter.MyInfoWindowAdapter
import com.thishkt.pharmacydemo.data.PharmacyInfo
import com.thishkt.pharmacydemo.databinding.ActivityMapBinding
import com.thishkt.pharmacydemo.util.OkHttpUtil
import okhttp3.Response

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {
    private lateinit var binding: ActivityMapBinding

    private var locationPermissionGranted = false
    private var mCurrLocationMarker: Marker? = null

    private var pharmacyInfo: PharmacyInfo? = null

    private lateinit var mContext: Context
    private var googleMap: GoogleMap? = null
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    //台北101
    private val defaultLocation = LatLng(25.0338483, 121.5645283)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_map)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mContext = this
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        getPharmacyData()
    }

    private fun getPharmacyData() {
        //顯示忙碌圈圈
        binding.progressBar.visibility = View.VISIBLE

        OkHttpUtil.mOkHttpUtil.getAsync(PHARMACIES_DATA_URL, object : OkHttpUtil.ICallback {
            override fun onResponse(response: Response) {
                val pharmaciesData = response.body?.string()

                Log.d("QQQ", "pharmaciesData:$pharmaciesData")

                pharmacyInfo = Gson().fromJson(pharmaciesData, PharmacyInfo::class.java)

                runOnUiThread {
                    //關閉忙碌圈圈
                    binding.progressBar.visibility = View.GONE
                    addAllMaker()
                }

            }

            override fun onFailure(e: okio.IOException) {
                Log.d("HKT", "onFailure: $e")

                //關閉忙碌圈圈
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    private fun getLocationPermission() {
        //檢查權限
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //已獲取到權限
            locationPermissionGranted = true
            checkGPSState()
        } else {
            //詢問要求獲取權限
            requestLocationPermission()
        }
    }

    private fun checkGPSState() {
        val locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder(mContext)
                .setTitle("GPS 尚未開啟")
                .setMessage("使用此功能需要開啟 GSP 定位功能")
                .setPositiveButton("前往開啟",
                    DialogInterface.OnClickListener { _, _ ->
                        startActivityForResult(
                            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQUEST_ENABLE_GPS
                        )
                    })
                .setNegativeButton("取消", null)
                .show()
        } else {
            Toast.makeText(this, "已獲取到位置權限且GPS已開啟，可以準備開始獲取經緯度", Toast.LENGTH_SHORT).show()
            getDeviceLocation()
        }
    }

    private fun getDeviceLocation() {
        try {
            if (locationPermissionGranted
            ) {
                val locationRequest = LocationRequest()
                locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                //更新頻率
                locationRequest.interval = 1000
                //更新次數，若沒設定，會持續更新
                //locationRequest.numUpdates = 1
                mFusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    object : LocationCallback() {
                        override fun onLocationResult(locationResult: LocationResult?) {
                            locationResult ?: return
                            Log.d(
                                "HKT",
                                "緯度:${locationResult.lastLocation.latitude} , 經度:${locationResult.lastLocation.longitude} "
                            )


//                            val currentLocation =
//                                LatLng(
//                                    locationResult.lastLocation.latitude,
//                                    locationResult.lastLocation.longitude
//                                )

                            //清除所有標記
                            //googleMap?.clear()

                            //清除上一次位置標記
                            //mCurrLocationMarker?.remove()

                            //當下位置存到一個 Marker 變數中，好讓下一次可以清除
//                            mCurrLocationMarker =googleMap?.addMarker(
//                                MarkerOptions().position(currentLocation).title("現在位置")
//                            )

//                            googleMap?.isMyLocationEnabled = true

//
//                            mCurrLocationMarker?.remove()
//                            googleMap?.setInfoWindowAdapter(MyInfoWindowAdapter(mContext))
//                            mCurrLocationMarker = googleMap?.addMarker(
//                                MarkerOptions()
//                                    .position(currentLocation)
//                                    .title("現在位置")
//                                    .snippet("100,66")
//                            )
//                            mCurrLocationMarker?.showInfoWindow()
//
//                            googleMap?.moveCamera(
//                                CameraUpdateFactory.newLatLngZoom(
//                                    currentLocation, 15f
//                                )
//                            )
                        }
                    },
                    null
                )

            } else {
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
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
                        //已獲取到權限
                        locationPermissionGranted = true
                        checkGPSState()
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                this,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        ) {
                            //權限被永久拒絕
                            Toast.makeText(this, "位置權限已被關閉，功能將會無法正常使用", Toast.LENGTH_SHORT).show()

                            AlertDialog.Builder(this)
                                .setTitle("開啟位置權限")
                                .setMessage("此應用程式，位置權限已被關閉，需開啟才能正常使用")
                                .setPositiveButton("確定") { _, _ ->
                                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                                    startActivityForResult(intent, REQUEST_LOCATION_PERMISSION)
                                }
                                .setNegativeButton("取消") { _, _ -> requestLocationPermission() }
                                .show()
                        } else {
                            //權限被拒絕
                            Toast.makeText(this, "位置權限被拒絕，功能將會無法正常使用", Toast.LENGTH_SHORT).show()
                            requestLocationPermission()
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                getLocationPermission()
            }
            REQUEST_ENABLE_GPS -> {
                checkGPSState()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                defaultLocation, 15f
            )
        )
        googleMap?.setInfoWindowAdapter(MyInfoWindowAdapter(mContext))
        googleMap?.setOnInfoWindowClickListener(this)

//        getLocationPermission()
    }

    private fun addAllMaker() {
        pharmacyInfo?.features?.forEach { feature ->
            val pinMarker = googleMap?.addMarker(
                MarkerOptions()
                    .position(
                        LatLng(
                            feature.geometry.coordinates[1],
                            feature.geometry.coordinates[0],
                        )
                    )
                    .title(feature.properties.name)
                    .snippet(
                        "${feature.properties.mask_adult}," +
                                "${feature.properties.mask_child}"
                    )
            )
        }

    }

    override fun onInfoWindowClick(marker: Marker?) {
        marker?.title?.let { title ->
//            Log.d("HKT", title)

            val filterData =
                pharmacyInfo?.features?.filter {
                    it.properties.name == (title)
                }

            if (filterData?.size!! > 0) {
                val intent = Intent(this, PharmacyDetailActivity::class.java)
                intent.putExtra("data", filterData.first())
                startActivity(intent)
            } else {
                Log.d("HKT", "查無資料")
            }
        }
    }
}