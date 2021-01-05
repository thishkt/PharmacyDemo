package com.thishkt.pharmacydemo

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.thishkt.pharmacydemo.data.PharmacyInfo
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var tv_pharmacies_data: TextView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_pharmacies_data = findViewById(R.id.tv_pharmacies_data)
        progressBar = findViewById(R.id.progressBar)

        getPharmacyData()

    }

    private fun getPharmacyData() {
        //顯示忙碌圈圈
        progressBar.visibility = View.VISIBLE

        //口罩資料網址
        val pharmaciesDataUrl =
            "https://raw.githubusercontent.com/thishkt/pharmacies/master/data/info.json"

        //Part 1: 宣告 OkHttpClient
        val okHttpClient = OkHttpClient().newBuilder().build()

        //Part 2: 宣告 Request，要求要連到指定網址
        val request: Request = Request.Builder().url(pharmaciesDataUrl).get().build()

        //Part 3: 宣告 Call
        val call = okHttpClient.newCall(request)

        //執行 Call 連線後，採用 enqueue 非同步方式，獲取到回應的結果資料
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {
                Log.d("HKT", "onFailure: $e")

                //關閉忙碌圈圈
                progressBar.visibility = View.GONE
            }

            override fun onResponse(call: Call, response: Response) {
                //藥局名稱變數宣告
                var propertiesName = StringBuilder()

                val pharmaciesData = response.body?.string()

                //將 pharmaciesData 整包字串資料，轉成 JSONObject 格式
                val obj = JSONObject(pharmaciesData)

                val pharmacyInfo = Gson().fromJson(pharmaciesData, PharmacyInfo::class.java)

                for (i in pharmacyInfo.features) {
                    propertiesName.append(i.property.name + "\n")
                }


                runOnUiThread {
                    //最後取得所有藥局名稱資料，指定顯示到 TextView 元件中
                    tv_pharmacies_data.text = propertiesName

                    //關閉忙碌圈圈
                    progressBar.visibility = View.GONE
                }

            }
        })
    }
}