package com.thishkt.pharmacydemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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
            }

            override fun onResponse(call: Call, response: Response) {
                val pharmaciesData = response.body?.string()

                //將 pharmaciesData 整包字串資料，轉成 JSONObject 格式
                val obj = JSONObject(pharmaciesData)

                //features 是一個陣列 [] ，需要將他轉換成 JSONArray
                val featuresArray = JSONArray(obj.getString("features"))


                //藥局名稱變數宣告
//                var propertiesName: String = ""
                val propertiesName = StringBuilder()
                //透過 for 迴圈，即可以取出所有的藥局名稱
                for (i in 0 until featuresArray.length()) {
                    val properties = featuresArray.getJSONObject(i).getString("properties")
                    val propertieObj = JSONObject(properties)

                    //將每次獲取到的藥局名稱，多加跳行符號，存到變數中
//                    propertiesName += propertiesName + propertieObj.getString("name") + "\n"
                    propertiesName.append(propertieObj.getString("name") + "\n")
                }

                runOnUiThread {
                    //最後取得所有藥局名稱資料，指定顯示到 TextView 元件中
                    tv_pharmacies_data.text = propertiesName
                }
            }
        })
    }
}