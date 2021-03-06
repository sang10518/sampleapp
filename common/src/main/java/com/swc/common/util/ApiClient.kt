package com.swc.common.util

import android.content.Context
import com.google.gson.GsonBuilder
import com.swc.common.BuildConfig
import com.swc.common.model.BResponse
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject
import io.reactivex.rxjava3.subscribers.DisposableSubscriber
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
Created by sangwn.choi on2020-06-29

 **/
object ApiClient {
    //checkFormat: code 정합성 확인할 경우 true로 설정.
    private var checkFormat: Boolean = false


    var client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor {
            LOG.e("Interceptor msg: $it")
        }.apply{
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    class UserSubscriber<T>(
        private var onSuccessAction: ((data: T) -> Unit)? = null,
        private var onErrorAction: ((error: Throwable?) -> Unit)? = null,
        private var onCompleteAction: (() -> Unit)? = null
        ) : DisposableSubscriber<BResponse<T>>() {

        override fun onError(e: Throwable?) {
            onErrorAction?.invoke(e)

            when (e) {
                is ResponseFormatException -> {
                    LOG.e("UserSubscriber responseformat exception, ${e.resultCode} ${e.resultMessage}, ${e.debugMsg}")
                }

                is ApiClientException -> {
                    LOG.e("UserSubscriber exception, ${e.debugMsg}")
                }
            }
        }

        override fun onComplete() {
            onCompleteAction?.invoke()
        }

        override fun onNext(t: BResponse<T>) {
            LOG.e("onNExt $t")
            if (!checkFormat) {
                onSuccessAction?.invoke(t.result!!)
            } else {
                when {
                    t.resultCode != CODE_OK -> {
                        onErrorAction?.invoke(ResponseFormatException("BResponse wrong resultCode", t.resultCode, t.resultMessage))
                    }
                    t.result == null -> {
                        onErrorAction?.invoke(ResponseFormatException("BResponse result data is null", t.resultCode, t.resultMessage))
                    }
                    else -> {
                        onSuccessAction?.invoke(t.result!!)
                    }
                }
            }

        }
    }

    //프로젝트마다 resultCode, result 형식이 다를경우 어떻게 처리할지 고민 필요.
    class DefaultConsumer<T>(private val emitter: Subject<T>, private val checkFormat: Boolean) : Consumer<BResponse<T>> {
        override fun accept(t: BResponse<T>) {
            if (!checkFormat) {
                emitter.onNext(t.result)
            } else {
                when {
                    t.resultCode != CODE_OK -> {
                        emitter.onError(ResponseFormatException("BResponse wrong resultCode", t.resultCode, t.resultMessage))
                    }
                    t.result == null -> {
                        emitter.onError(ResponseFormatException("BResponse result data is null", t.resultCode, t.resultMessage))
                    }
                    else -> {
                        emitter.onNext(t.result)
                    }
                }
            }
            emitter.onComplete()
        }
    }


    /**
     * TODO Glide 연계
     */
    fun <T> requestApi(context: Context, serviceType: String, serviceCode: String, data: Any, respClass: Class<T>, checkFormat: Boolean = true): Flowable<T> {
        var emitterSubject: Subject<T> = BehaviorSubject.create()
        var consumer: DefaultConsumer<T> = DefaultConsumer<T>(emitterSubject, checkFormat)

        //accept consumer.
//        consumer.accept()

        return emitterSubject.timeout(API_TIMEOUT_DURATION, TimeUnit.MILLISECONDS).toFlowable(BackpressureStrategy.BUFFER)
    }


    class ApiClientException(val debugMsg: String?) : Exception(debugMsg)
    class ResponseFormatException(val debugMsg: String?, val resultCode: Int, val resultMessage: String?) : Exception(debugMsg)

    private const val CODE_OK: Int = 200
    private const val CODE_FAIL: Int = 202

    private const val API_TIMEOUT_DURATION: Long = 10000 // 권장기준 3초 + 버퍼 7초 적용.
}