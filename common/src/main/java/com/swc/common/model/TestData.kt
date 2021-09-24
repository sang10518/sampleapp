package com.swc.common.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
Created by sangwn.choi on2020-07-13

 **/

data class TestReq(
    @SerializedName("USER_ID") //게시판 종류별 ID
    val USER_ID: String?
) : Vo()

data class TestRes(
    @SerializedName("TEST_LIST")
    val TEST_LIST: List<TestInfo>?
) : Vo()

data class TestInfo(
    @SerializedName("TT")
    val TT: String?,
    @SerializedName("T_LIST")
    val T_LIST: List<TInfo>?
): Vo()

data class TInfo(
    @SerializedName("TTTTTT")
    val TTTTTT: String?
): Vo()

