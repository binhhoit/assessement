package com.sts.feature.ext

import android.net.Uri
import android.os.Build
import android.os.Parcelable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.google.gson.Gson
import java.io.Serializable

fun NavHostController.navigateArg(route: String, arg: Pair<String, Any>?) {
    arg?.let {
        val json = Uri.encode(Gson().toJson(arg.second))
        navigate(route.replaceFirst("{${arg.first}}", json))
    } ?: navigate(route)
}

fun NavHostController.navigateArgs(route: String, args: Map<String, Any>?) {
    args?.let {
        args.forEach { (key, value) ->
            val json = Uri.encode(Gson().toJson(value))
            route.replace("{$key}", json)
        }
        navigate(route)
    } ?: navigate(route)
}

@Suppress("UNCHECKED_CAST")
fun <T : Serializable?> NavBackStackEntry.getSerializable(name: String, clazz: Class<T>): T? {
    @Suppress("DEPRECATION")
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        arguments?.getSerializable(name, clazz)
    else
        arguments?.getSerializable(name) as T
}

@Suppress("UNCHECKED_CAST", "DEPRECATION")
fun <T : Parcelable?> NavBackStackEntry.getParcelable(name: String, clazz: Class<T>): T? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        arguments?.getParcelable(name, clazz)
    else
        arguments?.getParcelable<T>(name) as T
}

typealias NavRoute = (String) -> Unit
typealias NavRouteArg = (String, Pair<String, Any>?) -> Unit
typealias NavRouteArgs = (String, Map<String, Any>?) -> Unit