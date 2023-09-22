package com.reader.bd_bank_info.common.webservice

import android.content.Context
import okhttp3.Cache

interface AppCache {

    fun getCache(): Cache

    fun clearCache()
}

class AppCacheImpl private constructor(private val cache: Cache) : AppCache {

    companion object {
        private const val MAX_CACHE_SIZE: Long = 10 * 1024 * 1024L // 10 MB

        @Volatile
        private var instance: AppCacheImpl? = null

        fun getInstance(context: Context): AppCacheImpl {
            return instance ?: synchronized(AppCacheImpl::class) {
                return instance ?: AppCacheImpl(Cache(context.cacheDir, MAX_CACHE_SIZE)).apply {
                    instance = this
                }
            }
        }
    }

    override fun getCache(): Cache = cache

    override fun clearCache() {
        cache.evictAll()
        cache.directory.deleteRecursively()
    }
}