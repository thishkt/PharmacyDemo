package com.thishkt.pharmacydemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.thishkt.pharmacydemo.Util.OkHttpUtil
import com.thishkt.pharmacydemo.Util.OkHttpUtil.Companion.mOkHttpUtil
import com.thishkt.pharmacydemo.data.Feature
import com.thishkt.pharmacydemo.data.PharmacyInfo
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*

class MainActivity : AppCompatActivity(), MainAdapter.IItemClickListener {

    //定義全域變數
    private lateinit var viewAdapter: MainAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        getPharmacyData()

    }

    private fun initView() {
        // 定義 LayoutManager 為 LinearLayoutManager
        viewManager = LinearLayoutManager(this)

        // 自定義 Adapte 為 MainAdapter，稍後再定義 MainAdapter 這個類別
        viewAdapter = MainAdapter(this)

        // 定義從佈局當中，拿到 recycler_view 元件，
        recycler_view.apply {
            // 透過 kotlin 的 apply 語法糖，設定 LayoutManager 和 Adapter
            layoutManager = viewManager
            adapter = viewAdapter

            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun getPharmacyData() {
        //顯示忙碌圈圈
        progressBar.visibility = View.VISIBLE

        mOkHttpUtil.getAsync(PHARMACIES_DATA_URL, object : OkHttpUtil.ICallback {
            override fun onResponse(response: Response) {
                val pharmaciesData = response.body?.string()

                val pharmacyInfo = Gson().fromJson(pharmaciesData, PharmacyInfo::class.java)

                runOnUiThread {
                    //將下載的口罩資料，指定給 MainAdapter
                    viewAdapter.pharmacyList = pharmacyInfo.features

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

    override fun onItemClickListener(data: Feature) {
        val intent = Intent(this, PharmacyDetailActivity::class.java)
        intent.putExtra("data",data)
        startActivity(intent)
    }


}
