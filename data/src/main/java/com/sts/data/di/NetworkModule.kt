package com.sts.data.di

import android.annotation.SuppressLint
import android.content.Context
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import com.sts.data.BuildConfig
import com.sts.data.di.NetworkModuleConstants.KEY_AUTHORIZATION
import com.sts.data.di.NetworkModuleConstants.TIME_OUT
import com.sts.data.network.intercepter.ConnectivityInterceptor
import com.sts.data.network.remote.ServiceAPI
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

val networkModule = module {
    single { okHttpClient(get()) }
    single { retrofit(get()) }
    single { providesAPI(get()) }
}

private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.HEADERS
    return interceptor
}

private fun okHttpClient(context: Context): OkHttpClient {
    val spec = ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
        .tlsVersions(TlsVersion.TLS_1_2)
        .cipherSuites(
            CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
            CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
        )
        .build()

    val trustAllCerts = arrayOf<TrustManager>(
        @SuppressLint("CustomX509TrustManager")
        object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
        }
    )

    // Install the all-trusting trust manager
    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, SecureRandom())
    // Create an ssl socket factory with our all-trusting manager
    val sslSocketFactory = sslContext.socketFactory

    return OkHttpClient.Builder()
        .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        .addNetworkInterceptor(ConnectivityInterceptor(context))
        .addInterceptor(getHttpLoggingInterceptor())
        .addInterceptor { chain ->

            val originalRequest = chain.request()
            val originalUrl = originalRequest.url

            val newUrl = originalUrl.newBuilder()
                .addQueryParameter(KEY_AUTHORIZATION, BuildConfig.API_KEY)
                .build()

            val newRequest = originalRequest.newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }
        .connectionSpecs(Collections.singletonList(spec))
        .retryOnConnectionFailure(true)
        .connectTimeout(TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
        .readTimeout(TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
        .writeTimeout(TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
        .build()
}

private fun retrofit(okHttpClient: OkHttpClient): Retrofit {
    val builder = Retrofit.Builder()
    builder.baseUrl(BuildConfig.HOST)
    return builder.addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
        .client(okHttpClient)
        .build()
}

private fun providesAPI(retrofit: Retrofit): ServiceAPI = retrofit.create(ServiceAPI::class.java)

private object NetworkModuleConstants {
    const val TIME_OUT = 30000
    const val KEY_AUTHORIZATION = "api-key"
}
