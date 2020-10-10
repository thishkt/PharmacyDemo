package com.thishkt.pharmacydemo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.thishkt.pharmacydemo.Util.OkHttpUtil
import com.thishkt.pharmacydemo.Util.OkHttpUtil.Companion.mOkHttpUtil
import com.thishkt.pharmacydemo.data.PharmacyInfo
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPharmacyData()

    }

    private fun getPharmacyData() {
        //顯示忙碌圈圈
        progressBar.visibility = View.VISIBLE

        mOkHttpUtil.getAsync(PHARMACIES_DATA_URL, object : OkHttpUtil.ICallback {
            override fun onResponse(response: Response) {
                //藥局名稱變數宣告
                var propertiesName = StringBuilder()

                val pharmaciesData = response.body?.string()

                val pharmacyInfo = Gson().fromJson(pharmaciesData, PharmacyInfo::class.java)

                for (i in pharmacyInfo.features) {
                    propertiesName.append(i.property.name + "\n")
                }

                runOnUiThread {
                    tv_pharmacies_data.text = propertiesName

                    //關閉忙碌圈圈
                    progressBar.visibility = View.GONE
                }
            }

            override fun onFailure(e: okio.IOException) {
                Log.d("HKT", "onFailure: $e")

                //關閉忙碌圈圈
                progressBar.visibility = View.GONE
            }
        })
    }
}