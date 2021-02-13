package com.thishkt.pharmacydemo.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.thishkt.pharmacydemo.REQUEST_LOCATION_PERMISSION
import com.thishkt.pharmacydemo.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding

    private var locationPermissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_map)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getLocationPermission()
    }

    private fun getLocationPermission() {
        //檢查權限
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //已獲取到權限
            Toast.makeText(this, "已獲取到位置權限，可以準備開始獲取經緯度", Toast.LENGTH_SHORT).show()
            locationPermissionGranted = true
            //todo checkGPSState()
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
                .setMessage("此應用程式，需要位置權限才能正常使用")
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
                        //todo checkGPSState()
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
        }
    }
}