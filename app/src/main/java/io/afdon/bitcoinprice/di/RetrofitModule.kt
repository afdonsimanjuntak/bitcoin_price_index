package io.afdon.bitcoinprice.di

import dagger.Module
import dagger.Provides
import io.afdon.bitcoinprice.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converter: Converter.Factory
    ) : Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converter)
            .build()

    @Provides
    @Singleton
    fun provideClient(loggingInterceptor: Interceptor) : OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideLoggingIntercept() : Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideConverter() : Converter.Factory = GsonConverterFactory.create()
}