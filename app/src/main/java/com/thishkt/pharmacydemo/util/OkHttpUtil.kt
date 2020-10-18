package com.thishkt.pharmacydemo.util

import okhttp3.*
import okio.IOException


class OkHttpUtil {
    private var mOkHttpClient: OkHttpClient? = null

    companion object {
        val mOkHttpUtil: OkHttpUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            OkHttpUtil()
        }
    }

    init {
        //Part 1: 宣告 OkHttpClient
        mOkHttpClient = OkHttpClient().newBuilder().build()
    }

    //Get 非同步
    fun getAsync(url: String, callback: ICallback) {
        //Part 2: 宣告 Request，要求要連到指定網址
        val request = with(Request.Builder()) {
            url(url)
            get()
            build()
        }

        //Part 3: 宣告 Call
        val call = mOkHttpClient?.newCall(request)

        //執行 Call 連線後，採用 enqueue 非同步方式，獲取到回應的結果資料
        call?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFailure(e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                callback.onResponse(response)
            }
        })


    }


    interface ICallback {
        fun onResponse(response: Response)

        fun onFailure(e: IOException)
    }
}