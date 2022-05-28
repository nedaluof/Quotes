package com.nedaluof.data.datasource.remote.util

import okhttp3.Interceptor
import javax.inject.Inject

/**
 * Created by NedaluOf on 8/10/2021.
 */
class ApiInterceptor @Inject constructor(
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain) = chain.proceed(chain.request())
}