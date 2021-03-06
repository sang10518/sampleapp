package com.swc.sampleapp

import com.swc.common.BuildConfig
import com.swc.common.model.BResponse
import com.swc.common.util.ApiClient
import com.swc.common.util.LOG
import com.swc.sampleapp.model.SampleResp
import io.reactivex.rxjava3.core.Flowable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


/**
Created by sangwn.choi on2020-07-13

 **/
interface UserApiClient {


//    @GET("ck")
//    fun getCk(): Flowable<BResponse<SampleResp>>

    @GET("ck")
    suspend fun getCk(): BResponse<SampleResp>

    companion object {
        private var retrofit: Retrofit? = null
        var BASE_URL = BuildConfig.SERVER_IP

        fun getClient() : UserApiClient {
            if (retrofit == null ) {
                retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(ApiClient.client)
//                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build()
            }
            return retrofit!!.create(UserApiClient::class.java)
        }

    }

}



