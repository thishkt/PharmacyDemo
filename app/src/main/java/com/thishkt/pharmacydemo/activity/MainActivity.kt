package com.thishkt.pharmacydemo.activity

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
import com.thishkt.pharmacydemo.MainAdapter
import com.thishkt.pharmacydemo.PHARMACIES_DATA_URL
import com.thishkt.pharmacydemo.Util.CountyUtil
import com.thishkt.pharmacydemo.data.Feature
import com.thishkt.pharmacydemo.data.PharmacyInfo
import com.thishkt.pharmacydemo.databinding.ActivityMainBinding
import com.thishkt.pharmacydemo.util.OkHttpUtil
import com.thishkt.pharmacydemo.util.OkHttpUtil.Companion.mOkHttpUtil
import okhttp3.*

class MainActivity : AppCompatActivity(), MainAdapter.IItemClickListener {

    //定義全域變數
    private lateinit var viewAdapter: MainAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityMainBinding

    private var currentCounty: String = "臺東縣"
    private var currentTown: String = "池上鄉"
    private var pharmacyInfo: PharmacyInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        getPharmacyData()

    }

    private fun initView() {

        val adapterCounty = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            CountyUtil.getAllCountiesName()
        )
        binding.spinnerCounty.adapter = adapterCounty
        binding.spinnerCounty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentCounty = binding.spinnerCounty.selectedItem.toString()
                setSpinnerTown()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.spinnerTown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                currentTown = binding.spinnerTown.selectedItem.toString()
                if (pharmacyInfo != null) {
                    updateRecyclerView()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        setDefaultCountyWithTown()

        // 定義 LayoutManager 為 LinearLayoutManager
        viewManager = LinearLayoutManager(this)

        // 自定義 Adapte 為 MainAdapter，稍後再定義 MainAdapter 這個類別
        viewAdapter = MainAdapter(this)

        // 定義從佈局當中，拿到 recycler_view 元件，
        binding.recyclerView.apply {
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
        binding.spinnerCounty.setSelection(CountyUtil.getCountyIndexByName(currentCounty))
        setSpinnerTown()
    }

    private fun setSpinnerTown() {
        val adapterTown = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            CountyUtil.getTownsByCountyName(currentCounty)
        )
        binding.spinnerTown.adapter = adapterTown
        binding.spinnerTown.setSelection(CountyUtil.getTownIndexByName(currentCounty, currentTown))
    }

    private fun getPharmacyData() {
        //顯示忙碌圈圈
        binding.progressBar.visibility = View.VISIBLE

        mOkHttpUtil.getAsync(PHARMACIES_DATA_URL, object : OkHttpUtil.ICallback {
            override fun onResponse(response: Response) {
                val pharmaciesData = response.body?.string()

                pharmacyInfo = Gson().fromJson(pharmaciesData, PharmacyInfo::class.java)

                runOnUiThread {
                    //將下載的口罩資料，指定給 MainAdapter
//                    viewAdapter.pharmacyList = pharmacyInfo.features
                    updateRecyclerView()

                    //關閉忙碌圈圈
                    binding.progressBar.visibility = View.GONE
                }
            }

            override fun onFailure(e: okio.IOException) {
                Log.d("HKT", "onFailure: $e")

                //關閉忙碌圈圈
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    override fun onItemClickListener(data: Feature) {
        val intent = Intent(this, PharmacyDetailActivity::class.java)
        intent.putExtra("data", data)
        startActivity(intent)
    }

    private fun updateRecyclerView() {

        val filterData =
            pharmacyInfo?.features?.filter {
                it.properties.county == currentCounty && it.properties.town == currentTown
            }

        if (filterData != null) {
            viewAdapter.pharmacyList = filterData
        }
    }

}
