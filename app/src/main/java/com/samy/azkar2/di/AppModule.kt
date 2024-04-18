package com.samy.azkar2.di

import android.content.Context
import com.samy.azkar2.data.Azkar
import com.samy.azkar2.pojo.Hadith
import com.samy.azkar2.pojo.Ziker
import com.samy.azkar2.ui.main.home.NameAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Singleton


@Module
@InstallIn(ActivityComponent::class)
object AppModule {


    @Provides
    fun getAllZiker(): List<Ziker> = Azkar().getAzkar()


//    @Provides
//    @Annotations.Ziker
//    fun getPageNumbers(): Int = 1


//    @Provides
//    @Singleton
//    fun providesOkHttp(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
//        OkHttpClient.Builder().apply {
//            addInterceptor(httpLoggingInterceptor)
//            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
//            writeTimeout(TIMEOUT, TimeUnit.SECONDS)
//            readTimeout(TIMEOUT, TimeUnit.SECONDS)
//        }.build()
}