package com.thishkt.pharmacydemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.thishkt.pharmacydemo.data.Feature
import com.thishkt.pharmacydemo.data.PharmacyInfo

class PharmacyDetailActivity : AppCompatActivity() {

    private val data by lazy { intent.getSerializableExtra("data") as? Feature }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy_detail)

        val name = data?.property?.name
        Log.d("HKT", "藥局名稱：${name ?: "資料錯誤"}")
    }
}