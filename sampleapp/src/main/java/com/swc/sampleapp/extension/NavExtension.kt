package com.swc.sampleapp.extension

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.swc.common.R
import com.swc.common.util.LOG


/**
Created by sangwn.choi on2020-07-08

 **/

//fun NavController.navigate(
//    directions: NavDirections,
//    options: NavOptions? = null
//) {
//    //default anim 설정
//    val builder = NavOptions.Builder()
//        .setEnterAnim(R.anim.activity_slide_from_left)
////        .setExitAnim(R.anim.activity_stay)
////        .setPopEnterAnim(R.anim.activity_stay)
//        .setPopExitAnim(R.anim.activity_slide_to_left)
//
//    options?.run {
//        builder.setPopUpTo(popUpTo, isPopUpToInclusive)
//        builder.setLaunchSingleTop(shouldLaunchSingleTop())
//    }
//
//
//    navigate(directions, builder.build())
//}

fun NavController.navigateSafely(actionId: Int, currentArgs: Bundle? = null, bundle: Bundle? = null, popUpDest: Int = -1) {

    currentDestination?.run {
        LOG.e("navSafely source args ${getAction(actionId)?.defaultArguments}, destrination args $bundle")

        if (id == getAction(actionId)?.destinationId) {
            LOG.e("same destination. not moving..")
        } else {
            navigate(actionId, bundle)
        }
    }
}