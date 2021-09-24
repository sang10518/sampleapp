package com.swc.common.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
Created by sangwn.choi on2020-07-09

 **/
data class BResponse<T> (
    @SerializedName("resultCode", alternate = ["RESULT_CODE"])//결과 코드
    val resultCode: Int,

    @SerializedName("resultMessage", alternate = ["RESULT_MESSAGE"])//결과 메시지
    val resultMessage: String?,

    @SerializedName("result", alternate = ["data"])//결과 값.
    // TODO json 응답에 데이터는 해당 필드로 말아달라고 요청 필요.
    val result: T?
): Vo()