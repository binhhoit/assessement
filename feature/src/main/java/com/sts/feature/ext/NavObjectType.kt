package com.sts.feature.ext

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import java.io.Serializable

@Suppress("UNCHECKED_CAST")
class NavObjectType<T: Serializable>(private val clazz: Class<T>) : NavType<T>(isNullableAllowed = true) {
    override fun get(bundle: Bundle, key: String): T? {
        @Suppress("DEPRECATION")
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            bundle.getSerializable(name, clazz)
        else
            bundle.getSerializable(name) as T?
    }

    override fun parseValue(value: String): T {
        return Gson().fromJson(value, clazz)
    }

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putSerializable(key, value)
    }
}