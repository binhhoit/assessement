package com.sts.data.network.intercepter

import android.content.Context
import com.sts.base.util.ConnectionState
import com.sts.base.util.currentConnectivityState
import com.sts.data.exception.NoConnectionException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(private val mContext: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
       /* if (mContext.currentConnectivityState === ConnectionState.Available) {
            throw NoConnectionException()
        }*/
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}
