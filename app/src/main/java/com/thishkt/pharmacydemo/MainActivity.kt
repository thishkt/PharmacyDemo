package com.thishkt.pharmacydemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.thishkt.pharmacydemo.util.OkHttpUtil
import com.thishkt.pharmacydemo.util.OkHttpUtil.Companion.mOkHttpUtil
import com.thishkt.pharmacydemo.data.Feature
import com.thishkt.pharmacydemo.data.PharmacyInfo
import com.thishkt.pharmacydemo.util.CountyUtil
import com.thishkt.pharmacydemo.util.CountyUtil.getCountyIndexByName
import com.thishkt.pharmacydemo.util.CountyUtil.getTownIndexByName
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*

class MainActivity : AppCompatActivity(), MainAdapter.IItemClickListener {

    //定義全域變數
    private lateinit var viewAdapter: MainAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var pharmacyInfo: PharmacyInfo? = null

    //預設名稱
    private var currentCounty: String = "臺東縣"
    private var currentTown: String = "池上鄉"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        getPharmacyData()

    }

    private fun initView() {
        val adapterCounty =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                CountyUtil.getAllCountiesName()
            )
        spinnerCounty.adapter = adapterCounty
        spinnerCounty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentCounty = spinnerCounty.selectedItem.toString()
                setSpinnerTown()
            }
        }
        spinnerTown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentTown = spinnerTown.selectedItem.toString()
                if (pharmacyInfo != null) {
                    updateRecyclerView()
                }
            }
        }
        setDefaultCountyWithTown()


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

    private fun setDefaultCountyWithTown() {
        spinnerCounty.setSelection(getCountyIndexByName(currentCounty))
        setSpinnerTown()
    }

    private fun setSpinnerTown() {
        val adapterTown =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                CountyUtil.getTownsByCountyName(currentCounty)
            )
        spinnerTown.adapter = adapterTown
        spinnerTown.setSelection(getTownIndexByName(currentCounty, currentTown))
    }

    private fun getPharmacyData() {
        //顯示忙碌圈圈
        progressBar.visibility = View.VISIBLE

        mOkHttpUtil.getAsync(PHARMACIES_DATA_URL, object : OkHttpUtil.ICallback {
            override fun onResponse(response: Response) {
                val pharmaciesData = response.body?.string()

                pharmacyInfo = Gson().fromJson(pharmaciesData, PharmacyInfo::class.java)

                runOnUiThread {

                    updateRecyclerView()

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

    private fun updateRecyclerView() {

        val filterData =
            pharmacyInfo?.features?.filter {
                it.property.county == currentCounty && it.property.town == currentTown
            }

        if (filterData != null) {
            viewAdapter.pharmacyList = filterData
        }
    }

    override fun onItemClickListener(data: Feature) {
        val intent = Intent(this, PharmacyDetailActivity::class.java)
        intent.putExtra("data", data)
        startActivity(intent)
    }


}
