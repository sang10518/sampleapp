package com.swc.sampleapp.model

import com.google.gson.annotations.SerializedName
import com.swc.common.model.Vo
import java.io.Serializable

/**
Created by sangwn.choi on2020-06-29

 **/
data class SampleResp(
    @SerializedName("sampleFieldOne")
    val sampleFieldOne: Int,

    @SerializedName("sampleFieldTwo")
    val sampleFieldTwo: Int
): Vo()

data class SampleReq(
    @SerializedName("sampleReqFieldOne")
    val sampleReqFieldOne: Int,

    @SerializedName("sampleReqFieldTwo")
    val sampleReqFieldTwo: Int
): Vo()
