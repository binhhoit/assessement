package com.sts.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.sts.data.model.Article
import com.sts.data.model.response.ArticlesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharePreferenceManager(context: Context) {
    companion object {
        private const val SHARED_PREF_NAME = "shared_pref"
        private const val ARTICLE_KEY = "article_key"

        // For Singleton instantiation
        @Volatile
        private var instance: SharePreferenceManager? = null

        fun getInstance(context: Context): SharePreferenceManager {
            return instance ?: synchronized(this) {
                instance ?: SharePreferenceManager(context).also {
                    instance = it
                }
            }
        }
    }

    private val sharedPreferences by lazy(LazyThreadSafetyMode.NONE) {
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
    }

    private inline fun SharedPreferences.put(body: SharedPreferences.Editor.() -> Unit) {
        val editor = this.edit()
        editor.body()
        editor.apply()
    }

    fun saveArticlesData(data: ArticlesResponse) {
        val raw = Gson().toJson(data)
        sharedPreferences.put {
            putString(ARTICLE_KEY, raw)
        }
    }

    suspend fun getArticlesData(): ArticlesResponse =
        withContext(Dispatchers.IO) {
            val raw = sharedPreferences.getString(ARTICLE_KEY, "")
            Gson().fromJson(raw, ArticlesResponse::class.java)
        }
}
