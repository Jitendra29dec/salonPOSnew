package com.hubwallet.apptspos.retrofit

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * Created by pankajk on 12/7/2018.
 *  All rights reserved by Intigate Technologies.
 */
abstract class CallBackManager<T> : Callback<T> {
    override fun onFailure(call: Call<T>, t: Throwable) {
        if (t is SocketTimeoutException || t is SocketException || t is ConnectException || t is TimeoutException || t is IOException || t is UnknownHostException) {
            val error =
                Error("Check your internet connection\nor try again…")
            onFailure(error, -500)
            return
        }
        Log.d("OkHttp", t.localizedMessage!!)
        onFailure(t,-2)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            onSuccess(response.body())
        } else {
            val error1 =
                Error("Something went wrong please try after sometime.")
            onFailure(error1, -2)
        }
    }

    abstract fun onSuccess(response: T?)
    abstract fun onFailure(t: Throwable, statusCode: Int)
}