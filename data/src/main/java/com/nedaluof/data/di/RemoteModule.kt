package com.nedaluof.data.di

import com.nedaluof.data.BuildConfig
import com.nedaluof.data.datasource.remote.api.ApiServices
import com.nedaluof.data.datasource.remote.util.ApiInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
  fun provideMoshi(): Moshi = Moshi.Builder().build()

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
    moshi: Moshi
  ): ApiServices = Retrofit.Builder()
    .baseUrl(BuildConfig.BASE_URL)
    .client(client)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()
    .create(ApiServices::class.java)


  private const val READ_TIMEOUT: Long = 240
  private const val WRITE_TIMEOUT: Long = 240
  private const val CONNECTION_TIMEOUT: Long = 90

}