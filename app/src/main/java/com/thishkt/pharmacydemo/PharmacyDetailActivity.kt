package com.thishkt.pharmacydemo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.thishkt.pharmacydemo.data.Feature
import kotlinx.android.synthetic.main.activity_pharmacy_detail.*


class PharmacyDetailActivity : AppCompatActivity() {

    private val data by lazy { intent.getSerializableExtra("data") as? Feature }

    private val name by lazy { data?.property?.name }
    private val maskAdultAmount by lazy { data?.property?.mask_adult }
    private val maskChildAmount by lazy { data?.property?.mask_child }
    private val phone by lazy { data?.property?.phone }
    private val address by lazy { data?.property?.address }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacy_detail)

        initView()

    }

    private fun initView() {
        tv_name.text = name ?: "資料發生錯誤"
        tv_adult_amount.text = maskAdultAmount
        tv_child_amount.text = maskChildAmount
        tv_phone.text = phone
        tv_address.text = address
    }
}