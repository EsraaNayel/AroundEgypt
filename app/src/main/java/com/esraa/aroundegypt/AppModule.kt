package com.esraa.aroundegypt

import android.content.Context
import androidx.room.Room
import com.esraa.aroundegypt.data.cache.AppDatabase
import com.esraa.aroundegypt.data.remote.ExperiencesApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://aroundegypt.34ml.com/api/v2/"

object AppModule {

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun provideAPI(): ExperiencesApiService {
        return provideRetrofit().create(ExperiencesApiService::class.java)
    }

    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "aroundedgypt-database"
        ).build()
    }
}