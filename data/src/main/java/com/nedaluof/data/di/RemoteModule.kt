package com.nedaluof.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nedaluof.data.BuildConfig
import com.nedaluof.data.datasource.remote.api.ApiServices
import com.nedaluof.data.datasource.remote.util.ApiInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by NedaluOf on 8/10/2021.
 */
@InstallIn(SingletonComponent::class)
@Module
object RemoteModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().apply {
        setLenient()
    }.create()

    @Singleton
    @Provides
    fun provideInterceptor() = HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        apiInterceptor: ApiInterceptor
    ) = OkHttpClient.Builder()
        .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .addInterceptor(apiInterceptor)
        .retryOnConnectionFailure(true)
        .build()


    @Singleton
    @Provides
    fun provideInspirationalRESTRetrofitClient(
        client: OkHttpClient,
        gson: Gson
    ): ApiServices = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(ApiServices::class.java)



    private const val READ_TIMEOUT: Long = 240
    private const val WRITE_TIMEOUT: Long = 240
    private const val CONNECTION_TIMEOUT: Long = 90

}